import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

public class ReplayBeispiel {

    public static void main(String[] args) throws InterruptedException {

        Instant startTime = Instant.now();

        Mono<Instant> hotObservable = Mono.just(rufeAktuelleZeitAbUndVerzoegere()).doOnNext(zeit -> {
            System.out.println("Just-Fall. Vergangene Zeit in Sekunden: " + (zeit.getEpochSecond() - startTime.getEpochSecond()) );
        });

        Flux<Instant> replayedObservable = Flux.fromIterable(List.of(rufeAktuelleZeitAbUndVerzoegere()))
                .doOnNext(zeit -> {
                    System.out.println("Replay-Fall. Vergangene Zeit in Sekunden: " + (zeit.getEpochSecond() - startTime.getEpochSecond()) );
                });


        replayedObservable.subscribe();
        replayedObservable.subscribe();
        replayedObservable.subscribe();

        hotObservable.subscribe();
    }

    private static Instant rufeAktuelleZeitAbUndVerzoegere() throws InterruptedException {
        Thread.sleep(2000);
        return Instant.now();
    }
}
