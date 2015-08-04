package com.zy.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;


public class ChatClient extends Frame{
	Socket s = null;
	DataOutputStream dos = null;
	
	TextField tfTxt = new TextField();
	TextArea taCon = new TextArea();
	
	public static void main(String[] args){
		new ChatClient().launchFrame();
	}
	
	public void launchFrame(){
		setLocation(400, 300);
		this.setSize(300, 300);
		add(tfTxt,BorderLayout.SOUTH);
		add(taCon,BorderLayout.NORTH);
		pack();
		//���Ӵ��ڹرյĴ���ʹ�������ࣩ
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();
	}
	
	/**
	 * Client����Server
	 */
	public void connect(){
		try {
			s = new Socket("127.0.0.1",8888);//IP+�˿ں�
			dos = new DataOutputStream(s.getOutputStream());
System.out.println("connceted");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ر�����
	 */
	public void disconnect(){
		try {
			dos.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();//ȡ��tfTxt�е�����
			taCon.setText(str);//ȡ�������ݷ���taCon
			tfTxt.setText("");
			//������Ϣ��������
			try {
				dos.writeUTF(str);
				dos.flush();
				//dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	
}
