import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
 
public class server 
{
    public static void main(String[] args) throws IOException 
    {
        ServerSocket ss = new ServerSocket(3333);
        while (true) 
        {
            Socket s = null;
            try
            {
                s = ss.accept();
                System.out.println("A new client is connected : " + s);
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                System.out.println("Assigning new thread for this client");
                Thread t = new ClientHandler(s, dis, dos);
                t.start();
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}
 


class ClientHandler extends Thread 
{

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
 
    @Override
    public void run() 
    {
        String received;
        String toreturn;
        String history="";
        while (true) 
        {
            try {
                received = dis.readUTF();
                if(received.equals("exit"))
                { 
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    dos.writeUTF(history);
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }
               
                System.out.println("calculating : "+received);
				try{
                    String ans = ""+(Double)(new javax.script.ScriptEngineManager()
                                                  .getEngineByName("js")
                                                  .eval(received+"*1.0"));
					dos.writeUTF(ans);
                    history+=received+" : "+ans+"\n";  

				}catch(Exception e){
					dos.writeUTF("Invalid Input");
				}

            } catch (IOException e) {

            }
        }
        try
        {
            this.dis.close();
            this.dos.close();
             
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
