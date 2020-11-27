/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author pi
 */
public class Listening extends JFrame implements ActionListener{

    String hi;
    JPanel panel = new JPanel();
    List<JButton> hello = new ArrayList<>();
    
    public Listening() {
        super("Messing Around");
        setSize(800,800);
        hi = "hello";
        JButton button1 = new JButton(hi);
        JButton button2 = new JButton("Bye");
        button1.addActionListener(this);
       panel.add(button1);
       add(panel);
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Listening();
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        hi = "Bye";
        JButton button2 = new JButton(hi);
        panel.add(button2);
        for (JButton b : hello){
            add(b);
        }
        this.revalidate();
        //panel.repaint();
    }
    
}
