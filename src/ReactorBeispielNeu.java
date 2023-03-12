import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReactorBeispielNeu {

    private static long zeitReferenz;
    private static long kurzeBearbeitungszeit = 5000L;
    private static long langeBearbeitungszeit = 7000L;

    public static void main(String[] args) {

        Mono<String> aufgabe1 = aufgabeAusfuehren("1", Mono.just(""), kurzeBearbeitungszeit);
        Mono<String> aufgabe2 = aufgabeAusfuehren("2", Mono.just(""), langeBearbeitungszeit);
        Mono<String> aufgabe3 = aufgabeAusfuehren("3", aufgabe1);
        Mono<String> aufgabe4 = aufgabeAusfuehren("4", Mono.just(""));
        Mono<String> aufgabe5 = aufgabeAusfuehren("5", aufgabe2);
        Mono<String> aufgabe6 = aufgabeAusfuehren("6", Mono.just(""));

        List<Mono<String>> alleAufgaben = List.of(aufgabe1, aufgabe2, aufgabe3, aufgabe4, aufgabe5, aufgabe6);

        zeitReferenz = Instant.now().getEpochSecond();

        Flux<String> mockaufgabenStream = Flux.fromIterable(alleAufgaben)
                .flatMap(stringMono -> stringMono);

        var subscription = mockaufgabenStream.subscribe();

        while (!subscription.isDisposed()) {
        }

    }

    public static Mono<String> aufgabeAusfuehren(String aufgabe, Mono<String> referenz) {
        return aufgabeAusfuehren(aufgabe, referenz, 0L);
    }

    public static Mono<String> aufgabeAusfuehren(String aufgabe, Mono<String> referenz, Long bearbeitungsZeit) {
        return referenz
                .map(aString -> {

                    long benoetigteZeit = Instant.now().getEpochSecond() - zeitReferenz;

                    String aufgabenReferenz = aString.isEmpty() ? aufgabe : "Von " + aString
                            + " abhängiger Prozess " + aufgabe;

                    System.out.println("mockAufgabe " + aufgabenReferenz + " ausgeführt."
                            + ". \n Vergangene Zeit: " + benoetigteZeit
                            + " Sekunden");

                    return aufgabenReferenz;
                })
                .delayElement(Duration.of(bearbeitungsZeit, ChronoUnit.MILLIS))
                .cache();
    }
}
