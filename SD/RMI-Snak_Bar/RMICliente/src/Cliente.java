import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.server.RemoteStub;
import java.rmi.server.UnicastRemoteObject;
 
public class Cliente implements Runnable {
	int relogioVetorialGlobal[];
	int clockProcesso[];
	private int id;
	
	public int[] getClockProcesso() {
		return clockProcesso;
	}

	public void setClockProcesso(int[] clockProcesso) {
		this.clockProcesso = clockProcesso;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void run() {
		int id=getId();
		for (int i = 0; i < 3; i++) {
			setClockProcesso(execute (getClockProcesso(), id));
		}
	}
	
	public static void main (String[] args) throws InterruptedException {
		//Cliente, Pão, Carne, Queijo e Lanchonete
		int clockInicial[]={1,0,0,0,0};
		
		//Cliente 1
		Cliente cliente = new Cliente();
		cliente.setId(1);
		cliente.setClockProcesso(clockInicial);
		
		Thread threadCliente = new Thread(cliente);
		threadCliente.start();
		Thread.sleep((int) (Math.random() * 1000));
		
		//Cliente 2
		Cliente cliente2 = new Cliente();
		cliente2.setId(2);
		cliente2.setClockProcesso(clockInicial);
		
		Thread threadCliente2 = new Thread(cliente2);
		threadCliente2.start();
		Thread.sleep((int) (Math.random() * 1000));
		
		//Cliente 3
		Cliente cliente3 = new Cliente();
		cliente3.setId(3);
		cliente3.setClockProcesso(clockInicial);
		
		Thread threadCliente3 = new Thread(cliente3);
		threadCliente3.start();
		Thread.sleep((int) (Math.random() * 1000));
	}
	
	public int[] execute (int clockProcesso[], int idCliente) {
		InterfaceLanchonete cliente;
		InterfaceMensagem request = new Mensagem();
		
		try {
  		        System.setSecurityManager(new RMISecurityManager());
  		        cliente = (InterfaceLanchonete)Naming.lookup("rmi://localhost/lanchonete");
  		        RemoteStub stubInterfaceMensagem = UnicastRemoteObject.exportObject(request);
  		       
  		        request.setPedido(String.valueOf(idCliente));
				request.setRelogioVetorial(getClockProcesso());
				
				cliente.enviaMensagem(request);
				
				request.getRelogioVetorial()[0]++;//Cliente recebe mensagem
				
				clockProcesso = request.getRelogioVetorial();
		}
		catch (Exception e) {
				System.out.println("Aconteceu algo de inesperado com o cliente: " + e);
		}
		return clockProcesso;
	}
}