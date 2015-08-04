package com.zy.test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class ChatServer {
	
	public static void main(String[] args){
		boolean started = false;  //表示Server是否启动
		try {
			ServerSocket ss = new ServerSocket(8888);
			started = true; //Server启动
			while(started) {
				boolean bConnected = false;//客户端是否连接上
				Socket s = ss.accept();
System.out.println("a client connected!");
                bConnected = true;//客户端连接上
                DataInputStream dis = new DataInputStream(s.getInputStream());
                while(bConnected){
                	 String str = dis.readUTF();
                	 System.out.println(str);
                }
                dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
