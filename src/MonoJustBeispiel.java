import reactor.core.publisher.Mono;

public class MonoJustBeispiel {


    public static void main(String[] args) throws InterruptedException {


        Mono<Double> hotObservable = Mono.just(generiereZufallszahl()).doOnNext(nummer -> {
            System.out.println("Zufallszahl: " + nummer);
        });

        //Observer 1
        hotObservable.subscribe();
        //Observer 2
        hotObservable.subscribe();
        //Observer 3
        hotObservable.subscribe();
    }

    private static double generiereZufallszahl() throws InterruptedException {;
        return Math.random();
    }
}
