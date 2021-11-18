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
public class SeccaoTribunal extends SuperModel implements Serializable {//ref,nome,descricao

    private String seccao;

    public SeccaoTribunal() {
    }
    
    public SeccaoTribunal(String ref, String nome, String seccao) {
        super(ref, nome);
        this.seccao = seccao;
    }

    public String getSeccao() {
        return seccao;
    }

    public void setSeccao(String seccao) {
        this.seccao = seccao;
    }
    
    

}
