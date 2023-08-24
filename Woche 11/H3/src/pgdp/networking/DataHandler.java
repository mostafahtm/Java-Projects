package pgdp.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import pgdp.networking.DataHandler.ConnectionException;
import pgdp.networking.ViewController.Message;
import pgdp.networking.ViewController.User;

public class DataHandler {

	private DataInputStream in;
	private DataOutputStream out;
	private Socket socket;

	private Queue<Byte> handshakeMutex;
	private Thread inputHandler;

	private HttpClient client;
	private int id;
	private String username;
	private String password;

	public static String serverAddress = "carol.sse.cit.tum.de";

	private final static byte SUPPORTED_VERSION = 42;

	boolean connected;

	/**
	 * Erstellt neuen HTTP Client für die Verbindung zum Server
	 */
	public DataHandler() {
		handshakeMutex = new LinkedList<>();

		/************************
		 * Your Code goes here: *
		 ************************/
		client = HttpClient.newBuilder().version(Version.HTTP_1_1).followRedirects(Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(20)).build();
	}

	/************************************************************************************************************************
	 * * HTTP Handling * *
	 *************************************************************************************************************************/
	public static void main(String... args) { // zum Testen
//		DataHandler dh = new DataHandler();
//		System.out.println(dh.register("mosi", "ge64baw"));
//		System.out.println(dh.register("mosi", "ge64ba"));
//		System.out.println(dh.login("mosi", "123"));
//		System.out.println(dh.requestToken("mosi", "123"));
	}

	/**
	 * Registriert den Nutzer beim Server oder erfragt ein neues Passwort Gibt bei
	 * Erfolg true zurück. Endpoint: /api/user/register
	 * 
	 * @param username Nutzername
	 * @param kennung  TUM Kennung
	 * @return Registrierung erfolgreich
	 */
	public boolean register(String username, String kennung) {
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://" + serverAddress + "/api/user/register"))
				.header("Content-Type", "application/json").POST(BodyPublishers
						.ofString("{\"username\": \"" + username + "\",\"tum_kennung\": \"" + kennung + "\"}"))
				.build();

		HttpResponse<String> response = null;

		try {
			response = client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			return false;
		}

		// Zurückgeben, ob die Anfrage erfolgreich war.
		return response.statusCode() == 200 ? true : false;
	}

	/**
	 * Hilfsmethode um nach erfolgreichem Login einen Authentifizierungstoken zu
	 * erhalten. Returns null upon failure
	 * 
	 * @return Authentication token or null
	 */
	public String requestToken() {

		if (this.username == null || this.password == null) {
			return null;
		}

		return requestToken(this.username, this.password);
	}

	/**
	 * Erfragt Autentifizierungstoken vom Server. Gibt null bei Fehler zurück
	 * Endpoint: /token
	 * 
	 * @param username Nutzername
	 * @param password Passwort
	 * @return token oder null
	 */
	private String requestToken(String username, String password) {
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://" + serverAddress + "/token"))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.POST(BodyPublishers.ofString("username=" + username + "&password=" + password)).build();

		HttpResponse<String> response = null;

		try {
			response = client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			return null;
		}

		// Falls die Anfrage erfolgreich war, Token auslesen und zurückgeben.
		if (response.statusCode() == 200) {
			String jsonString = response.body();
			JSONObject jo = new JSONObject(jsonString);
			return jo.getString("access_token");
		}
		return null;
	}

	/**
	 * Initialer login. Wenn ein Token mit Nutzername und Passwort erhalten wird,
	 * werden diese gespeichert. Anschließend wird die Nutzer ID geladen. Endpoint:
	 * /token /api/user/me
	 * 
	 * @param username Nutzername
	 * @param password Passwort
	 * @return Login erfolgreich
	 */
	public boolean login(String username, String password) {
		// Token anfragen und, falls die Anfrage erfolgreich war, Nutzername und
		// Passwort dieses DataHandlers setzen.
		String token = requestToken(username, password);
		if (token == null) {
			return false;
		}

		HttpRequest request = HttpRequest.newBuilder(URI.create("http://" + serverAddress + "/api/user/me/"))
				.header("accept", "application/json").header("Authorization", "Bearer " + token).GET().build();

		HttpResponse<String> response = null;

		try {
			response = client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			return false;
		}

		// Zurückgeben, ob die Anfrage erfolgreich war und wenn ja, die ID dieses
		// DataHandlers setzen.
		if (response.statusCode() == 200) {
			this.username = username;
			this.password = password;
			String jsonString = response.body();
			JSONObject jo = new JSONObject(jsonString);
			this.id = jo.getInt("id");
			return true;
		}
		return false;
	}

	/**
	 * Erfragt alle öffentlichen Nutzer vom Server Endpoint: /api/users
	 * 
	 * @return Map von Nutzern und IDs
	 */
	public Map<Integer, User> getContacts() {

		HttpRequest request = HttpRequest.newBuilder(URI.create("http://" + serverAddress + "/api/users"))
				.header("Authorization", "Bearer " + requestToken(username, password)).GET().build();

		HttpResponse<String> response = null;

		try {
			response = client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			return null;
		}

		// Erzeuge und fülle eine Map entsprechend der vom Server erhaltenen
		// Antwort,
		// falls die Anfrage erfolgreich war.
		if (response.statusCode() == 200) {
			Map<Integer, User> map = new HashMap<>();
			String jsonString = response.body();
			JSONArray ja = new JSONArray(jsonString);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				map.put(jo.getInt("id"),
						new User(jo.getInt("id"), jo.getString("username"), new ArrayList<ViewController.Message>()));
			}
			return map;
		}
		return null;
	}

	/**
	 * Erfragt alle Nachrichten, welche mit einem gewissen Nutzer ausgetauscht
	 * wurden. Endpoint: /api/messages/with/
	 * 
	 * @param id    ID des Partners
	 * @param count Anzahl der zu ladenden Nachrichten
	 * @param page  Falls count gesetzt, gibt die Seite an Nachrichten an.
	 * @return Liste der Abgefragten Nachrichten.
	 */
	public List<Message> getMessagesWithUser(int id, int count, int page) {
		HttpRequest request = HttpRequest
				.newBuilder(URI.create("http://" + serverAddress + "/api/messages/with/" + Long.toString(id) + "?limit="
						+ Integer.toString(count) + "&pagination=" + Integer.toString(page)))
				.header("Authorization", "Bearer " + requestToken(username, password)).GET().build();

		HttpResponse<String> response = null;

		try {
			response = client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			return null;
		}

		// Erzeuge und fülle eine List entsprechend der vom Server erhaltenen
		// Antwort,
		// falls die Anfrage erfolgreich war.
		if (response.statusCode() == 200) {
			JSONArray ja = new JSONArray(response.body());
			List<ViewController.Message> msgList = new ArrayList<>(ja.length());
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
//				OffsetDateTime odt = OffsetDateTime.parse(jo.getString("time"));
//				LocalDateTime ldt = odt.toLocalDateTime();
				LocalDateTime ldt = LocalDateTime.parse(jo.getString("time"));
				msgList.add(new Message(ldt, jo.getString("text"), this.id == jo.getInt("from_id"), jo.getInt("id")));
			}
			return msgList;
		}
		return null;
	}

	/*-**********************************************************************************************************************
	*                                                                                                                       *
	*                                       Socket Handling                                                                 *
	*                                                                                                                       *
	*************************************************************************************************************************/

//	/**
//	 * Thread Methode um ankommende Nachrichten zu behandeln
//	 */
	private void handleInput() {

		System.out.println("Input Handler started");

		try {
			while (true) {

				byte type = in.readByte();
				System.out.println("Recieved Message");

				switch (type) {
				case 0 -> {
					byte hsType = in.readByte();
					if (hsType == 5) {

						passHandshakeMessage(new byte[] { type, hsType });
					}
				}
				case 1 -> {

					int length = (in.readByte() << 8) | in.readByte();

					byte[] content = new byte[length];
					in.read(content);

					displayMessage(new String(content, StandardCharsets.UTF_8));
				}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Erstelle einen Socket und Verbinde mit dem Server. Gebe Nutzer ID und Token
	 * an. Verifiziert Server Antworten
	 * 
	 * @throws ConnectionException
	 */
	private void connect() throws ConnectionException {
		try {
			// TODO: Socket erstellen und bei Erfolg den Handshake mit dem Server ausführen.
			// Der 'DataInputStream in' und 'DataOutputStream out' sollen entsprechend zum
			// Lesen/Schreiben
			// des Input-/Output-Streams des Sockets gesetzt werden.
			socket = new Socket(serverAddress, 1337);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			// Perform the handshake with the server
			// server Hello
			byte[] serverHello = new byte[3];
			in.read(serverHello);
			if (serverHello[0] != 0x00 || serverHello[1] != 0x00 || serverHello[2] != 0x2a) {
				socket = null;
				in = null;
				out = null;
				throw new ConnectionException();
			}
			// client Hello
			byte[] clientHello = new byte[] { 0x00, 0x01 };
			out.write(clientHello);
			out.flush();

			// identification
			byte[] byteArr_ID = convertIntToHexByteArray(this.id);
			byte[] clientIdentification = new byte[3 + byteArr_ID.length];
			clientIdentification[0] = 0x00;
			clientIdentification[1] = 0x02;
			clientIdentification[2] = (byte) byteArr_ID.length;
			for (int i = 0; i < byteArr_ID.length; i++) {
				clientIdentification[i + 3] = byteArr_ID[i];
			}
			out.write(clientIdentification);
			out.flush();

			// authentication
			byte[] utf8FormatOfToken = toUTF8Bytes(requestToken());
			byte[] clientAuthentication = new byte[4 + utf8FormatOfToken.length];
			clientAuthentication[0] = 0x00;
			clientAuthentication[1] = 0x03;
			byte[] lengthOfTokenInHex = convertIntToHexByteArray(utf8FormatOfToken.length);
			if (lengthOfTokenInHex.length < 2) {
				lengthOfTokenInHex = new byte[] { 0x00, lengthOfTokenInHex[0] };
			}
			clientAuthentication[2] = lengthOfTokenInHex[0];
			clientAuthentication[3] = lengthOfTokenInHex[1];
			for (int i = 0; i < utf8FormatOfToken.length; i++) {
				clientAuthentication[i + 4] = utf8FormatOfToken[i];
			}
			out.write(clientAuthentication);
			out.flush();

			startInputHandler();
			connected = true;
		} catch (Throwable t) {
			if (t.getClass().equals(ConnectionException.class)) {
				throw (ConnectionException) t;
			}

			t.printStackTrace();
			System.exit(-1);
		}
	}

	private static byte[] toUTF8Bytes(String input) {
		return input.getBytes(StandardCharsets.UTF_8);
	}

	private static byte[] convertIntToHexByteArray(int number) {
		if (number < 16) {
			return new byte[] { (byte) number };
		}
		String s = Integer.toHexString(number);
		if (s.length() % 2 != 0) {
			s = "0" + s;
		}
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * Wechselt die Verbindung zu einem anderen Chatpartner
	 * 
	 * @param partnerID
	 * @throws ConnectionException
	 */
	public void switchConnection(int partnerID) throws ConnectionException {
		try {
			if (!connected) {
				connect();
			}

			// TODO: Teile dem Server mit, dass du dich mit dem Chatpartner mit ID
			// 'partnerID' verbinden möchtest
			// und stelle sicher, dass der Server dies acknowledgt.
			byte[] byteArr_ID = convertIntToHexByteArray(partnerID);
			byte[] switchReq = new byte[3 + byteArr_ID.length];
			switchReq[0] = 0x00;
			switchReq[1] = 0x04;
			switchReq[2] = (byte) byteArr_ID.length;
			for (int i = 0; i < byteArr_ID.length; i++) {
				switchReq[i + 3] = byteArr_ID[i];
			}
			out.write(switchReq);
			out.flush();

			handshakeMutex = new LinkedList<>();
			for (byte b : getResponse(2)) {
				handshakeMutex.add(b);
			}
			for (int i = 0; i < 2; i++) {
				byte b = handshakeMutex.poll();
				if (i == 0 && b != 0x00) {
					throw new ConnectionException();
				}
				if (i == 1 && (b != 0x05 || b != 0xf0 || b != 0xf1 || b != 0xff)) {
					throw new ConnectionException();
				}
			}

		} catch (Throwable t) {

			if (t.getClass().equals(ConnectionException.class)) {
				throw (ConnectionException) t;
			}
			t.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Sende eine Nachricht an den momentan ausgewählten Nutzer.
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		try {
			// Kodiert die übergebene 'message' in UTF-8
			byte[] buf = StandardCharsets.UTF_8.encode(message).array();
			int length = Math.min(buf.length, 0xffff);

			// TODO: Sende die übergebene Message

			byte[] msg = new byte[length + 3];
			msg[0] = 0x01;
			msg[1] = (byte) ((length >> 8) & 0xff);
			msg[2] = (byte) (length & 0xff);
			System.arraycopy(buf, 0, msg, 3, length);
			out.write(msg);
			out.flush();

		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
		}

	}

	/**
	 * Holt sich length bytes vom empfänger Thread
	 * 
	 * @param length anzahl an bytes
	 * @return
	 */
	private byte[] getResponse(int length) {

		boolean wait = true;
		byte[] resp = new byte[length];

		synchronized (handshakeMutex) {
			wait = handshakeMutex.size() < length;
		}

		while (wait) {
			synchronized (inputHandler) {
				try {
					inputHandler.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
			synchronized (handshakeMutex) {
				wait = handshakeMutex.size() < length;
			}
		}

		synchronized (handshakeMutex) {
			for (int i = 0; i < resp.length; i++) {
				resp[i] = handshakeMutex.remove();
			}
		}
		return resp;
	}

	/**
	 * Startet einen neuen thread für das input handling.
	 */
	private void startInputHandler() {

		inputHandler = new Thread() {
			@Override
			public void run() {
				handleInput();
			}
		};
		inputHandler.start();

	}

	/**
	 * Übergibt eine Nachricht an die Nutzeroberfläche
	 * 
	 * @param content Nachrichten inhalt
	 */
	private void displayMessage(String content) {
		Platform.runLater(() -> {
			ViewController.displayMessage(ViewController.currentChat,
					new Message(LocalDateTime.now(), content, false, 0));
		});
	}

	/**
	 * Übergibt eine Handshake Nachricht an den Hauptthread
	 * 
	 * @param handshake Nachricht
	 */
	private void passHandshakeMessage(byte[] handshake) {
		synchronized (handshakeMutex) {

			for (byte b : handshake) {
				handshakeMutex.add(b);
			}
		}

		synchronized (inputHandler) {
			inputHandler.notifyAll();
		}
		System.out.println("Notified main thread");
	}

	/**
	 * Setter fürs testing
	 * 
	 * @param client
	 */
	public void setClient(HttpClient client) {
		this.client = client;
	}

	/**
	 * Schlißet offene Verbindungen
	 */
	public void close() {
		if (inputHandler != null) {
			inputHandler.interrupt();
		}
		if (socket != null) {
			try {
				out.write(new byte[] { 0, -1 });
				socket.close();
			} catch (IOException e) {
				// pass
			}
		}
	}

	public static class ConnectionException extends Exception {

		private static final long serialVersionUID = 9055969838018372992L;

		public ConnectionException() {
			super();
		}

		public ConnectionException(String message) {
			super(message);
		}

	}
}