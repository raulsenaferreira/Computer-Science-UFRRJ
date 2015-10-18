import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class ServerFornecedorCarne {
	public static void main(String[] argv) {
		try {
			System.setSecurityManager(new RMISecurityManager());

			FornecedorCarne fornecedor = new FornecedorCarne();
			Naming.rebind("rmi://localhost/carne", fornecedor);

			System.out.println("Fornecedor em funcionamento.");

		} catch (Exception e) {
			System.out.println("Fornecedor ainda está fechado: " + e);
		}
	}
}