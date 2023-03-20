import java.time.Instant;
import java.util.concurrent.*;

public class FuturesBeispielV4 {

    private static final ExecutorService executor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    private static long stoppUhr = 0;
    private static final Integer ZEIT_INKREMENT_IN_SEKUNDEN = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Future<Long> future1 = aufgabeAusfuehren("1", stoppUhr);
        Future<Long> future2 = aufgabeAusfuehren("2", stoppUhr, 7);
        Future<Long> future3 = aufgabeAusfuehren("3", stoppUhr, 5);
        Future<Long> future4 = aufgabeAusfuehren("4", stoppUhr);
        Future<Long> future5 = aufgabeAusfuehren("5", future2.get());
        Future<Long> future6 = aufgabeAusfuehren("6", future3.get());

        executor.shutdown();
    }

    public static Future<Long> aufgabeAusfuehren(String aufgabe, long vergangeneZeit, Integer bearbeitungsZeit) {
        return executor.submit(() -> {

            long startZeit = Instant.now().getEpochSecond();

            System.out.println("Ausführung von mockAufgabe " + aufgabe + ". \n Vergangene Zeit: " + stoppUhr + " Sekunden" + "\n ----------");

            Thread.sleep(bearbeitungsZeit * 1000);

            long ausfuehrungsZeit = Instant.now().getEpochSecond() - startZeit;
            stoppUhr = vergangeneZeit + ausfuehrungsZeit;
            return stoppUhr;
        });
    }

    public static Future<Long> aufgabeAusfuehren(String aufgabe, long vergangeneZeit) {
        return aufgabeAusfuehren(aufgabe, vergangeneZeit, ZEIT_INKREMENT_IN_SEKUNDEN);
    }
}
