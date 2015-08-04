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
		//增加窗口关闭的处理（使用匿名类）
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
	 * Client连接Server
	 */
	public void connect(){
		try {
			s = new Socket("127.0.0.1",8888);//IP+端口号
			dos = new DataOutputStream(s.getOutputStream());
System.out.println("connceted");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭连接
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
			String str = tfTxt.getText().trim();//取出tfTxt中的内容
			taCon.setText(str);//取出的内容放在taCon
			tfTxt.setText("");
			//发送信息到服务器
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
