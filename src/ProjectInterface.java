import java.rmi.Remote;
import java.util.Set;
import java.util.HashMap;

public interface ProjectInterface extends Remote {
    public int Count(String s) throws Exception;

    public String longest(String s) throws Exception;

    public String shortest(String s) throws Exception;

    public Set<String> repeatedwords(String s) throws Exception;

    public HashMap<String, Integer> Repeat(String s) throws Exception;

}
