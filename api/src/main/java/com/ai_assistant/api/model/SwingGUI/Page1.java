package com.ai_assistant.api.model.SwingGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ai_assistant.api.model.Client;
import com.ai_assistant.api.model.FileExtractor;
import com.ai_assistant.api.model.Prompt;

public class Page1 extends JPanel implements ActionListener{

    private JTextField filePathField;
    private JTextArea responseTextArea;
    private JComboBox<String> functionComboBox;
    private JButton sendButton;
    private Client client;
    Locale currentLocale;
    ResourceBundle bundle;

    public Page1() {
        currentLocale = new Locale("zh", "CN");
        bundle = ResourceBundle.getBundle("LocaleBundle", currentLocale);
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filePathLabel = new JLabel(bundle.getString("lblPath"));
        filePathField = new JTextField(30);
        inputPanel.add(filePathLabel);
        inputPanel.add(filePathField);
        add(inputPanel, BorderLayout.NORTH);

        responseTextArea = new JTextArea(10, 50);
        responseTextArea.setEditable(false);
        add(new JScrollPane(responseTextArea), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] functions = {bundle.getString("selection1"), bundle.getString("selection2"), bundle.getString("selection3"), bundle.getString("selection4")};
        functionComboBox = new JComboBox<>(functions);
        sendButton = new JButton(bundle.getString("btnSend"));
        sendButton.addActionListener(this);
        controlPanel.add(functionComboBox);
        controlPanel.add(sendButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = filePathField.getText();
                FileExtractor file = new FileExtractor(filePath);
                String question = file.getContent();
                int selectedFunction = functionComboBox.getSelectedIndex();
                Prompt prompt = new Prompt(client.getUID(), selectedFunction, question, null);
                //Hard coded UID, port, and address for test
                String response = client.runConnection(8189, "127.0.0.1", prompt);
                responseTextArea.setText(response);
                System.out.println("Request completed.");
            }
}