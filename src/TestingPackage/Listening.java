/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Popup;
import javax.swing.PopupFactory;

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

        button2.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                           Popup p = PopupFactory.getSharedInstance().getPopup(button2, new JLabel("help"), 100, 100);

               p.show();
            }
        });
       panel.add(button1);
       panel.add(button2);
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
