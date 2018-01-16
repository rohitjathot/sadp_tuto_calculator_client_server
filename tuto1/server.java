import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;


class server{  
	public static void main(String args[])throws Exception{  
		ServerSocket ss=new ServerSocket(3333);  
		Socket s=ss.accept();  
		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		System.out.println("server started");
		String str="";  
		while(!str.equals("stop")){  
			str=din.readUTF();  
			System.out.println("calculating : "+str);
			try{
				dout.writeUTF(""+(Double)(new javax.script.ScriptEngineManager()
				        					  .getEngineByName("js")
				        					  .eval(str+"*1.0")));  
			}catch(Exception e){
				dout.writeUTF("Invalid Input");
			}
			dout.flush();  
		}  
		din.close();  
		s.close();  
		ss.close();  
	}
}  