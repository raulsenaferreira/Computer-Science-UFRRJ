import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceLanchonete extends Remote {
	public void enviaMensagem(InterfaceMensagem msg) throws RemoteException;
}
