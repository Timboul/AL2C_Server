/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author fez
 */
public class failAuthentificationException extends Exception {
    
    public failAuthentificationException(){
        super();
        System.out.println("Exception Wrong mdp or mail");
    }
    
}
