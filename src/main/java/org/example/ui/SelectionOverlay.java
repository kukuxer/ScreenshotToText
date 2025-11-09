package org.example.ui;

import org.example.utils.ScreenshotUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectionOverlay {

    public SelectionOverlay() {
        // Create a full-screen transparent window
        JWindow window = new JWindow();
        window.setAlwaysOnTop(true);
        window.setBackground(new Color(0, 0, 0, 50)); // semi-transparent black
        window.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds());

        JPanel panel = new JPanel() {
            Point start, end;

            {
                setOpaque(false); // Allow transparency
                MouseAdapter mouse = new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        start = e.getPoint();
                        end = start;
                        repaint();
                    }

                    @Override
                    public void mouseDragged(MouseEvent e) {
                        end = e.getPoint();
                        repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        end = e.getPoint();
                        window.dispose();
                        captureSelection(start, end);
                    }
                };
                addMouseListener(mouse);
                addMouseMotionListener(mouse);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (start != null && end != null) {
                    int x = Math.min(start.x, end.x);
                    int y = Math.min(start.y, end.y);
                    int width = Math.abs(start.x - end.x);
                    int height = Math.abs(start.y - end.y);
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(Color.RED);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRect(x, y, width, height);
                    g2.dispose();
                }
            }
        };

        window.add(panel);
        window.setVisible(true);
    }

    private void captureSelection(Point start, Point end) {
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);

        if (width == 0 || height == 0) {
            System.out.println("⚠️ Empty selection, screenshot cancelled.");
            return;
        }

        Rectangle rect = new Rectangle(x, y, width, height);
        ScreenshotUtils.captureAndSave(rect);
    }
}
