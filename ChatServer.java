import java.io.*;
import java.net.*;


public class ChatServer {

	boolean started = false;
	ServerSocket ss = null;
	
	public static void main(String[] args) {
		new ChatServer().start();
	}

	public void start() {
			
		try {
			ss = new ServerSocket(8999);
		} catch (BindException e){
			System.out.println("�˿�ʹ����...");
			System.out.println("��ص���س����������з�������");
			System.exit(0);
		} catch (IOException e){
			e.printStackTrace();
		}
		try {
			started = true;
			while (started) {
				
				Socket s = ss.accept();
				Client c = new Client (s);
System.out.println("a client connected!");
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class Client implements Runnable {

		private Socket s = null;
		private DataInputStream dis = null;
		private boolean bConnected = false;
		
		public Client (Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				bConnected = true;
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		public void run() {			
				try {
					while(bConnected){
						String str = dis.readUTF();
						System.out.println(str);
					}

				} catch (EOFException e) {
					System.out.println("Client closed!");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if(dis != null) dis.close();
						if(s != null) s.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		}
		
}

