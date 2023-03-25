package sample;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
    private int[][] imageData;
    private int depth;

    public MyPanel(int[][] imageData, int depth) {
        this.imageData = imageData;
        this.depth = depth;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int pixelWidth = width / (imageData[0].length);
        int pixelHeight = height / (imageData.length);
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                int intensity = (imageData[i][j] * 255) / depth;
                //int intensity = imageData[i][j];
                Color pixelColor = new Color(intensity, intensity, intensity);
                g.setColor(pixelColor);
                g.fillRect(j * pixelWidth, i * pixelHeight, pixelWidth, pixelHeight);
            }
        }
    }

    public void setImageData(int[][] imageData) {
        this.imageData = imageData;
        repaint();
    }

}

