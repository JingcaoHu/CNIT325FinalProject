package com.ai_assistant.api.model.SwingGUI;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Map<String, Component> cards;
    private MainFrame mainFrame;

    private Page1 page1;
    private Page2 page2;
    private HistoryPage historyPage;

    public MainPanel(MainFrame frame) {
        this.mainFrame = frame;
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        cards = new HashMap<>();

        page1 = new Page1();
        page2 = new Page2();
        historyPage = new HistoryPage(this);

        cards.put("page1", page1);
        cards.put("page2", page2);
        cards.put("history", historyPage);

        contentPanel.add(page1, "page1");
        contentPanel.add(page2, "page2");
        contentPanel.add(historyPage, "history");

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        showPage("page1"); // 默认显示第一个页面
    }

    public void showPage(String pageName) {
        cardLayout.show(contentPanel, pageName);
    }
}