import java.rmi.Remote;
import java.rmi.RemoteException;


public interface InterfaceMensagem extends Remote{
	public void setPedido(String pedido) throws RemoteException;
	public String getPedido() throws RemoteException; 
	
	public void setRelogioVetorial(int relogioVetorial[]) throws RemoteException; 
	public int[] getRelogioVetorial() throws RemoteException; 
}
