import java.time.Instant;
import java.util.concurrent.*;

public class FuturesBeispielV1 {

    private static final ExecutorService executor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    private static long stoppUhr = 0;
    private static final Integer ZEIT_INKREMENT_IN_SEKUNDEN = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        aufgabeAusfuehren("1", stoppUhr);
        aufgabeAusfuehren("2", stoppUhr);
        aufgabeAusfuehren("3", stoppUhr);
        aufgabeAusfuehren("4", stoppUhr);
        aufgabeAusfuehren("5", stoppUhr);
        aufgabeAusfuehren("6", stoppUhr);

        executor.shutdown();
    }

    public static void aufgabeAusfuehren(String aufgabe, long vergangeneZeit) {
        executor.submit(() -> {

            long startZeit = Instant.now().getEpochSecond();

            System.out.println("Ausführung von mockAufgabe " + aufgabe
                    + ". \n Vergangene Zeit: " + vergangeneZeit
                    + " Sekunden" + "\n ----------");

            try {
                Thread.sleep(ZEIT_INKREMENT_IN_SEKUNDEN * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long ausfuehrungsZeit = Instant.now().getEpochSecond() - startZeit;
            stoppUhr = vergangeneZeit + ausfuehrungsZeit;
        });
    }
}
