import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class ServerFornecedorBread {
	public static void main(String[] argv) {
		try {
			System.setSecurityManager(new RMISecurityManager());

			FornecedorBread fornecedor = new FornecedorBread();
			Naming.rebind("rmi://localhost/bread", fornecedor);

			System.out.println("Fornecedor em funcionamento.");

		} catch (Exception e) {
			System.out.println("Fornecedor ainda está fechado: " + e);
		}
	}
}