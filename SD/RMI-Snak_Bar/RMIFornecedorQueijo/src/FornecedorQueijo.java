import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FornecedorQueijo  extends UnicastRemoteObject implements InterfaceLanchonete{

	private static final long serialVersionUID = 1L;

	protected FornecedorQueijo() throws RemoteException {
		super();
	}

	public void enviaMensagem(InterfaceMensagem msg) throws RemoteException {
		int clock[]=msg.getRelogioVetorial();
		clock[3]++;//fornecedor de queijo recebeu mensagem
		//vai até o estoque pega o produto e entrega ao servidor
		clock[3]++;//fornecedor de queijo envia mensagem para o servidor
		msg.setRelogioVetorial(clock);
	}
}
