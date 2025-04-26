package com.ai_assistant.api.model.SwingGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton backToLoginButton;
    private MainFrame mainFrame;

    public RegisterPage(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("账号:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("密码:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("重复密码:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(confirmPasswordField, gbc);

        registerButton = new JButton("注册");
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
                    System.out.println("尝试注册: " + usernameField.getText() + ", " + new String(passwordField.getPassword()));
                    // 假设注册成功后返回登录页面
                    mainFrame.showCard("login");
                } else {
                    JOptionPane.showMessageDialog(RegisterPage.this, "两次输入的密码不一致！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(registerButton, gbc);

        backToLoginButton = new JButton("返回登录");
        gbc.gridy = 4;
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showCard("login");
            }
        });
        add(backToLoginButton, gbc);
    }
}