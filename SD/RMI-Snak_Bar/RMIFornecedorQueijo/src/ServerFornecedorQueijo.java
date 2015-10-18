import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class ServerFornecedorQueijo {
	public static void main(String[] argv) {
		try {
			System.setSecurityManager(new RMISecurityManager());

			FornecedorQueijo fornecedor = new FornecedorQueijo();
			Naming.rebind("rmi://localhost/queijo", fornecedor);

			System.out.println("Fornecedor em funcionamento.");

		} catch (Exception e) {
			System.out.println("Fornecedor ainda está fechado: " + e);
		}
	}
}