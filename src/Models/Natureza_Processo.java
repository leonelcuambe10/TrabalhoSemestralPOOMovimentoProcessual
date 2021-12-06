/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Controllers.ControlaSuperModel;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author LEONEL
 */
@Entity
public class Natureza_Processo extends SuperModel implements Serializable {//ref,nome

    @Id
    @GeneratedValue
    public int id;
    private int uid;
    private String ref;
    private String nome;
    private String create_date;
    boolean activo;

    public Natureza_Processo() {
    }

    public Natureza_Processo(String ref, String nome) {
        this.ref = ref;
        this.nome = nome;

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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String getSrc() {
        return id + "|" + new ControlaSuperModel(this).getExpressinById(new Usuario(), uid) + "|" + ref + "|" + nome + "|" + create_date + "|" + activo;
    }

}
