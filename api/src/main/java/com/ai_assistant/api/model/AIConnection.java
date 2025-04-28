package com.ai_assistant.api.model;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AIConnection {
    //
    public String getSelection(int selectionInt){
        switch (selectionInt) {
        //Indexes of functions: 0. Hint 1. Suggestion 2. Debug 3. Generic
            case 0:
                return "You are a helpful and experienced assistant in programming. " +
                        "Here is a snippet of code from a programmer who needs your help, " +
                        "please find out all existing code issues and room for improvement " +
                        "and give suggestions of how to resolve the issues and improve the code. " +
                        "The suggestion could include what could go wrong in the current code, " +
                        "what logic can be used instead of the existing one in the code, " +
                        "and what methods/functions/data structures can be used for better efficiency." +
                        "Note that you are required to only give suggestions to help the user improve " +
                        "DO NOT provide any direct answer!";
            case 1:
                return "You are a helpful and experienced assistant in programming." +
                        "Here is a snippet of code from a programmer who needs your help." +
                        "Please give suggestions to the code provided, find issues,"+
                        " and give possible solutions.";
            
            case 2:
                return "You are a helpful and experienced assistant in programming. " +
                        "Here is a snippet of code from a human programmer who needs your help. " +
                        "Please create a code solution based on the snippet of code. "+
                        " You are required to only return revised code." +
                        "Please DO NOT return anything other than revised code " +
                        "so that the returned code can be executed directly. " +
                        "To explain the changes, you can use comment syntax between the code.";
            case 3:
                return "You are a helpful assistant on daily routine topics. "+
                        "Please answer questions from user in a short paragraph.";
            default:
                throw new AssertionError();
        }
    }

    public String runConnection(int selection, String AIInput, String address){
        String response = "Error occured, please check code.";
        String result = "Error: Cannot generate response";

        try {
            // Set up the endpoint URL
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // JSON payload
            
            CustomizedJson cJson = new CustomizedJson("deepseek-r1-distill-qwen-7b", getSelection(selection), AIInput);
            String jsonInputString = cJson.CreateCustomJson();

            // Send the JSON input
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            int status = conn.getResponseCode();
            System.out.println("HTTP Status Code: " + status);

            try (var reader = new java.util.Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                response = reader.useDelimiter("\\A").next();
                System.out.println("Response: " + response);
            }
            //处理AI返回的信息，裁剪到只包含答案
            String[] parts = response.split("</think>");
            String tempResult = parts[1];
            String[] parts1 = tempResult.split("}");
            result = parts1[0].trim();
            result = result.substring(0, result.length()-1);

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}