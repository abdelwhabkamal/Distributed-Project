import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        String filePath = "E:\\data.txt";
        String data = new String(Files.readAllBytes(Paths.get(filePath)));
        ProjectRemote projectRemote = new ProjectRemote();
        System.out.println(projectRemote.repeatedwords(data));
    }
}