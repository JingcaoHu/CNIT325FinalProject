package com.ai_assistant.api.model;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Server{
    private int port;
    private String address;

    public Server(int port, String address){
        this.port = port;
        this.address = address;
    }

    public void setServerPort(int port){
        this.port = port;
    }
    public void setIPAddress(String address){
        this.address = address;
    }

    public int getServerPort(){
        return port;
    }
    public String getIPAddress(){
        return address;
    }

    public void runConnection(int port, String address){
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server: System up and running.");
            boolean over = false;
            while (!over){
                Socket incoming = ss.accept();
                System.out.println("Client Connected.");
                try{
                    InputStream inStream = incoming.getInputStream();
                    OutputStream outStreamToClient = incoming.getOutputStream();
                    Scanner input = new Scanner(inStream);
                    PrintWriter output = new PrintWriter(outStreamToClient, true);

                    // String inputStr = input.nextLine();
                    // Prompt prompt = parsePrompt(inputStr);

                    StringBuilder AIInput = new StringBuilder();
                    while (input.hasNextLine()){
                        String thisLine = input.nextLine();
                        if (thisLine.trim().equals("END_OF_MESSAGE")){
                            break;
                        }
                        AIInput.append(thisLine);
                    }
                    Prompt prompt = parsePrompt(AIInput.toString());
                    // System.out.println(AIInput.toString());

                    AIConnection toAI = new AIConnection();
                    //StringBuilder AIOutput = new StringBuilder();
                    
                    //把Prompt内容发送给AI并返回结果
                    String response = toAI.runConnection(prompt.selection, prompt.content, address);


                    //在以下部分需要加入JDBC，使用Prompt对象的UID和时间戳保存问题和答复到数据库
                    //to do code here


                    //以下是返回给客户端的信息
                    output.println(response);
                    System.out.println(prompt.getTimeStamp());
                    System.out.println("Transaction completed.");
                }
                finally{
                    incoming.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    //Helper function to parse serialized Prompt object
    //Serialized formate: UID<DELIMITER>selection<DELIMITER>content<DELIMITER>response<DELIMITER>timeStamp
    public Prompt parsePrompt(String str){
        String[] parts = str.split("<DELIMITER>");
        int UID = Integer.parseInt(parts[0]);
        int selection = Integer.parseInt(parts[1]);
        Prompt prompt = new Prompt(UID, selection, parts[2], parts[3]);
        prompt.setTimeStamp(getTimeStamp());
        return prompt;
    }

    //Helper function to get timestamp
    //Output format: 25-04-21 03:26:15
    public String getTimeStamp(){
        String timeStamp = "Time Stamp Not Available";
        String tempTimeStamp = "";
        String mysqlDateTimeString = "";
        try{
            Socket s = new Socket("time-A.timefreq.bldrdoc.gov", 13);
            try{
                InputStream inStream = s.getInputStream();
                Scanner in = new Scanner(inStream);
                
                while(in.hasNextLine()){
                    tempTimeStamp = in.nextLine();
                }
                String[] parts = tempTimeStamp.split(" ");
                if (parts.length >= 3){
                    timeStamp = parts[1] + " " + parts[2];
                }
                //Parse the output time string into MySQL DATETIME format of yyyy-MM-dd HH:mm:ss
                DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(timeStamp, inputFormat);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                mysqlDateTimeString = localDateTime.format(outputFormatter);
            }
            finally{
                s.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return mysqlDateTimeString;
    }

    public static void main(String[] args) {
        Server s1 = new Server(8189, "http://localhost:1234/v1/chat/completions");
        s1.runConnection(8189, "http://localhost:1234/v1/chat/completions");
    }
}