package com.ai_assistant.api.model.SwingGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.ai_assistant.api.model.Client;
import com.ai_assistant.api.model.Prompt;
import com.ai_assistant.api.model.RecordRetriever;

public class HistoryPage extends JPanel implements ClientHandler {

    private JComboBox<String> functionFilterComboBox;
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private MainPanel mainPanel;
    private Client client;
    Locale currentLocale;
    ResourceBundle bundle;
    private RecordRetriever recordRetriever;

    private final String[] columnNames = {"用户输入", "程序输出", "选择", "时间"};

    public HistoryPage(MainPanel panel) {
        currentLocale = new Locale("en", "US");
        bundle = ResourceBundle.getBundle("LocaleBundle", currentLocale);

        this.mainPanel = panel;
        this.recordRetriever = new RecordRetriever();
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filterLabel = new JLabel("按功能筛选:");
        String[] functions = {"All", "Hint", "Suggestion", "Debug", "Generic"};
        functionFilterComboBox = new JComboBox<>(functions);
        filterPanel.add(filterLabel);
        filterPanel.add(functionFilterComboBox);
        add(filterPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(null, columnNames);
        historyTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        // 注意：初始化时不加载数据，等待 client 被设置

        // 监听功能筛选下拉框的事件
        functionFilterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 只有当 client 不为 null 时才加载数据
                if (client != null) {
                    int selectedFunction = functionFilterComboBox.getSelectedIndex();
                    loadHistoryData(selectedFunction);
                }
            }
        });

        // 双击表格行事件
        historyTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { 
                    int row = historyTable.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        String input = (String) tableModel.getValueAt(row, 0);
                        String output = (String) tableModel.getValueAt(row, 1);
                        showHistoryDetail(input, output);
                    }
                }
            }
        });

    }

    private void loadHistoryData(int function) {
        if (client == null) {
            System.err.println("Client尚未设置, 无法加载历史记录。");
            tableModel.setRowCount(0); // 清空表格
            return;
        }

        try {
            int functionIndex = getFunctionIndex(function);
            ResultSet rs = recordRetriever.getTable(client.getUID(), functionIndex);
            // 清空现有数据
            tableModel.setRowCount(0);
            // 填充表格数据
            while (rs.next()) {
                String selection = rs.getString("SELECTION");
                String content = rs.getString("CONTENT");
                String response = rs.getString("RESPONSE");
                Timestamp timeStamp = rs.getTimestamp("TIME_STAMP");
                Object[] rowData = {content, response, selection, timeStamp};
                tableModel.addRow(rowData);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // 在界面上显示加载失败的消息
            JOptionPane.showMessageDialog(this, "加载历史记录失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getFunctionIndex(int function) {
        switch (function) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 0:
                return 5;
            default: // "All"
                return 5; // 对应 RecordRetriever 中表示所有功能的数值
        }
    }

    private void showHistoryDetail(String input, String output) {
        JDialog detailDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
                                         bundle.getString("historyDetailTitle"), 
                                         true);
        detailDialog.setLayout(new BorderLayout(5, 5));
        detailDialog.setSize(800, 600);
        detailDialog.setLocationRelativeTo(detailDialog.getParent());
    
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
    
        JTextArea inputTextArea = new JTextArea(input);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        JScrollPane inputScroll = new JScrollPane(inputTextArea);
        inputScroll.setBorder(BorderFactory.createTitledBorder(bundle.getString("historyInputTitle")));
        
        JTextArea outputTextArea = new JTextArea(output);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        JScrollPane outputScroll = new JScrollPane(outputTextArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder(bundle.getString("historyOutputTitle")));
    
        splitPane.setLeftComponent(inputScroll);
        splitPane.setRightComponent(outputScroll);
    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton resendButton = new JButton(bundle.getString("historyResendButton"));
        JButton closeButton = new JButton(bundle.getString("historyCloseButton"));
    
        resendButton.addActionListener(e -> {
            int selectedFunction = functionFilterComboBox.getSelectedIndex();
            Prompt prompt = new Prompt(client.getUID(), selectedFunction, inputTextArea.getText(), null);
            String newOutput = client.runConnection(8189, "127.0.0.1", prompt);
            outputTextArea.setText(newOutput.replace("\\n", "\n"));
        });
    
        closeButton.addActionListener(e -> detailDialog.dispose());
    
        buttonPanel.add(resendButton);
        buttonPanel.add(closeButton);
    
        detailDialog.add(splitPane, BorderLayout.CENTER);
        detailDialog.add(buttonPanel, BorderLayout.SOUTH);
    
        detailDialog.setVisible(true);
    }


    @Override
    public void setClient(Client client) {
        this.client = client;
        // 当 Client 设置后，加载历史记录
        loadHistoryData(functionFilterComboBox.getSelectedIndex());
    }

    @Override
    public void setLocale(String language, String country) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setLocale'");
    }
}