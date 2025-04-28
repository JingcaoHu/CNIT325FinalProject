package com.ai_assistant.api.model.SwingGUI;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ai_assistant.api.model.Client;

public class LoginPage extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JComboBox localeSelection;
    String[] locales = {"English (US)", "简体中文 (中国大陆)", "Français"};
    private MainFrame mainFrame;
    private JLabel usernameLabel, passwordLabel;
    private String language, country;
    private Locale locale;
    ResourceBundle bundle;

    public LoginPage(MainFrame frame) {
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

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        passwordLabel = new JLabel(bundle.getString("lblPwd"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        loginButton = new JButton(bundle.getString("btnLogin"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在这里添加登录逻辑
                System.out.println("Login attempted: " + usernameField.getText() + ", " + new String(passwordField.getPassword()));
                int UID = Integer.parseInt(usernameField.getText());

                //To-do code: Authentication in DB
                boolean authenticated = true;
                if (authenticated){
                    Client client = new Client(UID, new String(passwordField.getPassword()));
                // 假设登录成功后切换到主面板
                    mainFrame.setClient(client);
                    mainFrame.setLocale(language, country);
                }
            }
        });
        add(loginButton, gbc);

        registerButton = new JButton(bundle.getString("btnReg"));
        gbc.gridy = 3;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showCard("register");
            }
        });
        add(registerButton, gbc);

        localeSelection = new JComboBox<>(locales);
        gbc.gridy = 4;
        localeSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = localeSelection.getSelectedIndex();
                switch (index) {
                    case 0:
                        language = "en";
                        country = "US";
                        break;
                    case 1:
                        language = "zh";
                        country = "CN";
                        break;
                    case 2:
                        language = "fr";
                        country = "FR";
                        break;
                    default:
                        language = "en";
                        country = "US";
                        break;
                }
                locale = new Locale(language, country);
                updateUI(locale);
            }
        });
        add(localeSelection, gbc);
    }// End Constructor
    public void updateUI(Locale locale){
        this.locale = locale;
        bundle = ResourceBundle.getBundle("LocaleBundle", locale);
        usernameLabel.setText(bundle.getString("lblUID"));
        passwordLabel.setText(bundle.getString("lblPwd"));
        loginButton.setText(bundle.getString("btnLogin"));
        registerButton.setText(bundle.getString("btnReg"));
        revalidate();
        repaint();
    }
}