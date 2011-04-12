import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame{
	
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	
	Socket s = null;
	DataOutputStream dos = null;
	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	public void launchFrame() {
		setLocation(400,300);
		this.setSize(300,300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				disConnect();
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TextFieldListener());
		setVisible(true);
		connect();
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1", 8999);
			dos = new DataOutputStream (s.getOutputStream());
System.out.println("connected!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disConnect() {
		try {
			dos.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private class TextFieldListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			String str = tfTxt.getText().trim();
			taContent.setText(str);
			tfTxt.setText("");
			
			try {
//				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeUTF(str);
				dos.flush();
	//			dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
