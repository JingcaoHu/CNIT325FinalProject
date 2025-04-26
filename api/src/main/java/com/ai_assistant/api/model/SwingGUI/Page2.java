package com.ai_assistant.api.model.SwingGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import com.ai_assistant.api.model.Client;
import com.ai_assistant.api.model.Prompt;

public class Page2 extends JPanel implements ClientHandler {

    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JComboBox<String> functionComboBox;
    private JButton sendButton;
    private Client client;

    public Page2() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        inputTextArea = new JTextArea(10, 25);
        inputTextArea.setBorder(BorderFactory.createTitledBorder("用户输入"));
        splitPane.setLeftComponent(new JScrollPane(inputTextArea));

        outputTextArea = new JTextArea(10, 25);
        outputTextArea.setEditable(false);
        outputTextArea.setBorder(BorderFactory.createTitledBorder("程序输出"));
        splitPane.setRightComponent(new JScrollPane(outputTextArea));

        add(splitPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] functions = {"Hint", "Suggestion", "Debug", "Generic"};
        functionComboBox = new JComboBox<>(functions);
        sendButton = new JButton("发送请求");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedFunction = functionComboBox.getSelectedIndex();
                Prompt prompt = new Prompt(client.getUID(), selectedFunction, inputTextArea.getText(), null);
                // 在这里处理发送请求的逻辑
                String output = client.runConnection(8189, "127.0.0.1", prompt);
                outputTextArea.setText(output);
                System.out.println("Request completed.");
            }
        });
        controlPanel.add(functionComboBox);
        controlPanel.add(sendButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }
}