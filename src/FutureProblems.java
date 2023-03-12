import java.util.concurrent.*;

public class FutureProblems {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
        try {
            // get f3 with dependent result from f1
            Future<String> f1 = executor.submit(new CallToRemoteServiceA());
            Future<String> f3 = executor.submit(new CallToRemoteServiceC(f1.get()));

            /* The work below can not proceed until f1.get() completes even though there is no dependency */

            // also get f4/f5 after dependency f2 completes
            Future<Integer> f2 = executor.submit(new CallToRemoteServiceB());
            Future<Integer> f4 = executor.submit(new CallToRemoteServiceD(f2.get()));
            Future<Integer> f5 = executor.submit(new CallToRemoteServiceE(f2.get()));

            System.out.println(f3.get() + " => " + (f4.get() * f5.get()));
        } finally {
            executor.shutdownNow();
        }
    }

    private static final class CallToRemoteServiceA implements Callable<String> {
        @Override
        public String call() throws Exception {
            // simulate fetching data from remote service
            System.out.println("Inside Call A");
            Thread.sleep(100);
            return "responseA";
        }
    }

    private static final class CallToRemoteServiceB implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            // simulate fetching data from remote service
            System.out.println("Inside Call B");
            Thread.sleep(40);
            return 100;
        }
    }

    private static final class CallToRemoteServiceC implements Callable<String> {

        private final String dependencyFromA;

        public CallToRemoteServiceC(String dependencyFromA) {
            this.dependencyFromA = dependencyFromA;
        }

        @Override
        public String call() throws Exception {
            // simulate fetching data from remote service
            System.out.println("Inside Call C");
            Thread.sleep(60);
            return "responseB_" + dependencyFromA;
        }
    }

    private static final class CallToRemoteServiceD implements Callable<Integer> {

        private final Integer dependencyFromB;

        public CallToRemoteServiceD(Integer dependencyFromB) {
            this.dependencyFromB = dependencyFromB;
        }

        @Override
        public Integer call() throws Exception {
            // simulate fetching data from remote service
            System.out.println("Inside Call D");
            Thread.sleep(140);
            return 40 + dependencyFromB;
        }
    }

    private static final class CallToRemoteServiceE implements Callable<Integer> {

        private final Integer dependencyFromB;

        public CallToRemoteServiceE(Integer dependencyFromB) {
            this.dependencyFromB = dependencyFromB;
        }

        @Override
        public Integer call() throws Exception {
            // simulate fetching data from remote service
            System.out.println("Inside Call E");
            Thread.sleep(55);
            return 5000 + dependencyFromB;
        }
    }
}
