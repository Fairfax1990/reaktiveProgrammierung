import reactor.core.publisher.Mono;

public class SubscribeBeispiel {

    public static void main(String[] args) {

        Mono<String> GrussObservable = Mono.just("Hallo Welt")
                .doOnNext(System.out::println)
                .doOnSuccess(s -> System.out.println("Stream ohne Fehler durchlaufen"));

        GrussObservable.subscribe();
    }
}
