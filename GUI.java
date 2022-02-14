import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class GUI extends javax.swing.JFrame {

  JFrame frame;

  GUI() {
    frame = new JFrame();
    frame.setSize(500, 500);
  }

  public void loadImage(String imageFileName, int x, int y) {
    try {
      
      BufferedImage image = resize(ImageIO.read(new File("Sprites/" + imageFileName)), 100, 100);

      frame.setLayout(null);
      ImageIcon icon = new ImageIcon(image);
      JLabel lbl = new JLabel();
      lbl.setIcon(icon);
      lbl.setBounds(x, y, 500, 500);
      frame.add(lbl);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } catch (IOException e) {

    }
  }

  public void setBackground() {
    //Image img = Toolkit.getDefaultToolkit().getImage("Sprites/fish.jpg");

    JTextArea text = new JTextArea() {
      final ImageIcon icon = new ImageIcon("Sprites/fish.jpg");
      Image img = icon.getImage();
      
      {setOpaque(false);}
      public void paintComponent(Graphics graphics) 
      {
        graphics.drawImage(img, 0, 0, this);
        super.paintComponent(graphics);
    };

    JScrollPane pane = new JScrollPane(text);
    Container content = frame.getContentPane();
    content.add(pane, BorderLayout.CENTER);
  }

  private BufferedImage resize(BufferedImage image, int width, int height) {
    Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    // g2d IS the BufferedImage
    Graphics2D g2d = dimg.createGraphics();
    // draws scaled image
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();

    return dimg;
  }
}