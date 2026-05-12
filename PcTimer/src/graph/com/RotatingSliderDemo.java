package graph.com;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class RotatingSliderDemo extends JPanel {
    private BufferedImage image;
    private JSlider slider;
    private int degrees = 0;
    private ImagePanel imagePanel;

    public RotatingSliderDemo(BufferedImage img) {
        this.image = img;
        imagePanel = new ImagePanel();
        slider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);

        slider.setMajorTickSpacing(90);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new SliderListener());

        setLayout(new BorderLayout());
        add(imagePanel, BorderLayout.CENTER);
        add(slider, BorderLayout.PAGE_END);
    }

    private class SliderListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            degrees = slider.getValue();
            imagePanel.repaint(); // Repaint the panel to reflect the new rotation
        }
    }

    private class ImagePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create(); // Create a copy of the Graphics object

            // Apply anti-aliasing for smoother rotation
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Calculate center point for rotation
            double anchorX = getWidth() / 2.0;
            double anchorY = getHeight() / 2.0;

            // Create and apply the rotation transform
            double theta = Math.toRadians(degrees);
            AffineTransform af = AffineTransform.getRotateInstance(theta, anchorX, anchorY);
            g2.setTransform(af);

            // Draw the image centered within the panel
            int x = (int) (anchorX - image.getWidth() / 2.0);
            int y = (int) (anchorY - image.getHeight() / 2.0);
            g2.drawImage(image, x, y, null);

            g2.dispose(); // Dispose of the copy
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Load a sample image (replace with your image path)
                    BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = bi.createGraphics();
                    g2d.setColor(Color.BLACK);
                    //g2d.fillRect(0, 0, 100, 100);
                    g2d.fillOval(0, 0, 100, 100);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("Rotate", 30, 50);
                    g2d.dispose();

                    JFrame f = new JFrame("Rotating Slider in Java Graphics");
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.add(new RotatingSliderDemo(bi));
                    f.pack();
                    f.setLocationRelativeTo(null);
                    f.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
