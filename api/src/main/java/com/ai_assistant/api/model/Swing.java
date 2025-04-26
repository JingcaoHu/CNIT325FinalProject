package com.ai_assistant.api.model;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Swing extends JFrame implements ActionListener{
    JPanel pnlContain;
    JTextField txtInput, txtUID;
    JTextArea txtDisplay;
    JButton btnSend;
    String[] selectionList = {"Suggestion", "Debug", "Hint", "Generic"};
    JComboBox comboSelection;

    int port;
    String serverAddr;

    public Swing(){
        Container cp = getContentPane();
        pnlContain = new JPanel();

        txtUID = new JTextField("UID");
        pnlContain.add(txtUID);

        txtInput = new JTextField(50);
        pnlContain.add(txtInput);

        txtDisplay = new JTextArea(50,50);
        // txtDisplay.setEditable(false);
        pnlContain.add(txtDisplay);

        comboSelection = new JComboBox<>(selectionList);
        comboSelection.addActionListener(this);
        pnlContain.add(comboSelection);

        btnSend = new JButton("Send Request");
        btnSend.addActionListener(this);
        pnlContain.add(btnSend);

        cp.add(pnlContain);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSend){
            int selection = comboSelection.getSelectedIndex();
            String UIDString = txtUID.getText();
            int UID = Integer.parseInt(UIDString);
            String filePath = txtInput.getText();
            FileExtractor file = new FileExtractor(filePath);
            String content = file.getContent();
            Prompt prompt = new Prompt(UID, selection, content, null);
            String result = runConnection(8189, "127.0.0.1", prompt);
            txtDisplay.setText(result);
        }
    }

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
    
    public static void main(String[] args) {
        Swing gui = new Swing();
        gui.setSize(800,800);
        gui.setVisible(true);
    }

}
