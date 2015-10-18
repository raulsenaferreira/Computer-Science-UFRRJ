import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Lanchonete extends UnicastRemoteObject implements InterfaceLanchonete {

	private static final long serialVersionUID = 1L;

	public Lanchonete() throws RemoteException {}

	public static void imprimeVetor(int relogio[], String pedido){
		System.out.print("\n"+pedido+": ");
		
		for (int i = 0; i < relogio.length; i++) {
			System.out.print(" "+relogio[i]);
		}
	}
	
	public void enviaMensagem(InterfaceMensagem request) throws RemoteException {
		
			InterfaceLanchonete lanchonete;
			int clock[]=request.getRelogioVetorial();
			int idCliente=Integer.parseInt(request.getPedido());
			
			clock[4]++;//servidor recebeu mensagem do cliente
			
			try {
	  		        System.setSecurityManager(new RMISecurityManager());
	  		        lanchonete = (InterfaceLanchonete)Naming.lookup("rmi://localhost/queijo");
		  		    
		  		    request.setPedido("Cliente["+idCliente+"](queijo)");
		  		    clock[4]++;//servidor envia mensagem para o fornecedor de queijo
		  		    request.setRelogioVetorial(clock);
		  		    lanchonete.enviaMensagem(request);
		  		    
		  		    clock=request.getRelogioVetorial();
		  		    clock[4]++;//servidor recebe mensagem do fornecedor de queijo
		  		    request.setRelogioVetorial(clock);
		  		    imprimeVetor(clock, request.getPedido());
			}
			catch (Exception e) {
					System.out.println("Aconteceu algo de inesperado com o pedido de queijo da lanchonete: " + e);
			}
			
			try {
				request.setPedido("");
  		        System.setSecurityManager(new RMISecurityManager());
  		        lanchonete = (InterfaceLanchonete)Naming.lookup("rmi://localhost/bread");
  		        
	  		    request.setPedido("Cliente["+idCliente+"](plus vita)");
	  		    clock=request.getRelogioVetorial();
	  		 	clock[4]++;//servidor envia mensagem para o fornecedor de pão
	  		 	request.setRelogioVetorial(clock);
	  		    lanchonete.enviaMensagem(request);
	  		    
	  		    clock=request.getRelogioVetorial();
	  		    clock[4]++;//servidor recebe mensagem do fornecedor de  pão
	  		    request.setRelogioVetorial(clock);
	  		    imprimeVetor(clock, request.getPedido());
			}
			catch (Exception e) {
					System.out.println("Aconteceu algo de inesperado com o pedido de pão de hamburguer da lanchonete: " + e);
			}
			
			try {
				request.setPedido("");
  		        System.setSecurityManager(new RMISecurityManager());
  		        lanchonete = (InterfaceLanchonete)Naming.lookup("rmi://localhost/carne");
  		        
	  		    request.setPedido("Cliente["+idCliente+"](carne)");
	  		    clock=request.getRelogioVetorial();
	  		    clock[4]++;//servidor envia mensagem para o fornecedor de carne
	  		    request.setRelogioVetorial(clock);
	  		    lanchonete.enviaMensagem(request);
	  		    
	  		    clock=request.getRelogioVetorial();
	  		    clock[4]++;//servidor recebe mensagem do fornecedor de carne
	  		    request.setRelogioVetorial(clock);
	  		    imprimeVetor(clock, request.getPedido());
			}
			catch (Exception e) {
					System.out.println("Aconteceu algo de inesperado com o pedido de hamburguer da lanchonete: " + e);
			}
			
		
	}
}
