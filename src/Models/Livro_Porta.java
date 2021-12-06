/*
 * To change this license header;private String   choose License Headers in Project Properties.
 * To change this template file;private String   choose Tools | Templates
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
public class Livro_Porta extends SuperModel implements Serializable {

    @Id
    @GeneratedValue
    public int id;
    private int uid;
    public int state_id;
    private String ref;
    private String nome;
    private String create_date;
    boolean activo;

    private int tribunal_id;
    private int natureza_id;
    private String data_entrada;
    public String data_actualizacao="";
    private String data_findo;
    private String senteca;//(=estado findo)
    private String nr_processo;

    private String autor;//(do processo)
    private String arguido;
    private String estado;

    public int ponto;// sera = um caso o estado nao seja Transitado

    public Livro_Porta() {
    }

    public Livro_Porta(String ref, String nome, int tribunal_id, int natureza_id, String data_entrada, String data_findo, String senteca, String nr_processo, String autor, String arguido, String estado,String data_actualizacao) {
        this.ref = ref;
        this.nome = nome;
        this.tribunal_id = tribunal_id;
        this.natureza_id = natureza_id;
        this.data_entrada = data_entrada;
        this.data_findo = data_findo;
        this.senteca = senteca;
        this.nr_processo = nr_processo;
        this.autor = autor;
        this.arguido = arguido;
        this.estado = estado;
        this.data_actualizacao = data_actualizacao;
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

    public int getTribunal_id() {
        return tribunal_id;
    }

    public void setTribunal_id(int tribunal_id) {
        this.tribunal_id = tribunal_id;
    }

    public int getNatureza_id() {
        return natureza_id;
    }

    public void setNatureza_id(int natureza_id) {
        this.natureza_id = natureza_id;
    }

    public String getData_entrada() {
        return data_entrada;
    }

    public void setData_entrada(String data_entrada) {
        this.data_entrada = data_entrada;
    }

    public String getData_findo() {
        return data_findo;
    }

    public void setData_findo(String data_findo) {
        this.data_findo = data_findo;
    }

    public String getSenteca() {
        return senteca;
    }

    public void setSenteca(String senteca) {
        this.senteca = senteca;
    }

    public String getNr_processo() {
        return nr_processo;
    }

    public void setNr_processo(String nr_processo) {
        this.nr_processo = nr_processo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getArguido() {
        return arguido;
    }

    public void setArguido(String arguido) {
        this.arguido = arguido;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String getSrc() {
        ControlaSuperModel con = new ControlaSuperModel(this);
        return id + "|" + con.getExpressinById(new Usuario(), uid) + "|" + ref + "|" + nome + "|" + create_date + "|" + activo + "|" + con.getExpressinById(new Tribunal(), tribunal_id) + "|" + con.getExpressinById(new Natureza_Processo(), natureza_id) + "|" + data_entrada + "|" + data_findo + "|" + senteca + "|" + nr_processo + "|" + autor + "|" + arguido + "|" + estado;
    }

}
