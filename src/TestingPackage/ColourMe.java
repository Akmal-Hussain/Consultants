/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingPackage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dakhussain
 */
public class ColourMe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Colour c = new Colour();
        LocalDate date = LocalDate.now();
        date.getDayOfWeek().getValue();
        List<Integer> nums = new ArrayList<>();
        nums = Colour.getList(); // alters the final static variable
                //new ArrayList<>(Colour.getList());
        System.out.println(nums);
        nums.remove(1);
        System.out.println(Colour.getList());
        double x = 2;
        Number num = 2;
        System.out.println(num.doubleValue()-x);
        
    }
    
}
