package pgdp.pingutrip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final public class PinguTrip {

	// To hide constructor in utility class.
	private PinguTrip() {
	}

	public static Stream<WayPoint> readWayPoints(String pathToWayPoints) {
		try {
			List<String> lines = Files.readAllLines(Path.of(pathToWayPoints));
			return lines.stream().filter(line -> !line.startsWith("//")).takeWhile(line -> !line.contentEquals("---"))
					.map(line -> WayPoint.ofString(line));
		} catch (IOException e) {
			return Stream.empty();
		}
	}

	public static Stream<OneWay> transformToWays(List<WayPoint> wayPoints) {
		return IntStream.range(0, wayPoints.size() - 1)
				.mapToObj(i -> new OneWay(wayPoints.get(i), wayPoints.get(i + 1)));
	}

	public static double pathLength(Stream<OneWay> oneWays) {
		return oneWays.map(way -> way.getLength()).reduce(0.0, (a, b) -> a + b);
	}

	public static List<OneWay> kidFriendlyTrip(List<OneWay> oneWays) {
		double avgLength = pathLength(oneWays.stream()) / oneWays.size();
		return oneWays.stream().takeWhile(way -> way.getLength() <= avgLength).collect(Collectors.toList());
	}

	public static WayPoint furthestAwayFromHome(Stream<WayPoint> wayPoints, WayPoint home) {
		return wayPoints.max(Comparator.comparingDouble(point -> point.distanceTo(home))).orElse(home);
	}

	public static boolean onTheWay(Stream<OneWay> oneWays, WayPoint visit) {
		return oneWays.anyMatch(way -> way.isOnPath(visit) == true);
	}

	public static String prettyDirections(Stream<OneWay> oneWays) {
		return oneWays.map(way -> way.prettyPrint()).reduce("", (a, b) -> a + b + "\n").trim();
	}

	public static void main(String[] args) {
		List<WayPoint> wayPoints = readWayPoints("test_paths/path.txt").toList();
//		List.of(new WayPoint(4.0, 11.5), new WayPoint(19.1, 3.2));
		System.out.println(wayPoints);
		List<OneWay> oneWays = transformToWays(wayPoints).toList();
		// List.of(new OneWay(new WayPoint(4.0, 11.5), new WayPoint(19.1, 3.2)));
		System.out.println(oneWays);
		double length = pathLength(oneWays.stream());
		// 17.230 ...
		System.out.println(length);
		List<OneWay> kidFriendly = kidFriendlyTrip(oneWays);
		// List.of(new OneWay(new WayPoint(4.0, 11.5), new WayPoint(19.1, 3.2)));
		System.out.println(kidFriendly);
		WayPoint furthest = furthestAwayFromHome(wayPoints.stream(), wayPoints.get(0));
		// new WayPoint(19.1, 3.2);
		System.out.println(furthest);
		boolean onTheWay = onTheWay(oneWays.stream(), new WayPoint(0.0, 0.0));
		// false
		System.out.println(onTheWay);
//		onTheWay = onTheWay(oneWays.stream(), new WayPoint(19.1, 3.2));
		// true

		String directions = prettyDirections(oneWays.stream());
		// "25 Schritte Richtung 331 Grad."
		System.out.println(directions);

		Stream s = Stream.of(new OneWay(new WayPoint(0.0, 0.0), new WayPoint(1.0, 0.0)),
				new OneWay(new WayPoint(1.0, 0.0), new WayPoint(3.0, 0.0)),
				new OneWay(new WayPoint(3.0, 0.0), new WayPoint(4.0, 1.0)));
		String dir = prettyDirections(s);
		System.out.println(dir);
	}

}
