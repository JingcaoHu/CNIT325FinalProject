package com.ai_assistant.api.model.SwingGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Page2 extends JPanel {

    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JComboBox<String> functionComboBox;
    private JButton sendButton;

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
                String input = inputTextArea.getText();
                String selectedFunction = (String) functionComboBox.getSelectedItem();
                // 在这里处理发送请求的逻辑
                String output = "您选择了 " + selectedFunction + " 功能，并输入了: \n" + input;
                outputTextArea.setText(output);
                System.out.println("请求发送: " + output);
            }
        });
        controlPanel.add(functionComboBox);
        controlPanel.add(sendButton);
        add(controlPanel, BorderLayout.SOUTH);
    }
}