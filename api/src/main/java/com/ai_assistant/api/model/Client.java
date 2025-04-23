package com.ai_assistant.api.model;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements ConnectionHandler
{
    int clientID;

    public Client(int clientID, String password){
        this.clientID = clientID;
    }

    //Getter
    public int getUID(){
        return clientID;
    }
    //Setter
    public void setUID(int UID){
        this.clientID = UID;
    }

    @Override
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

        //调试1:发送本地文件
        // FileExtractor file1 = new FileExtractor("/Users/huanfuli/CNIT325FinalProject/api/src/main/java/com/ai_assistant/api/model/Testcode.java");
        // String question = file1.getContent();

        //调试2:发送简单问题
        String question = "When was the USA founded?";

        Prompt prompt = new Prompt(0, 3, question, null); 
        //Selection 0:Code suggestion || 1:Code solution || 3:General question
        
        //Note: The following connection takes port and address of server
        String result = c1.runConnection(8189, "127.0.0.1", prompt);
        System.out.println(result);
    } //end public
} //end class

