package com.ai_assistant.api.model.SwingGUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ai_assistant.api.model.Client;
import com.formdev.flatlaf.FlatDarculaLaf;

public class MainFrame extends JFrame implements ClientHandler {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JPanel menuPanel;
    private Map<String, Component> cards;

    private LoginPage loginPage;
    private RegisterPage registerPage;
    private MainPanel mainPanel;

    Locale currentLocale;
    ResourceBundle bundle;

    Client client;

    public MainFrame() {
        
        currentLocale = new Locale("zh", "CN");
        bundle = ResourceBundle.getBundle("LocaleBundle", currentLocale);
        setTitle(bundle.getString("title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        cards = new HashMap<>();

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(150, getHeight()));

        client = new Client(0, "");//Placeholder for client object
        loginPage = new LoginPage(this);
        registerPage = new RegisterPage(this);
        mainPanel = new MainPanel(this);

        cards.put("login", loginPage);
        cards.put("register", registerPage);
        cards.put("main", mainPanel);

        contentPanel.add(loginPage, "login");
        contentPanel.add(registerPage, "register");
        contentPanel.add(mainPanel, "main");

        add(contentPanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.WEST);

        cardLayout.show(contentPanel, "login");

        setVisible(true);
    }

    public void showCard(String cardName) {
        cardLayout.show(contentPanel, cardName);
        if (cardName.equals("main")) {
            updateMenu();
        } else {
            menuPanel.removeAll();
            menuPanel.revalidate();
            menuPanel.repaint();
        }
    }

    @Override
    public void setClient(Client client){
        this.client = client;
        mainPanel.setClient(client);
        showCard("main");
    }

    private void updateMenu() {
        menuPanel.removeAll();
        addButton(bundle.getString("page1"), e -> mainPanel.showPage("page1"));
        addButton(bundle.getString("page2"), e -> mainPanel.showPage("page2"));
        addButton(bundle.getString("page3"), e -> mainPanel.showPage("history"));
        //Create a glue between buttons and profile/settings
        menuPanel.add(Box.createVerticalGlue());
        addButton(bundle.getString("userPage"), e -> mainPanel.showPage("history"));
        addButton(bundle.getString("settingPage"), e -> mainPanel.showPage("history"));
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private void addButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(listener);
        menuPanel.add(button);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf()); // 设置 FlatLightLaf 主题
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(MainFrame::new);

    }
}