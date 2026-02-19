package pctimer.com;
 

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwingLineChartExample extends JPanel {
    private final List<Integer> dataPoints;

    public SwingLineChartExample(List<Integer> dataPoints) {
        this.dataPoints = dataPoints;
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int padding = 40;
        int labelPadding = 25;
        int plotWidth = getWidth() - 2 * padding - labelPadding;
        int plotHeight = getHeight() - 2 * padding - labelPadding;

        // Draw axes (basic representation)
        g2d.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding + plotWidth, getHeight() - padding - labelPadding);
        g2d.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        
        // Find max value to scale the data
        int maxData = dataPoints.stream().mapToInt(v -> v).max().orElse(100);

        // Plot the line
        g2d.setColor(Color.BLUE);
        for (int i = 0; i < dataPoints.size() - 1; i++) {
            int x1 = padding + labelPadding + i * plotWidth / (dataPoints.size() - 1);
            int y1 = getHeight() - padding - labelPadding - dataPoints.get(i) * plotHeight / maxData;
            int x2 = padding + labelPadding + (i + 1) * plotWidth / (dataPoints.size() - 1);
            int y2 = getHeight() - padding - labelPadding - dataPoints.get(i + 1) * plotHeight / maxData;
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {
        List<Integer> data = new ArrayList<>();
        data.add(10);
        data.add(25);
        data.add(15);
        data.add(40);
        data.add(30);
        data.add(50);

        JFrame frame = new JFrame("Single Line Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SwingLineChartExample(data));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
