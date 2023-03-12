import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;

public class SchedulerLoesungBeispiel {

    private static long zeitReferenz;

    public static void main(String[] args) {

        zeitReferenz = Instant.now().getEpochSecond();

        var subscription1 = Mono.just("Max")
                .publishOn(Schedulers.boundedElastic()).map(SchedulerLoesungBeispiel::gruessePerson).subscribe();
        var subscription2 = Mono.just("Lisa")
                .publishOn(Schedulers.boundedElastic()).map(SchedulerLoesungBeispiel::gruessePerson).subscribe();
        var subscription3 = Mono.just("Peter")
                .publishOn(Schedulers.boundedElastic()).map(SchedulerLoesungBeispiel::gruessePerson).subscribe();

        while (!(subscription1.isDisposed() && subscription2.isDisposed() && subscription3.isDisposed())) {
            //Hält den Prozess aktiv, bis alle Subscriptions entweder ein Error-, oder ein Completed-Signal senden
        }
    }

    static String gruessePerson(String name) {
        return Mono.just(name)
                .delayElement(Duration.ofMillis(1000L))
                .doOnNext(s -> {
                    System.out.println("Hallo " + s + "! :-) \n" +
                            "Vergangene Zeit: " + (Instant.now().getEpochSecond() - zeitReferenz));
                })
                .block();
    }
}
