import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

public class Schedulerbeispiel {

    private static long zeitReferenz;

    public static void main(String[] args) {


        zeitReferenz = Instant.now().getEpochSecond();

        Mono.just("Max").map(Schedulerbeispiel::gruessePerson).subscribe();
        Mono.just("Lisa").map(Schedulerbeispiel::gruessePerson).subscribe();
        Mono.just("Peter").map(Schedulerbeispiel::gruessePerson).subscribe();

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
