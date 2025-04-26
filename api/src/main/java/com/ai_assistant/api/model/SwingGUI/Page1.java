package com.ai_assistant.api.model.SwingGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Page1 extends JPanel {

    private JTextField filePathField;
    private JTextArea responseTextArea;
    private JComboBox<String> functionComboBox;
    private JButton sendButton;

    public Page1() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filePathLabel = new JLabel("文件路径:");
        filePathField = new JTextField(30);
        inputPanel.add(filePathLabel);
        inputPanel.add(filePathField);
        add(inputPanel, BorderLayout.NORTH);

        responseTextArea = new JTextArea(10, 50);
        responseTextArea.setEditable(false);
        add(new JScrollPane(responseTextArea), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] functions = {"Hint", "Suggestion", "Debug", "Generic"};
        functionComboBox = new JComboBox<>(functions);
        sendButton = new JButton("发送请求");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = filePathField.getText();
                String selectedFunction = (String) functionComboBox.getSelectedItem();
                // 在这里处理发送请求的逻辑
                String response = "您选择了 " + selectedFunction + " 功能，并发送了文件路径: " + filePath;
                responseTextArea.setText(response);
                System.out.println("请求发送: " + response);
            }
        });
        controlPanel.add(functionComboBox);
        controlPanel.add(sendButton);
        add(controlPanel, BorderLayout.SOUTH);
    }
}