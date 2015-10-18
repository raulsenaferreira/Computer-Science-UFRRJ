import java.rmi.RemoteException;

public class Mensagem implements InterfaceMensagem{

	private String pedido;
	private int relogioVetorial[];
	
	public void setPedido(String pedido) throws RemoteException{
		this.pedido = pedido;
	}
	public String getPedido() throws RemoteException{
		return this.pedido;
	}
	
	public void setRelogioVetorial(int relogioVetorial[]) throws RemoteException{
		this.relogioVetorial = relogioVetorial;
	}
	public int[] getRelogioVetorial() throws RemoteException{
		return this.relogioVetorial;
	}

}
