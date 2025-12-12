package com.example.tasktrack;

import com.example.tasktrack.model.Task;
import com.example.tasktrack.service.TaskService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class TaskTrackUI extends JFrame {
    private final TaskService service;
    private final TaskTableModel tableModel;
    private final JTable table;

    public TaskTrackUI() {
        super("TaskTrack — Productivity");
        this.service = new TaskService("tasks.json");

        // Window settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 520);
        setLocationRelativeTo(null); // center

        // Top header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(32, 160, 78)); // green theme
        header.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel title = new JLabel("TaskTrack");
        title.setForeground(Color.white);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(title, BorderLayout.WEST);

        JLabel subtitle = new JLabel("Simple tasks — get things done");
        subtitle.setForeground(new Color(220, 255, 238));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        header.add(subtitle, BorderLayout.SOUTH);

        // Toolbar with buttons
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(245, 249, 247));
        toolBar.setBorder(new EmptyBorder(8, 12, 8, 12));

        JButton addBtn = new JButton("Add Task");
        JButton deleteBtn = new JButton("Delete");
        JButton completeBtn = new JButton("Mark Complete");
        JButton refreshBtn = new JButton("Refresh");

        addBtn.setFocusable(false);
        deleteBtn.setFocusable(false);
        completeBtn.setFocusable(false);
        refreshBtn.setFocusable(false);

        toolBar.add(addBtn);
        toolBar.addSeparator(new Dimension(8, 0));
        toolBar.add(deleteBtn);
        toolBar.addSeparator(new Dimension(8, 0));
        toolBar.add(completeBtn);
        toolBar.addSeparator(new Dimension(8, 0));
        toolBar.add(refreshBtn);

        // Table
        tableModel = new TaskTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // nicer column alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // status column

        // alternate row colors
        table.setDefaultRenderer(Object.class, new AlternateRowColorRenderer());

        // sortable rows
        TableRowSorter<TaskTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Layout
        JPanel content = new JPanel(new BorderLayout());
        content.add(toolBar, BorderLayout.NORTH);
        content.add(scroll, BorderLayout.CENTER);
        content.setBackground(new Color(250, 252, 250));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(content, BorderLayout.CENTER);

        // Load initial data
        refreshTable();

        // Button actions
        addBtn.addActionListener(e -> showAddDialog());
        refreshBtn.addActionListener(e -> refreshTable());
        deleteBtn.addActionListener(e -> deleteSelectedTask());
        completeBtn.addActionListener(e -> markSelectedComplete());
    }

    private void showAddDialog() {
        JTextField titleField = new JTextField();
        JTextArea descArea = new JTextArea(4, 20);
        JTextField dueField = new JTextField();

        JScrollPane descScroll = new JScrollPane(descArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panel = new JPanel(new BorderLayout(6,6));
        JPanel labels = new JPanel(new GridLayout(0,1,6,6));
        labels.add(new JLabel("Title:"));
        labels.add(new JLabel("Description:"));
        labels.add(new JLabel("Due (YYYY-MM-DD):"));

        JPanel controls = new JPanel(new GridLayout(0,1,6,6));
        controls.add(titleField);
        controls.add(descScroll);
        controls.add(dueField);

        panel.add(labels, BorderLayout.WEST);
        panel.add(controls, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(420, 180));

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Task",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String t = titleField.getText().trim();
            String d = descArea.getText().trim();
            String due = dueField.getText().trim();
            if (t.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title cannot be empty", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            service.add(t, d, due);
            refreshTable();
        }
    }

    private void deleteSelectedTask() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a task first", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int modelRow = table.convertRowIndexToModel(row);
        String id = tableModel.getTaskIdAt(modelRow);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected task?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = service.delete(id);
            if (ok) refreshTable();
            else JOptionPane.showMessageDialog(this, "Task not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markSelectedComplete() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a task first", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int modelRow = table.convertRowIndexToModel(row);
        String id = tableModel.getTaskIdAt(modelRow);
        boolean ok = service.complete(id);
        if (ok) refreshTable();
        else JOptionPane.showMessageDialog(this, "Task not found", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void refreshTable() {
        tableModel.load(service.list());
    }

    // ---- Table model ----
    private static class TaskTableModel extends AbstractTableModel {
        private final String[] columns = {"Title", "Due Date", "Status", "ID"};
        private java.util.List<Task> data = java.util.Collections.emptyList();

        public void load(List<Task> tasks) {
            this.data = tasks;
            fireTableDataChanged();
        }

        public String getTaskIdAt(int row) {
            return data.get(row).getId();
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Task t = data.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> t.getTitle();
                case 1 -> t.getDueDate();
                case 2 -> t.isCompleted() ? "Done" : "Pending";
                case 3 -> t.getId();
                default -> "";
            };
        }
    }

    // ---- Alternate row renderer for nicer look ----
    private static class AlternateRowColorRenderer extends DefaultTableCellRenderer {
        private final Color even = new Color(245, 248, 246);
        private final Color odd = Color.white;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isSelected) {
                c.setBackground(new Color(200, 230, 210));
            } else {
                c.setBackground(row % 2 == 0 ? even : odd);
            }
            setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
            return c;
        }
    }

    // Entry point helper (not required but convenient)
    public static void showUI() {
        SwingUtilities.invokeLater(() -> {
            TaskTrackUI ui = new TaskTrackUI();
            ui.setVisible(true);
        });
    }
}
