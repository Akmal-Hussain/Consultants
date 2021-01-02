/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplayData;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.java.ReadData.ConsultantList;
import main.java.ReadData.DatesReader;
import main.java.ReadData.ShiftStructureReader;
import main.java.RunData.RunRota;

/**
 *
 * @author pi
 */
public class TitleScreen extends JFrame {

    public TitleScreen() {
        super("Rota Organiser");
        SetLookAndFeel.setLookAndFeel();
        JPanel pane = new JPanel();
        BorderLayout b = new BorderLayout();
        pane.setLayout(b);
        b.setVgap(40);
 
        Image resize = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(getClass().getResourceAsStream("/emoji.png"));

            resize = bufferedImage.getScaledInstance(100, 100, 0);
        } catch (Exception e) {
            e.getMessage();
        }

        ImageIcon image = new ImageIcon(resize);

       JLabel picLabel = new JLabel(image);
        JLabel textLabel = new JLabel("WTF Enterprises\u00a9");
        Font font = new Font(Font.SANS_SERIF, Font.ITALIC, 30);
        Font font2 = new Font(Font.SERIF, Font.BOLD, 60);

        textLabel.setFont(font);
        JPanel panel = new JPanel();
        setBackground(panel.getBackground());
        JPanel buttonPanel = new JPanel();
      
        JButton continueButton = new JButton();
        JLabel buttontext = new JLabel("Continue");
        buttontext.setFont(font);
        continueButton.add(buttontext);
        JLabel presentsText = new JLabel("  Presents");
        JLabel consultantRotaText = new JLabel("The Rota Rejigger");
        consultantRotaText.setFont(font2);
        JPanel centerpanel = new JPanel();
        centerpanel.add(consultantRotaText);
        panel.add(picLabel);
        panel.add(textLabel);
        panel.add(presentsText);
        setSize(800, 600);
        System.out.println(Thread.currentThread().getName());
        setVisible(true);
        continueButton.setSize(100, 100);
        pane.add(panel, BorderLayout.NORTH);
        pane.add(centerpanel, BorderLayout.CENTER);

        buttonPanel.setLayout(new BorderLayout());
        //buttonPanel.add(continueButton, BorderLayout.EAST);
        //pane.add(buttonPanel, BorderLayout.SOUTH);
        add(pane);
        // add(continueButton);
             setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            // setLookAndFeel();
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }
      this.dispose();
      ConfirmDates d = new ConfirmDates();
    }

    @Override
    public Insets getInsets() {
        return new Insets(100, 10, 10, 10);
    } 

  
    public static void main(String[] args) {
        new DatesReader("/main/resources/Data/Dates.xml");
        new ShiftStructureReader("/main/resources/Data/Shift_Structure.xml");
        new ConsultantList("/main/resources/Data/All_Consultants.xml");
        TitleScreen t = new TitleScreen();
    }
}
