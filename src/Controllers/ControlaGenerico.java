package Controllers;
 
import Conexao.Util; 
import java.util.ArrayList; 
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ControlaGenerico<G> {
    
    public final SessionFactory sf = Util.getSessionFactory();
    Session sessao;
    Transaction ts;
    
    private final Object model;
    
    private void print(Object msg) {
        System.out.println("[" + className + "] " + msg);
    }
    
    public SessionFactory getSF() {
        return sf;
    }
    String className = "";
    
    public ControlaGenerico(Object model) {
        this.model = model;
        className = model.getClass().getName(); 
    }
    
    public void habilitaSessao() {
        sessao = sf.openSession();
        ts = sessao.beginTransaction();
    }
    
    public boolean commit_AND_Close() {
        boolean did = true;
        try {
            ts.commit();
            sessao.flush();
            sessao.close();
        } catch (Exception e) {
            did = false;
        }
        return did;
    }
    
    public boolean create(G ob) {
        boolean did = true;
        try {
            habilitaSessao();
            sessao.save(ob);
        } catch (Exception ex) {
            print("create.ex: " + ex);
            did = false;
        } finally {
            did = commit_AND_Close();
        }
        return did;
    }
    
    public boolean write(G ob) {
        boolean did = true;
        try {
            habilitaSessao();
            sessao.update(ob);
        } catch (Exception ex) {
            print("write.ex: " + ex);
            did = false;
        } finally {
            did = commit_AND_Close();
        }
        return did;
    }
    
    public boolean delete(G ob) {
        boolean did = true;
        try {
            habilitaSessao();
            sessao.delete(ob);
        } catch (Exception ex) {
            print("delete.ex: " + ex);
            did = false;
        } finally {
            did = commit_AND_Close();
        }
        return did;
    }

    public G search(int id) {
        G e = null;
        try {
            habilitaSessao();
            e = (G) sessao.get((Class) model.getClass(), id);
        } catch (Exception ex) {
            print("search.ex: " + ex);
        } finally {
            commit_AND_Close();
        }
        return e;
    }
    public ArrayList<G> getLista() {
        ArrayList<G> lst = new ArrayList<>();
        try {
            habilitaSessao();
            List<G> l = (List<G>) sessao.createCriteria((Class) model.getClass()).list();
            for (G ob : l) {
                lst.add(ob);
            }
        } catch (HibernateException ex) {
            print("getLista.ex: " + ex);
        } finally {
            commit_AND_Close();
        }
        return lst;
    }
    public G getLast() {
        ArrayList<G> lst = getLista();
        return lst.get(lst.size() - 1);
    }
}
