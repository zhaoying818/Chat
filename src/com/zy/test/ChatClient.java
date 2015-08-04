package com.zy.test;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ChatClient extends Frame{
	
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
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
	}
	
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String s = tfTxt.getText().trim();//ȡ��tfTxt�е�����
			taCon.setText(s);//ȡ�������ݷ���taCon
			tfTxt.setText("");
		}
		
	}
	
	
}
