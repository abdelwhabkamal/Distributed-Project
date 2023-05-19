import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ProjectRemote extends UnicastRemoteObject implements ProjectInterface {

    protected ProjectRemote() throws RemoteException {
        super();
    }

    @Override
    public int Count(String s) throws RemoteException {
        return s.replace("\n", "").replace("\r", "").length();
    }

    @Override
    public String longest(String s) throws RemoteException {
        s = s.replaceAll("[^a-zA-Z0-9]", " ");
        String longest = "";
        for (String word : s.split(" ")) {
            if (word.strip().length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }

    @Override
    public String shortest(String s) throws RemoteException {
        s = s.replaceAll("[^a-zA-Z0-9]", " ");
        String Shortest = "______________________________________________________";
        for (String word : s.split(" ")) {
            if (word.isBlank() || word.length()==1) continue;
            if (word.strip().length() < Shortest.length()) {
                Shortest = word;
            }
        }
        return Shortest;
    }
    @Override
    public Set<String> repeatedwords(String s) throws RemoteException {
        s = s.replaceAll("[^a-zA-Z0-9]", " ");
        Set<String> repeatedWords = new HashSet<>();
        String[] words = s.split(" ");
        for (int i = 0; i < words.length; i++) {
            if(words[i].isBlank() || words[i].length()==1) continue;
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].equals(words[j])) {
                    repeatedWords.add(words[i]);
                    break;
                }
            }
        }
        return repeatedWords;
    }

    @Override
    public HashMap<String, Integer> Repeat(String s){
        s = s.replaceAll("[^a-zA-Z0-9]", " ");
        String[] words = s.split(" ");
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            if(word.isBlank() || word.length()==1) continue;
            if (wordCount.containsKey(word)) wordCount.put(word, wordCount.get(word) + 1);
            else wordCount.put(word, 1);
        }
        return wordCount;
    }
}