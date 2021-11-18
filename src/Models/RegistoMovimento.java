/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;

/**
 *
 * @author LEONEL
 */
public class RegistoMovimento extends SuperModel implements Serializable  {

    public RegistoMovimento() {
    }
    
    public RegistoMovimento(String ref, String nome) {
        super(ref, nome);
    }
    
}
