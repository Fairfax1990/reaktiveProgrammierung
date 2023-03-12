import reactor.core.publisher.Flux;

import java.util.List;

public class HalloWeltReactor {

    public static void main(String[] args) {

        List<String> einigeWoerter = List.of(
                "Guten Tag",
                "Grüß Gott",
                "Hallo"
        );

        Flux.fromIterable(einigeWoerter)
                .filter(wort -> wort.equals("Hallo"))
                .single()
                .map(HalloWeltReactor::fuegeWeltZumHalloHinzu)
                .doOnSuccess(HalloWeltReactor::sagHalloZurWelt)
                .doOnError(throwable -> System.out.println("Ein Fehler ist aufgetreten: " + throwable.getMessage()))
                .subscribe();
    }

    private static String fuegeWeltZumHalloHinzu(String hallo) {
        return hallo + " Welt";
    }

    private static void sagHalloZurWelt(String halloWelt) {
        System.out.println(halloWelt);
    }
}
