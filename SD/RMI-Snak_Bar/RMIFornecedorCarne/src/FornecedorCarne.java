import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FornecedorCarne  extends UnicastRemoteObject implements InterfaceLanchonete{

	private static final long serialVersionUID = 1L;

	protected FornecedorCarne() throws RemoteException {
		super();
	}

	public void enviaMensagem(InterfaceMensagem msg) throws RemoteException {
		int clock[]=msg.getRelogioVetorial();
		clock[2]++;// fornecedor de carne recebe mensagem do servidor
		//vai até o estoque pega o produto e entrega ao servidor
		clock[2]++;
		msg.setRelogioVetorial(clock);//fornecedor de carne envia mensagem para o servidor
	}
}
