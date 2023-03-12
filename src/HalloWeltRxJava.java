import io.reactivex.rxjava3.core.Flowable;

import java.util.List;

public class HalloWeltRxJava {

    public static void main(String[] args) {

        List<String> einigeWoerter = List.of(
                "Guten Tag",
                "Grüß Gott",
                "Hallo"
        );

        Flowable.fromIterable(einigeWoerter)
                .filter(wort -> wort.equals("Hallo"))
                .single("Default, falls Downstream kein Element ankommt")
                .map(HalloWeltRxJava::fuegeWeltZumHalloHinzu)
                .doOnSuccess(HalloWeltRxJava::sagHalloZurWelt)
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

