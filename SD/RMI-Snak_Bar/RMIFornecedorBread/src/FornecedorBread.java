import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FornecedorBread  extends UnicastRemoteObject implements InterfaceLanchonete{

	private static final long serialVersionUID = 1L;

	protected FornecedorBread() throws RemoteException {
		super();
	}

	public void enviaMensagem(InterfaceMensagem msg) throws RemoteException {
		int clock[]=msg.getRelogioVetorial();
		clock[1]++;//fornecedor de pão recebeu mensagem do servidor
		//vai até o estoque pega o produto e entrega ao servidor
		clock[1]++;//fornecedor de pão enviou mensagem para o servidor
		msg.setRelogioVetorial(clock);
	}
}
