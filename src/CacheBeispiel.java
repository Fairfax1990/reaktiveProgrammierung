import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

public class CacheBeispiel {

    public static void main(String[] args) throws InterruptedException {

        Instant startZeit = Instant.now();

        Mono<Instant> hotObservable = Mono.just(rufeAktuelleZeitAbUndVerzoegere()).doOnNext(zeit -> {
            System.out.println("Just-Fall. Vergangene Zeit in Sekunden: " + (zeit.getEpochSecond() - startZeit.getEpochSecond()));
        });

        Flux<Instant> replayedObservable = Flux.fromIterable(List.of(rufeAktuelleZeitAbUndVerzoegere())).cache();


        replayedObservable.doOnNext(zeit -> {
            druckeVergangeneZeit(zeit, startZeit);
        }).subscribe();
        replayedObservable.doOnNext(zeit -> {
            druckeVergangeneZeit(zeit, startZeit);
        }).subscribe();
        replayedObservable.doOnNext(zeit -> {
            druckeVergangeneZeit(zeit, startZeit);
        }).subscribe();

        hotObservable.subscribe();
    }

    private static void druckeVergangeneZeit(Instant aktuelleZeit, Instant startZeit) {
        System.out.println("Cache-Fall. Vergangene Zeit in Sekunden: " + (aktuelleZeit.getEpochSecond() - startZeit.getEpochSecond()));
    }

    private static Instant rufeAktuelleZeitAbUndVerzoegere() throws InterruptedException {
        Thread.sleep(2000);
        return Instant.now();
    }
}
