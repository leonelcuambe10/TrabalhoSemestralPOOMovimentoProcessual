/*
 * To change this license header;private String   choose License Headers in Project Properties.
 * To change this template file;private String   choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;

/**
 *
 * @author LEONEL
 */
public class Livro_Porta extends SuperModel implements Serializable {

    private int natureza_id;
    private String data_entrada;
    private String data_findo;
    private String senteca;//(=estado findo)
    private String nr_processo;
    private String autor;//(do processo)
    private String arguido;
     
    public Livro_Porta(String ref, String nome, int natureza_id, String data_entrada, String data_findo, String senteca, String nr_processo, String autor, String arguido) {
        super(ref, nome);
        this.natureza_id = natureza_id;
        this.data_entrada = data_entrada;
        this.data_findo = data_findo;
        this.senteca = senteca;
        this.nr_processo = nr_processo;
        this.autor = autor;
        this.arguido = arguido;
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
    
    

}
