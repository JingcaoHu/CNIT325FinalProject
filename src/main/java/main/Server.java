package main;

import java.awt.im.InputContext;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private int port;

    public Server(int port){
        this.port = port;
    }

    public void setServerPort(int port){
        this.port = port;
    }

    public int getServerPort(){
        return port;
    }

    public void runServer(int port){
        try {
            ServerSocket ss = new ServerSocket(port);
            boolean over = false;
            while (!over){
                try{
                    Socket incoming = ss.accept();
                    InputStream inStream = incoming.getInputStream();
                    OutputStream outStreamToClient = incoming.getOutputStream();
                    Scanner input = new Scanner(inStream);
                    PrintWriter output = new PrintWriter(outStreamToClient, true);

                    StringBuilder AIInput = new StringBuilder();
                    while (input.hasNextLine()){
                        String thisLine = input.nextLine();
                        AIInput.append(thisLine);
                    }
                    AIConnection toAI = new AIConnection(AIInput.toString());


                    //以下是返回给客户端的信息
                    output.println();
                }
                finally{
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) {
        Server s1 = new Server(8189);
        s1.runServer(8189);
    }

}

