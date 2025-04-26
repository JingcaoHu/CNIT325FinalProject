package com.ai_assistant.api.model.SwingGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

public class HistoryPage extends JPanel {

    private JComboBox<String> functionFilterComboBox;
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private MainPanel mainPanel;

    // 模拟历史记录数据
    private final Object[][] historyData = {
            {new Date(), "Hint", "input 1", "output 1"},
            {new Date(), "Suggestion", "input 2", "output 2"},
            {new Date(), "Debug", "input 3", "output 3"},
            {new Date(), "Generic", "input 4", "output 4"}
    };
    private final String[] columnNames = {"日期", "功能", "用户输入", "程序输出"};

    public HistoryPage(MainPanel panel) {
        this.mainPanel = panel;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filterLabel = new JLabel("按功能筛选:");
        String[] functions = {"全部", "Hint", "Suggestion", "Debug", "Generic"};
        functionFilterComboBox = new JComboBox<>(functions);
        filterPanel.add(filterLabel);
        filterPanel.add(functionFilterComboBox);
        add(filterPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(historyData, columnNames);
        historyTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        // 双击表格行事件
        historyTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = historyTable.getSelectedRow();
                    if (row != -1) {
                        String input = (String) tableModel.getValueAt(row, 2);
                        String output = (String) tableModel.getValueAt(row, 3);
                        showHistoryDetail(input, output);
                    }
                }
            }
        });

        // TODO: 添加按日期检索的功能
    }

    private void showHistoryDetail(String input, String output) {
        JDialog detailDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "历史记录详情", true);
        detailDialog.setLayout(new BorderLayout(5, 5));
        detailDialog.setSize(600, 400);
        detailDialog.setLocationRelativeTo(detailDialog.getParent());

        JTextArea inputTextArea = new JTextArea(input);
        inputTextArea.setEditable(false);
        inputTextArea.setBorder(BorderFactory.createTitledBorder("用户输入"));
        detailDialog.add(new JScrollPane(inputTextArea), BorderLayout.WEST);

        JTextArea outputTextArea = new JTextArea(output);
        outputTextArea.setEditable(false);
        outputTextArea.setBorder(BorderFactory.createTitledBorder("程序输出"));
        detailDialog.add(new JScrollPane(outputTextArea), BorderLayout.EAST);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(inputTextArea), new JScrollPane(outputTextArea));
        splitPane.setResizeWeight(0.5); // 左右比例
        detailDialog.add(splitPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> detailDialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        detailDialog.add(buttonPanel, BorderLayout.SOUTH);

        detailDialog.setVisible(true);
    }

    // TODO: 实现按日期和功能筛选历史记录的功能
    public void filterHistory(String function, Date startDate, Date endDate) {
        // 实际应用中需要根据选择的条件更新 tableModel 中的数据
        // 这里只是一个示例，简单地打印筛选条件
        System.out.println("筛选条件：功能=" + function + ", 开始日期=" + startDate + ", 结束日期=" + endDate);
        // 刷新表格数据
        updateHistoryTable(historyData); // 实际应根据筛选结果更新数据
    }

    private void updateHistoryTable(Object[][] data) {
        tableModel.setDataVector(data, columnNames);
        tableModel.fireTableDataChanged();
    }
}