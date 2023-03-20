import reactor.core.publisher.Mono;

public class ObservableOhneObserver {
        public static void main(String[] args) {

        Mono.just("Hallo Welt")
                .doOnNext(System.out::println)
                .doOnSuccess(s -> System.out.println("Stream ohne Fehler durchlaufen"));

    }

}
