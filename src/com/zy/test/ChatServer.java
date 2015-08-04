package com.zy.test;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;

public class ChatServer {
	
	public static void main(String[] args){
		boolean started = false;  //表示Server是否启动
		ServerSocket ss = null;
		Socket s = null;
		DataInputStream dis = null;
		try {
			ss = new ServerSocket(8888);
		} catch (BindException e) {
			System.out.println("Port is using...");
			System.out.println("请关掉相关程序，并重新运行服务器");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			started = true; //Server启动
			while(started) {
				boolean bConnected = false;//客户端是否连接上
				s = ss.accept();
System.out.println("a client connected!");
                bConnected = true;//客户端连接上
                dis = new DataInputStream(s.getInputStream());
                while(bConnected){
                	 String str = dis.readUTF();
                	 System.out.println(str);
                }
                //dis.close();
			}
		} catch (EOFException e){
			System.out.println("Client closed!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(dis!=null) 
					dis.close();
				if(s!=null) 
					s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
