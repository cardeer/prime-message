/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex2.P1.P2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author siwakorn
 */
public class Prime {
    private int[] range;
    
    public Prime(String rangeText) throws Exception {
        Pattern pattern = Pattern.compile("[0-9]+, [0-9]+");
        Matcher matcher = pattern.matcher(rangeText);
        
        if (matcher.matches()) {
            this.range = new int[2];
            
            String[] splittedRange = rangeText.split(", ");
            range[0] = Integer.parseInt(splittedRange[0]);
            range[1] = Integer.parseInt(splittedRange[1]);
        }
        else {
            throw new Exception("Range pattern doesn't match.");
        }
    }
    
    public int countPrimeNumbers() {
        int count = 0;
        
        for (int i = range[0]; i <= range[1]; i++) {
            if (i == 1) continue;
            
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            
            if (isPrime) {
                count++;
            }
        }
        
        return count;
    }
    
    public int getMin() {
        return range[0];
    }
    
    public int getMax() {
        return range[1];
    }
}
