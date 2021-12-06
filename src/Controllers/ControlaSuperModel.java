/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Jars.DH;
import Jars.Metodos;
import Models.Livro_Porta;
import Models.Natureza_Processo;
import Models.SuperModel;
import Models.Tribunal;
import Models.Usuario;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LEONEL
 */
public class ControlaSuperModel extends ControlaGenerico<SuperModel> {

    SuperModel model;
    Metodos met = new Metodos();

    public ControlaSuperModel(SuperModel model) {
        super(model);
        this.model = model;
        if (model instanceof Usuario && getLista().size() == 0) {
            Usuario o = new Usuario("U00001", "Leonel Cuambe", true, "admin", "admin");
            o.setActivo(true);
            o.setCreate_date(new DH().getData());
            o.setUid(1);
            o.setTipo("Administrador");
            create(o);
        }
    }

    public void print(Object msg) {
        System.out.println(msg);
    }

    public void listarNaTabela(JTable tabela, SuperModel parent) {
        ((DefaultTableModel) tabela.getModel()).setNumRows(0);
        for (SuperModel o : getLista()) {
            row(tabela, o, parent);
        }
    }

    public void listarNaTabela(JTable tabela, SuperModel parent, ArrayList<SuperModel> lista) {
        ((DefaultTableModel) tabela.getModel()).setNumRows(0);
        for (SuperModel o : lista) {
            row(tabela, o, parent);
        }
    }

    public void row(JTable tabela, SuperModel ob, SuperModel parent) {
        DefaultTableModel instanceModelo = (DefaultTableModel) tabela.getModel();
        Object[] row = {};
        if (ob instanceof Usuario && parent instanceof Usuario) {
            Usuario o = (Usuario) ob;
            row = new Object[]{o.getId(), o.getRef(), o.getNome(), o.getLogin(), o.getTipo()};
        } else if (ob instanceof Tribunal && parent instanceof Tribunal) {
            Tribunal o = (Tribunal) ob;
            row = new Object[]{o.getId(), o.getRef(), o.getNome(), o.getSeccao()};
        } else if (ob instanceof Natureza_Processo && parent instanceof Natureza_Processo) {
            Natureza_Processo o = (Natureza_Processo) ob;
            row = new Object[]{o.getId(), o.getRef(), o.getNome()};
        } else if (ob instanceof Livro_Porta && parent instanceof Livro_Porta) {
            Livro_Porta o = (Livro_Porta) ob;//"ID", "NR PROCESSO", "DESCR", "NATUREZA", "AUTOR", "ARGUIDO", "D.ENTRADA", "SENTECA"
            String natureza = ((Natureza_Processo) new ControlaSuperModel(new Natureza_Processo()).search(o.getNatureza_id())).getNome();
            row = new Object[]{o.getId(), o.getRef(), o.getNome(), natureza, o.getAutor(), o.getArguido(), o.getData_entrada(), o.getSenteca(), o.getData_findo()};
        }
        instanceModelo.addRow(row);
    }

    public String _add_digitos(int valor) {
        String str_nr = "" + valor;
        while (str_nr.length() < 5) {
            str_nr = "0" + str_nr;
        }
        return str_nr;
    }

    public int getIdByExpressin(SuperModel model, String expression) {
        ArrayList<String> palavras = met.getPalavrasOf(expression, ".|-;");
        ArrayList<SuperModel> ids = new ControlaSuperModel(model).getLista();
        for (SuperModel id : ids) {
            if (model instanceof Natureza_Processo && id.getClass().equals(model.getClass())) {
                Natureza_Processo o = (Natureza_Processo) id;
                if (o.getRef().equals(palavras.get(0))) {
                    return o.getId();
                }
            }else  if (model instanceof Tribunal && id.getClass().equals(model.getClass())) {
                Tribunal o = (Tribunal) id;
                if (o.getRef().equals(palavras.get(0))) {
                    return o.getId();
                }
            }
        }
        return 0;
    }

    public String getExpressinById(SuperModel model, int id) {
        ArrayList<SuperModel> ids = new ControlaSuperModel(model).getLista();
        for (SuperModel ms : ids) {
            if (model instanceof Natureza_Processo && ms.getClass().equals(model.getClass())) {
                Natureza_Processo o = (Natureza_Processo) ms;
                if (o.getId() == id) {
                    return o.getRef() + "|" + o.getNome();
                }
            }else if (model instanceof Tribunal && ms.getClass().equals(model.getClass())) {
                Tribunal o = (Tribunal) ms;
                if (o.getId() == id) {
                    return o.getRef() + "|" + o.getNome();
                }
            }
        }
        return "";
    }

    public void addModel(JComboBox cbo, SuperModel model) {
        cbo.removeAllItems();;
        ArrayList<SuperModel> ids = new ControlaSuperModel(model).getLista();
//        met.print("con.super.model: ids: "+ids);
        for (SuperModel ms : ids) {
            if (model instanceof Natureza_Processo && ms.getClass().equals(model.getClass())) {
                Natureza_Processo o = (Natureza_Processo) ms;
                cbo.addItem("" + o.getRef() + "|" + o.getNome());
            }else if (model instanceof Tribunal && ms.getClass().equals(model.getClass())) {
                Tribunal o = (Tribunal) ms;
                cbo.addItem("" + o.getRef() + "|" + o.getNome());
            }
        }
    }
}
