package org.example.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class BottomTextDisplay {

    public static void showTextAtBottom(String text) {
        // Screen setup
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = 180;
        int x = (screenSize.width - width) / 2;
        int y = screenSize.height - height - 60;

        // Frame
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setBackground(new Color(0, 0, 0, 0));

        // Main panel
        JPanel panel = new JPanel(new BorderLayout(15, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // single translucent background (no double overlay)
                g2.setColor(new Color(25, 25, 25, 220));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        panel.setOpaque(false);

        // Text area
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textArea.setBorder(null);

        // Scroll pane (clean look)
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(255, 255, 255, 80);
                this.trackColor = new Color(0, 0, 0, 0);
            }
        });

        // Top bar with buttons
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topBar.setOpaque(false);

        JButton copyBtn = createIconButton("📋", new Color(0, 120, 215));
        copyBtn.addActionListener(e -> {
            StringSelection selection = new StringSelection(textArea.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        });

        JButton closeBtn = createIconButton("✕", new Color(220, 20, 60));
        closeBtn.addActionListener(e -> frame.dispose());

        topBar.add(copyBtn);
        topBar.add(closeBtn);

        // Layout assembly
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel);

        frame.setSize(width, height);
        frame.setLocation(x, y);
        frame.setVisible(true);
    }

    private static JButton createIconButton(String symbol, Color baseColor) {
        JButton btn = new JButton(symbol);
        btn.setFocusable(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(new Color(255, 255, 255, 220));
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Hover effect
        btn.addChangeListener(e -> {
            if (btn.getModel().isRollover()) {
                btn.setForeground(baseColor.brighter());
            } else {
                btn.setForeground(new Color(255, 255, 255, 220));
            }
        });

        return btn;
    }
}
