package com.ai_assistant.api.testcase;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class GUITest extends JFrame {

    public GUITest() {
        setTitle("Tabbed Pane Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Create the pages
        JPanel page1 = createPage("Content for Tab 1", Color.LIGHT_GRAY);
        JPanel page2 = createPage("Content for Tab 2", Color.CYAN);
        JPanel page3 = createPage("Content for Tab 3", Color.YELLOW);

        // Add pages to the tabbed pane with titles
        tabbedPane.addTab("Page 1", page1);
        tabbedPane.addTab("Second Page", page2);
        tabbedPane.addTab("Another Function", page3);

        // Add the tabbed pane to the frame
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createPage(String labelText, Color bgColor) {
        JPanel page = new JPanel();
        page.setBackground(bgColor);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        page.add(label);
        return page;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUITest::new);
    }
}