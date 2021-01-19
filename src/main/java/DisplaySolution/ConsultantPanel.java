/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplaySolution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import main.java.DisplayData.SetLookAndFeel;

/**
 *
 * @author pi
 */
public class ConsultantPanel extends JPanel {

    
    JButton COW;
    JButton NOW;
    JButton paed;
    JButton neo;

    public JButton getCOW() {
        return COW;
    }

    public JButton getNOW() {
        return NOW;
    }

    public JButton getPaed() {
        return paed;
    }

    public JButton getNeo() {
        return neo;
    }

    
    public ConsultantPanel(){
        super();
    
        
        SetLookAndFeel.setLookAndFeel();
        setLayout(new GridLayout(2,2));
        
        // First Grid
        //JPanel northWest = new JPanel();
       
        COW = new JButton(" COW ");
        COW.setPreferredSize(new Dimension(2,2));
        COW.setBackground(Color.YELLOW);
        COW.setVisible(false);
        //        northWest.add(COW);
       
      //  JPanel northEast = new JPanel();
        
        NOW = new JButton(" NOW ");
        NOW.setBackground(Color.GREEN);
        NOW.setVisible(false);
    //    northEast.add(NOW);
        
        // Final Grid
        
        //JPanel southWest = new JPanel();
        paed = new JButton("Paed O/C");
        paed.setBackground(Color.ORANGE);
        paed.setVisible(false);
          //      southWest.add(paed);
//JPanel southEast = new JPanel();
        neo = new JButton("Neo O/C");
        neo.setBackground(Color.cyan);
        neo.setVisible(false);
//      southEast.add(neo);
        // add all components to Parent JPanel
       // add(northWest);
       // add(northEast);
       // add(southWest);
       // add(southEast);
       add(COW);
       add(NOW);
       add(paed);  
       add(neo);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
       setVisible(true);
    }
    
}
