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
public class Usuario extends SuperModel implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private int uid;
    private String ref;
    private String nome;
    private String create_date;
    boolean activo;

    private String login;
    private String senha;
    private String tipo;

    public Usuario() {
    }

    public Usuario(String ref, String nome, boolean activo, String login, String senha) {
        this.ref = ref;
        this.nome = nome;
        this.activo = activo;
        this.login = login;
        this.senha = senha;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String getSrc() {
        return id + "|" + new ControlaSuperModel(this).getExpressinById(this, uid) + "|" + ref + "|" + nome + "|" + create_date + "|" + activo + "|" + login + "|" + senha + "|" + tipo;
    }

}
