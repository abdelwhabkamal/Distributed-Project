
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends ProjectRemote {

    protected Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            ProjectRemote obj = new ProjectRemote();
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("obj", obj);
            System.out.println("Server ready");
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "server error", ex);
        }

    }

}
