import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.time.Instant;

public class Client {

    private Client() {
    }

    public static void main(String[] args) {
        try {
            Instant startTime = Instant.now();

            System.out.println("Start Time : " + startTime);

            String filePath = "E:\\data.txt";
            String data = new String(Files.readAllBytes(Paths.get(filePath)));

            // Getting the registry
            Registry registry = LocateRegistry.getRegistry();

            // Looking up the registry for the remote object
            ProjectInterface stub = (ProjectInterface) registry.lookup("obj");

            System.out.println("Longest Word Is : "  + stub.longest(data));

            System.out.println("----------------------------");

            System.out.println("Shortest Word Is : " + stub.shortest(data));

            System.out.println("----------------------------");

            System.out.println("Number Of Letters Is : " + stub.Count(data));

            System.out.println("----------------------------");

            System.out.println("The Repeated Words are  : "  + stub.repeatedwords(data));

            System.out.println("----------------------------");

            System.out.println("The Repetition Number Of Each Word Is : " + stub.Repeat(data));

            System.out.println("----------------------------");


            Instant endTime = Instant.now();
            System.out.println("End Time : " + endTime);

            System.out.println("----------------------------");

            System.out.println("Total Non-Threaded Time Is : " + Duration.between(startTime, endTime).toMillis() + "ms");

            System.out.println("----------------------------");

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }


    }

}
