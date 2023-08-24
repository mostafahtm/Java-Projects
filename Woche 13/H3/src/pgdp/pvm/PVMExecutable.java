package pgdp.pvm;

public class PVMExecutable {

	private final BasicBlock entryPoint;
	private final int localVariables;
	private final int maxStack;

	public PVMExecutable(BasicBlock entryPoint, int localVariables, int maxStack) {
		this.entryPoint = entryPoint;
		this.localVariables = localVariables;
		this.maxStack = maxStack;
	}

	/**
	 * Simuliert das nun verifizierte und optimierte Bytecode-Programm. Wie genau
	 * die Nutzereingaben und die Ausgaben funktionieren sollen, wird dabei durch
	 * das übergebene Objekt 'io' beschrieben (io.read() für eine Eingabe, io.write
	 * für eine Ausgabe).
	 *
	 * @param io Implementierung von IO für Eingaben (READ) und Ausgaben (WRITE).
	 */
	public void run(IO io) {
		// Beginne mit der Simulation bei dem 'entryPoint'
		BasicBlock currentBlock = entryPoint;
		// Im Verifikationsschritt wurde festgehalten, auf welche Größe der Stack
		// maximal wachsen kann.
		// Lege nun genau so viele Felder auf dem Stack an.
		int[] stack = new int[maxStack];
		// Lege zudem ausreichend Platz für die Variablen an (Simulation von ALLOC).
		int[] variables = new int[localVariables];
		// Speichere stets, an welchem Index in 'stack' das aktuell oberste
		// Stack-Element liegt.
		// Da anfangs noch nichts auf dem Stack liegt, ist dieser Wert erstmal -1.
		int stackPtr = -1;
		// Speichere, ob (durch einen HALT-Befehl) das Programm angehalten wurde.
		boolean halted = false;

		// Solange nicht angehalten wurde, führe die Simulation fort.
		while (!halted) {
			// Merke stets, ob gesprungen wurde, um am Ende, falls nicht, einfach in den
			// nächsten Block übergehen zu können.
			boolean jumped = false;

			// Simuliere jede Instruktion in dem aktuellen Block nacheinander
			for (Instruction instruction : currentBlock.getInstructions()) {
				switch (instruction.type) {
				// Arithmetische Operationen
				case ADD -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr] + stack[stackPtr + 1];
				}
				case SUB -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr + 1] - stack[stackPtr];
				}
				case MUL -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr + 1] * stack[stackPtr];
				}
				case DIV -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr + 1] / stack[stackPtr];
				}
				case MOD -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr + 1] % stack[stackPtr];
				}
				case NEG -> {
					stack[stackPtr] = -stack[stackPtr];
				}

				// Boolesche Operationen
				case AND -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr + 1] * stack[stackPtr];
				}
				case OR -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr] + stack[stackPtr + 1];
					if (stack[stackPtr] == 2) {
						stack[stackPtr] = 1;
					}
				}
				case NOT -> {
					stack[stackPtr] = (stack[stackPtr] + 1) % 2;
				}

				// Vergleichs-Operationen
				case LESS -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr + 1] < stack[stackPtr] ? 1 : 0;
				}
				case LEQ -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr + 1] <= stack[stackPtr] ? 1 : 0;
				}
				case EQ -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr + 1] == stack[stackPtr] ? 1 : 0;
				}
				case NEQ -> {
					stackPtr--;
					stack[stackPtr] = stack[stackPtr + 1] != stack[stackPtr] ? 1 : 0;
				}

				// IO-Befehle
				case READ -> {
					stack[++stackPtr] = io.read();
				}
				case WRITE -> {
					io.write(stack[stackPtr--]);
				}

				// Konstanten
				case CONST -> {
					IntInstruction cnst = (IntInstruction) instruction;
					int value = cnst.getValue();
					stack[++stackPtr] = value;
				}
				case TRUE -> {
					stack[++stackPtr] = 1;
				}
				case FALSE -> {
					stack[++stackPtr] = 0;
				}

				// Laden/Speichern
				case LOAD -> {
					IntInstruction load = (IntInstruction) instruction;
					int value = load.getValue();
					stack[++stackPtr] = variables[value];
				}
				case STORE -> {
					IntInstruction store = (IntInstruction) instruction;
					int value = store.getValue();
					variables[value] = stack[stackPtr--];
				}

				// Stack-Befehle
				case POP -> {
					stackPtr--;
				}
				case DUP -> {
					stackPtr++;
					stack[stackPtr] = stack[stackPtr - 1];
				}
				case SWAP -> {
					int tmp = stack[stackPtr];
					stack[stackPtr] = stack[stackPtr - 1];
					stack[stackPtr - 1] = tmp;
				}

				// Kontrollfluss-Befehle
				case JUMP -> {
					jumped = true;
					BlockInstruction jump = (BlockInstruction) instruction;
					currentBlock = jump.target;
				}
				case FJUMP -> {
					int value = stack[stackPtr--];
					if (value == 0) {
						jumped = true;
						BlockInstruction jump = (BlockInstruction) instruction;
						currentBlock = jump.target;
					}
				}
				case HALT -> {
					halted = true;
				}
				}
			}

			// Wenn nicht in einen anderen Block gesprungen oder angehalten wurde, gehe
			// einfach zum nächsten Block weiter.
			if (!jumped && !halted) {
				currentBlock = currentBlock.next;
			}
		}
	}

	public BasicBlock getEntryPoint() {
		return entryPoint;
	}
}
