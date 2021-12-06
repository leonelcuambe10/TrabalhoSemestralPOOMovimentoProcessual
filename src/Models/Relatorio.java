/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Jars.DH;

/**
 * @author LEONEL
 */
public class Relatorio {//ap = arguidos presos, anp = nao presos, mma = movimento mensal de arguidos

    public String natureza;
    public String tribunal;
    public String print_date;
    public String de;
    public String ate;

    public String pend_ap;//pendentes
    public String pend_anp;

    public String entr_ap;//entrados
    public String entr_anp;

    public String find_ps_ap;//findos por sentenca
    public String find_ps_anp;

    public String find_pom_ap;//findos por outros motivos
    public String find_pom_anp;

    public String mma_apj = "";// arguidos presos julgados 
    public String mma_apaj = "";// arguidos presos aguardando julgados 
    public String mma_acp = "";// arguidos em cumprimento de pena
    //processos transitados
    public String pt_ap;//entrados
    public String pt_anp;

    public Relatorio() {
    }

    public Relatorio(String tribunal, String de, String ate, String natureza, int pend_ap, int pend_anp, int entr_ap, int entr_anp, int find_ps_ap, int find_ps_anp, int find_pom_ap, int find_pom_anp, int pt_ap, int pt_anp) {
        this.tribunal = tribunal;
        this.print_date = new DH().getData();;
        this.de = de;
        this.ate = ate;
        this.natureza = natureza;
        this.pend_ap = "" + pend_ap;
        this.pend_anp = "" + pend_anp;
        this.entr_ap = "" + entr_ap;
        this.entr_anp = "" + entr_anp;
        this.find_ps_ap = "" + find_ps_ap;
        this.find_ps_anp = "" + find_ps_anp;
        this.find_pom_ap = "" + find_pom_ap;
        this.find_pom_anp = "" + find_pom_anp;
        this.pt_ap = "" + pt_ap;
        this.pt_anp = "" + pt_anp;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getTribunal() {
        return tribunal;
    }

    public void setTribunal(String tribunal) {
        this.tribunal = tribunal;
    }

    public String getPrint_date() {
        return print_date;
    }

    public void setPrint_date(String print_date) {
        this.print_date = print_date;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getAte() {
        return ate;
    }

    public void setAte(String ate) {
        this.ate = ate;
    }

    public String getPend_ap() {
        return pend_ap;
    }

    public void setPend_ap(String pend_ap) {
        this.pend_ap = pend_ap;
    }

    public String getPend_anp() {
        return pend_anp;
    }

    public void setPend_anp(String pend_anp) {
        this.pend_anp = pend_anp;
    }

    public String getEntr_ap() {
        return entr_ap;
    }

    public void setEntr_ap(String entr_ap) {
        this.entr_ap = entr_ap;
    }

    public String getEntr_anp() {
        return entr_anp;
    }

    public void setEntr_anp(String entr_anp) {
        this.entr_anp = entr_anp;
    }

    public String getFind_ps_ap() {
        return find_ps_ap;
    }

    public void setFind_ps_ap(String find_ps_ap) {
        this.find_ps_ap = find_ps_ap;
    }

    public String getFind_ps_anp() {
        return find_ps_anp;
    }

    public void setFind_ps_anp(String find_ps_anp) {
        this.find_ps_anp = find_ps_anp;
    }

    public String getFind_pom_ap() {
        return find_pom_ap;
    }

    public void setFind_pom_ap(String find_pom_ap) {
        this.find_pom_ap = find_pom_ap;
    }

    public String getFind_pom_anp() {
        return find_pom_anp;
    }

    public void setFind_pom_anp(String find_pom_anp) {
        this.find_pom_anp = find_pom_anp;
    }

    public String getMma_apj() {
        return mma_apj;
    }

    public void setMma_apj(String mma_apj) {
        this.mma_apj = mma_apj;
    }

    public String getMma_apaj() {
        return mma_apaj;
    }

    public void setMma_apaj(String mma_apaj) {
        this.mma_apaj = mma_apaj;
    }

    public String getMma_acp() {
        return mma_acp;
    }

    public void setMma_acp(String mma_acp) {
        this.mma_acp = mma_acp;
    }

    public String getPt_ap() {
        return pt_ap;
    }

    public void setPt_ap(String pt_ap) {
        this.pt_ap = pt_ap;
    }

    public String getPt_anp() {
        return pt_anp;
    }

    public void setPt_anp(String pt_anp) {
        this.pt_anp = pt_anp;
    }

}
