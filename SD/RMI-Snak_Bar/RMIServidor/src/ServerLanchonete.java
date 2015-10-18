import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class ServerLanchonete {
	public static void main(String[] argv) {
		try {
			System.setSecurityManager(new RMISecurityManager());

			Lanchonete lanchonete = new Lanchonete();
			Naming.rebind("rmi://localhost/lanchonete", lanchonete);

			System.out.println("Lanchonete está aberta.");

		} catch (Exception e) {
			System.out.println("Lanchonete ainda não abriu: " + e);
		}
	}
}