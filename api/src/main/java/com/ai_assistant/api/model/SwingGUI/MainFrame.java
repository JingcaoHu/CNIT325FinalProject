package com.ai_assistant.api.model.SwingGUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JPanel menuPanel;
    private Map<String, Component> cards;

    private LoginPage loginPage;
    private RegisterPage registerPage;
    private MainPanel mainPanel;

    public MainFrame() {
        setTitle("多功能应用");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        cards = new HashMap<>();

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(150, getHeight()));

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

    private void updateMenu() {
        menuPanel.removeAll();
        addButton("页面 1", e -> mainPanel.showPage("page1"));
        addButton("页面 2", e -> mainPanel.showPage("page2"));
        addButton("历史记录", e -> mainPanel.showPage("history"));
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
        SwingUtilities.invokeLater(MainFrame::new);
    }
}