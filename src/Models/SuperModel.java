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
public class SuperModel implements Serializable {

    private int id;
    private int uid;
    private String ref;
    private String nome;
    private String create_dade; 

    public SuperModel() {
    }
        
    public SuperModel(String ref, String nome) {
        this.ref = ref;
        this.nome = nome;
    }
    
}
