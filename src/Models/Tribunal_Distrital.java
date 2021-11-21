/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author LEONEL
 */
@Entity
public class Tribunal_Distrital extends SuperModel implements Serializable {//ref,nome,descricao

    @Id
    @GeneratedValue
    public int id;
    private int uid;
    private String ref;
    private String nome;
    private String create_date;
    boolean activo;

    private String seccao;

    public Tribunal_Distrital() {
    }

    public Tribunal_Distrital(String ref, String nome, String seccao) {
        this.ref = ref;
        this.nome = nome;
        this.seccao = seccao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getSeccao() {
        return seccao;
    }

    public void setSeccao(String seccao) {
        this.seccao = seccao;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    } 
    
}
