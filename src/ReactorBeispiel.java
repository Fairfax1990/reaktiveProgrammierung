import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ReactorBeispiel {

    private static long stoppUhr = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Map<String, Long> parameterMap = Map.of("1", 1000L,
                "2", 1000L,
                "3", 1000L,
                "4", 1000L,
                "5", aufgabeAusfuehren("callback 1", 5000L),
                "6", aufgabeAusfuehren("callback 2", 3000L));

        Flux<Long> executionTime = Flux.fromIterable(parameterMap.entrySet())
                .map(stringLongEntry -> {

                            try {

                                return (aufgabeAusfuehren(stringLongEntry.getKey(), stringLongEntry.getValue()));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return 0L;
                            }
                        }
                );

        var abc = executionTime.subscribeOn(Schedulers.boundedElastic()).subscribe();
        while(!abc.isDisposed()) {
            System.out.println("processing");
            Thread.sleep(1000);
        }

    }

    public static Long aufgabeAusfuehren(String aufgabe, Long bearbeitungsZeit) throws InterruptedException {
        long startZeit = Instant.now().getEpochSecond();
        System.out.println("Ausführung von mockAufgabe " + aufgabe
                + ". \n Vergangene Zeit: " + stoppUhr
                + " Sekunden");
        Thread.sleep(bearbeitungsZeit);
        long ausfuehrungsZeit = Instant.now().getEpochSecond() - startZeit;
        System.out.println("Ausführungszeit: " + ausfuehrungsZeit + "\n ----------");
        stoppUhr = stoppUhr + ausfuehrungsZeit;
        return stoppUhr;
    }
}
