import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.time.Instant;

public class ThreadClient {

    public static void main(String[] args) {
        Instant startTime = Instant.now();
        System.out.println("Start Time : " + startTime);
        try {
            String filePath = "E:\\data.txt";
            String data = new String(Files.readAllBytes(Paths.get(filePath)));

            Registry registry = LocateRegistry.getRegistry();
            ProjectInterface stub = (ProjectInterface) registry.lookup("obj");
            Thread longestWordThread = new Thread(() -> {
                try {
                    System.out.println("Longest Word Is : " + stub.longest(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread shortestWordThread = new Thread(() -> {
                try {
                    System.out.println("Shortest Word Is : " + stub.shortest(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread countThread = new Thread(() -> {
                try {
                    System.out.println("Number Of Letters Is : "  + stub.Count(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread repeatedWordsThread = new Thread(() -> {
                try {
                    System.out.println("The Repeated Words are  : "  + stub.repeatedwords(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread ReapetationThread = new Thread(() -> {
                try {
                    System.out.println("The Repetition Number Of Each Word Is : " + stub.Repeat(data));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            longestWordThread.start();
            shortestWordThread.start();
            countThread.start();
            repeatedWordsThread.start();
            ReapetationThread.start();

            // Wait for all threads to finish
            try {
                longestWordThread.join();
                shortestWordThread.join();
                countThread.join();
                repeatedWordsThread.join();
                ReapetationThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
        Instant endTime = Instant.now();

        System.out.println("Total Threaded Time Is : " + Duration.between(startTime, endTime).toMillis() + "ms");

    }
}
