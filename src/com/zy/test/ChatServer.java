package com.zy.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;

import javax.management.loading.PrivateClassLoader;

import java.util.*;
public class ChatServer {
	boolean started = false; 
	ServerSocket ss = null;
	
	List<Client> clients = new ArrayList<Client>();
	
	public static void main(String[] args){
		new ChatServer().start();
	}
	
	public void start(){
		try {
			ss = new ServerSocket(8888);
			started = true; //Server启动
		} catch (BindException e) {
			System.out.println("Port is using...");
			System.out.println("请关掉相关程序，并重新运行服务器");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			while(started) {
				Socket s = ss.accept();
				Client c = new Client(s); 
System.out.println("a client connected!");
                new Thread(c).start();
                clients.add(c);
                //dis.close();
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

	class Client implements Runnable{
		private Socket s;//每个Client的连接
		private DataInputStream dis = null;//从s中读数据
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		
		public Client(Socket s){
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;//客户端连接上
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				clients.remove(this);
				System.out.println(" 对方退出，我从List中去除了!!!");
				//e.printStackTrace();
			}
		}

		public void run() {
             try {
            	 while(bConnected){
             	 String str = dis.readUTF();
System.out.println(str);
            	 for(int i=0;i<clients.size();i++){
            		 Client c = clients.get(i);
            		 c.send(str);
            	 }
            	 /*
            	 for(Iterator<Client> it = clients.iterator();it.hasNext();){
            		 Client c = it.next();
            		 c.send(str);
            	 }
            	 */
            	 /*
            	 Iterator<Client> it = clients.iterator();
            	 while(it.hasNext()){
            		 Client c = it.next();
            		 c.send(str);
            	 }
            	 */
            	 }
            	 
             } catch (EOFException e){
     			System.out.println("Client closed!");
     		} catch (IOException e) {
     			e.printStackTrace();
     		} finally {
     			try {
     				if(dis!=null) {
     					dis.close();
     					//dis = null;
     				}
     				if(dos!=null){
     					dos.close();
     					//dos = null;
     				}		
     				if(s!=null) {
     					s.close();
     					//s = null; 
     				}		
     			} catch (IOException e1) {
     				e1.printStackTrace();
     			}
     			
     		}
			
		}
		
	}
}
