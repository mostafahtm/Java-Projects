package pgdp.trains.processing;

import pgdp.trains.connections.Station;
import pgdp.trains.connections.TrainConnection;
import pgdp.trains.connections.TrainStop;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProcessing {

    public static Stream<TrainConnection> cleanDataset(Stream<TrainConnection> connections) {
       return connections.distinct()
               .sorted(Comparator.comparing(connection -> connection.getFirstStop().scheduled()))
               .map(connection -> connection.withUpdatedStops(connection.stops().stream().filter(stop -> !stop.kind().equals(TrainStop.Kind.CANCELLED)).collect(Collectors.toList())));
    }

    public static TrainConnection worstDelayedTrain(Stream<TrainConnection> connections) {
        return connections.max(Comparator.comparingInt(conn -> conn.stops().stream().mapToInt(stop -> stop.getDelay()).max().orElse(0)))
                .orElse(null);
    }

    public static double percentOfKindStops(Stream<TrainConnection> connections, TrainStop.Kind kind) {
        List<TrainConnection> collect = connections.collect(Collectors.toList()) ;
        double numberOfAllStops = collect.stream().flatMap(connection -> connection.stops().stream())
                .count();
        double numberOfThatKindOfStop =  collect.stream().flatMap(connection -> connection.stops().stream())
                .filter(stop -> stop.kind().equals(kind))
                .count();
        return (numberOfThatKindOfStop / numberOfAllStops) * 100.0;
    }

    public static double averageDelayAt(Stream<TrainConnection> connections, Station station) {
        List<TrainConnection> collect = connections.collect(Collectors.toList());
       double totalDelay= collect.stream().flatMap(connection -> connection.stops().stream())
                .filter(stop -> stop.station().equals(station))
                .mapToDouble(stop -> stop.getDelay())
                .sum();
       double connectionsWithStopAtThatStation = collect.stream()
                         .filter(conn -> conn.stops().stream()
                        .anyMatch(stop -> stop.station().equals(station)))
                         .count();
        return totalDelay/ connectionsWithStopAtThatStation;
    }

    public static Map<String, Double> delayComparedToTotalTravelTimeByTransport(Stream<TrainConnection> connections) {
        Map<String, List<TrainConnection>> groupedByTypeMap = connections.collect(Collectors.groupingBy(connection -> connection.type()));

         return groupedByTypeMap.entrySet().stream().collect(Collectors.toMap(
                element -> element.getKey(),
                element -> {
                   List<TrainConnection> trainconns = element.getValue();
                            double totalSchedueldTime = trainconns.stream().mapToDouble(conn -> conn.totalTimeTraveledScheduled()).sum();
                            double totalActualTime =
                                    trainconns.stream().mapToDouble(conn -> (conn.totalTimeTraveledActual()-conn.totalTimeTraveledScheduled()) < 0 ? conn.totalTimeTraveledScheduled() : conn.totalTimeTraveledActual()).sum();
//
                            return (Double.isNaN(100.0* (totalActualTime-totalSchedueldTime)  / totalActualTime) ? 0.0 :100.0* (totalActualTime-totalSchedueldTime)  / totalActualTime);
                }
        ));
//       Map<String, Integer> EachConnectionWithItsDelay = connections.collect(Collectors.toMap(
//                connection -> connection.type(),
//                connection -> (connection.totalTimeTraveledActual()) - (connection.totalTimeTraveledScheduled())
//        ));
//       Map<Object, List<Map.Entry<String, Integer>>> groupedMap = EachConnectionWithItsDelay.entrySet().stream().collect(Collectors.groupingBy(connection -> connection.getKey()));
    }

    public static Map<Integer, Double> averageDelayByHour(Stream<TrainConnection> connections) {
        Map<Integer, List<TrainStop>> collect = connections.flatMap(connection -> connection.stops().stream())
                .collect(Collectors.groupingBy(stop -> stop.actual().getHour()));

        return collect.entrySet().stream().collect(Collectors.toMap(
                element -> element.getKey(),
                element -> {
                 List<TrainStop> stops = element.getValue();
                double totalDelay= stops.stream().mapToDouble(stop -> stop.getDelay()).sum();
                return totalDelay / stops.size();
                }
        ));

    }

    public static void main(String[] args) {
        // Um alle Verbindungen aus einer Datei zu lesen, verwendet DataAccess.loadFile("path/to/file.json"), etwa:
        // Stream<TrainConnection> trainConnections = DataAccess.loadFile("connections_test/fullDay.json");

        // Oder alternativ über die API, dies aber bitte sparsam verwenden, um die API nicht zu überlasten.
        //Stream<TrainConnection> trainsMunich = DataAccess.getDepartureBoardNowFor(Station.MUENCHEN_HBF);

        List<TrainConnection> trainConnections = List.of(
                new TrainConnection("ICE 2", "ICE", "2", "DB", List.of(
                        new TrainStop(Station.MUENCHEN_HBF,
                                LocalDateTime.of(2022, 12, 1, 11, 0),
                                LocalDateTime.of(2022, 12, 1, 11, 0),
                                TrainStop.Kind.REGULAR),
                        new TrainStop(Station.NUERNBERG_HBF,
                                LocalDateTime.of(2022, 12, 1, 11, 30),
                                LocalDateTime.of(2022, 12, 1, 12, 0),
                                TrainStop.Kind.REGULAR)
                )),
                new TrainConnection("ICE 1", "ICE", "1", "DB", List.of(
                        new TrainStop(Station.MUENCHEN_HBF,
                                LocalDateTime.of(2022, 12, 1, 10, 0),
                                LocalDateTime.of(2022, 12, 1, 10, 0),
                                TrainStop.Kind.REGULAR),
                        new TrainStop(Station.NUERNBERG_HBF,
                                LocalDateTime.of(2022, 12, 1, 10, 30),
                                LocalDateTime.of(2022, 12, 1, 10, 30),
                                TrainStop.Kind.REGULAR)
                )),
                new TrainConnection("ICE 3", "ICE", "3", "DB", List.of(
                        new TrainStop(Station.MUENCHEN_HBF,
                                LocalDateTime.of(2022, 12, 1, 12, 0),
                                LocalDateTime.of(2022, 12, 1, 12, 0),
                                TrainStop.Kind.REGULAR),
                        new TrainStop(Station.AUGSBURG_HBF,
                                LocalDateTime.of(2022, 12, 1, 12, 20),
                                LocalDateTime.of(2022, 12, 1, 13, 0),
                                TrainStop.Kind.CANCELLED),
                        new TrainStop(Station.NUERNBERG_HBF,
                                LocalDateTime.of(2022, 12, 1, 13, 30),
                                LocalDateTime.of(2022, 12, 1, 13, 30),
                                TrainStop.Kind.REGULAR)
                ))
        );

        List<TrainConnection> cleanDataset = cleanDataset(trainConnections.stream()).toList();
        // cleanDataset sollte sortiert sein: [ICE 1, ICE 2, ICE 3] und bei ICE 3 sollte der Stopp in AUGSBURG_HBF
        // nicht mehr enthalten sein.
//        for( TrainConnection element : cleanDataset)
//        System.out.println(element);

        TrainConnection worstDelayedTrain = worstDelayedTrain(trainConnections.stream());
        // worstDelayedTrain sollte ICE 3 sein. (Da der Stop in AUGSBURG_HBF mit 40 Minuten Verspätung am spätesten ist.)
//        System.out.println(worstDelayedTrain);

        double percentOfKindStops = percentOfKindStops(trainConnections.stream(), TrainStop.Kind.CANCELLED);
        // percentOfKindStops REGULAR sollte 85.71428571428571 sein, CANCELLED 14.285714285714285.
//        System.out.println(percentOfKindStops);

        double averageDelayAt = averageDelayAt(trainConnections.stream(), Station.AUGSBURG_HBF);
        // averageDelayAt sollte 10.0 sein. (Da dreimal angefahren und einmal 30 Minuten Verspätung).
//        System.out.println(averageDelayAt);

        Map<String, Double> delayCompared = delayComparedToTotalTravelTimeByTransport(trainConnections.stream());
        // delayCompared sollte ein Map sein, die für ICE den Wert 16.666666666666668 hat.
        // Da ICE 2 0:30 geplant hatte, aber 1:00 gebraucht hat, ICE 1 0:30 geplant und gebraucht hatte, und
        // ICE 3 1:30 geplant und gebraucht hat. Zusammen also 2:30 geplant und 3:00 gebraucht, und damit
        // (3:00 - 2:30) / 3:00 = 16.666666666666668.
//        System.out.println(delayCompared);

        Map<Integer, Double> averageDelayByHourOfDay = averageDelayByHour(trainConnections.stream());
        // averageDelayByHourOfDay sollte ein Map sein, die für 10, 11 den Wert 0.0 hat, für 12 den Wert 15.0 und
        // für 13 den Wert 20.0.
        System.out.println(averageDelayByHourOfDay);
    }


}
