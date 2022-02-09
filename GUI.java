import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

class GUI {

  String file = "fish.jpg";
  JFrame frame;

  GUI() {
    frame = new JFrame();
  }

  public void loadImage() {
    try {
      BufferedImage fish = resize(ImageIO.read(new File(file)), 100, 100);

      ImageIcon icon = new ImageIcon(fish);
      frame.setLayout(new FlowLayout());
      frame.setSize(500, 500);
      JLabel lbl = new JLabel();
      lbl.setIcon(icon);
      frame.add(lbl);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } catch (IOException e) {

    }
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