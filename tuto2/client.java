import java.net.*;  
import java.io.*;  
class client{  
	public static void main(String args[])throws Exception{  
		Socket s=new Socket("localhost",3333);  
		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		System.out.println("client started");
		String str="",str2="";  
		while(!str.equals("exit")){  
			str=br.readLine();  
			dout.writeUTF(str);  
			dout.flush();  
			str2=din.readUTF();  
			if(!str.equals("exit"))
				System.out.println("ans is : "+str2);
			else
				System.out.println(str2);
		}  
		
		dout.close();  
		s.close();  
	}}