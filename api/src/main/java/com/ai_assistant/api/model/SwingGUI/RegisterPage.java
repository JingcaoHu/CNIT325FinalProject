package com.ai_assistant.api.model.SwingGUI;
import javax.swing.*;

import com.ai_assistant.api.model.InsertUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterPage extends JPanel {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton backToLoginButton;
    private JLabel usernameLabel, passwordLabel, confirmPasswordLabel;
    private MainFrame mainFrame;  
    private String language, country;
    private Locale locale;
    ResourceBundle bundle;

    public RegisterPage(MainFrame frame) {
        language = "en";
        country = "US";
        locale = new Locale(language, country); //Set default locale to US English
        bundle = ResourceBundle.getBundle("LocaleBundle", locale);
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameLabel = new JLabel(bundle.getString("lblUID"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(emailField, gbc);

        passwordLabel = new JLabel(bundle.getString("lblPwd"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        confirmPasswordLabel = new JLabel(bundle.getString("lblRepeatPwd"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(confirmPasswordField, gbc);

        registerButton = new JButton(bundle.getString("btnReg"));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在这里添加注册逻辑，并进行密码校验
                if (new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
                    System.out.println("Attempting Register: " + emailField.getText() + ", " + new String(passwordField.getPassword()));
                    // 假设注册成功后返回登录页面
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword()).trim();
                    InsertUser newUser = new InsertUser(email, password);
                    newUser.connectDatabase(null);
                    mainFrame.showCard("login");
                } else {
                    JOptionPane.showMessageDialog(RegisterPage.this, "Passwords Are Not Same", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(registerButton, gbc);

        backToLoginButton = new JButton(bundle.getString("btnBackToLogin"));
        gbc.gridy = 4;
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showCard("login");
            }
        });
        add(backToLoginButton, gbc);
    }

    public void setLocale(String language, String country){
        locale = new Locale(language, country);
        bundle = ResourceBundle.getBundle("LocaleBundle", locale);
        usernameLabel.setText(bundle.getString("lblUID"));
        passwordLabel.setText(bundle.getString("lblPwd"));
        confirmPasswordLabel.setText(bundle.getString("lblRepeatPwd"));
        registerButton.setText(bundle.getString("btnReg"));
        backToLoginButton.setText(bundle.getString("btnBackToLogin"));
    }
}