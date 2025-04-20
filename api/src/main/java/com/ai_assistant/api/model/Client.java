package com.ai_assistant.api.model;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client 
{
    int clientID;
    String password;

    public Client(int clientID, String password){
        this.clientID = clientID;
        this.password = password;
    }

    //@Override
    public String runConnection(int port, String address, Prompt passedInfo){
        StringBuilder sb = new StringBuilder();
        try
        {
            Socket s = new Socket(address, port);
            try
            {
                InputStream inStream = s.getInputStream();
                Scanner in = new Scanner(inStream);
                OutputStream outStream = s.getOutputStream();
                PrintWriter out = new PrintWriter(outStream,true);
                System.out.println("Client Connected to Server. Passed question: " + passedInfo.toString());
                out.println(passedInfo.toString());
                out.println("END_OF_MESSAGE");
                sb = new StringBuilder();
                while (in.hasNextLine())
                {       
                    sb.append(in.nextLine());
                }
            }
            finally
            {
                s.close();
                //in.close();
            }
        }
        catch(IOException ioexc)
        {
            ioexc.printStackTrace();
        }
        return sb.toString();
    }
    public static void main(String [] args)
    {
        Client c1 = new Client(0,null);
        String question = "What is the capital of France";
        Prompt prompt = new Prompt(0, 3, question, null); //Selection 0:Code suggestion || 1:Code solution
        
        String result = c1.runConnection(8189, "127.0.0.1", prompt);
        System.out.println(result);
    } //end public
} //end class

