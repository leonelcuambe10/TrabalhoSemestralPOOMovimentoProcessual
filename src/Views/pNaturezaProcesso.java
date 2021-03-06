/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.ControlaSuperModel;
import Jars.DH;
import Jars.Metodos;
import Models.SuperModel;
import Models.Natureza_Processo;
import Models.Usuario;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author LEONEL
 */
public class pNaturezaProcesso extends javax.swing.JPanel {

    ControlaSuperModel con = new ControlaSuperModel(new Natureza_Processo());
    Metodos met = new Metodos();
    Usuario uid = null;
    Natureza_Processo selected = null;
    DH dh = new DH();
    ArrayList<SuperModel> lstModel;

    /**
     * Creates new form pUsuari
     */
    public pNaturezaProcesso(Usuario uid) {
        this.uid = uid;
        initComponents();
        evt();
    }

    private void evt() {
        con.listarNaTabela(tabela, new Natureza_Processo());
        JButton[] btns = new JButton[]{btnSalvar, btnRemover, btnLimpar};
        for (JButton btn : btns) {
            btn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (btn == btnSalvar) {
                        def_salvar();
                    } else if (btn == btnRemover) {
                        def_delete();
                    } else if (btn == btnLimpar) {
                        def_limpar();
                    }
                }
            });
        }
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                def_pesquisa();txtSearch.requestFocus();
            }
        });
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                def_pesquisa();
            }
        });
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (tabela.getSelectedRow() != -1) {
                    int id = (Integer) tabela.getValueAt(tabela.getSelectedRow(), 0);
                    selected = (Natureza_Processo) con.search(id);
                    if (selected != null) {
                        preencheDados(selected);
                    }
                }
            }
        });
    }

    private Natureza_Processo getDados() {
        String ref = met.getTxt(txtRef);
        String nome = met.getTxt(txtNome);
        boolean activo = true; 
        if (uid != null) {
            if (met.isValid(txtNome, 3, 48, "Nome Inv??lido!")) {
                return new Natureza_Processo(ref, nome);
            }
        } else {
            met.msg(pBtn, "Usu??rio Logado Inv??lido!");
        }
        return null;
    }

    private boolean isValid(Natureza_Processo o) {
        for (SuperModel sm : con.getLista()) {
            Natureza_Processo id = (Natureza_Processo) sm;
            if (id.getId() != o.getId()) {
                if (id.getRef().equalsIgnoreCase(o.getRef())) {
                    met.msg(pBtn, "Ja existe um usuario com esta referencia!\nID: " + id.getId() + ", [" + id.getRef() + "]" + id.getNome());
                    return false;
                }
                if (id.getNome().equalsIgnoreCase(o.getNome())) {
                    met.msg(pBtn, "Ja existe um usuario com este login!\nID: " + id.getId() + ", [" + id.getRef() + "]" + id.getNome());
                    return false;
                }
            }
        }
        return true;
    }

    public void def_salvar() {
        Natureza_Processo o = getDados();
        boolean canClean = false;
        if (o != null) {
            if (selected == null) {//pesquisaPrimaria() nuit user
                if (isValid(o)) {
                    o.setCreate_date(dh.getData());
                    o.setUid(uid.getId());
                    if (con.create(o)) {
                        o = (Natureza_Processo) con.getLast();
                        o.setRef("NP" + con._add_digitos(o.getId()));
                        con.write(o);
                        met.msg(pBtn, "Salvo!");
                        canClean = true;
                    }
                }
            } else {
                int id = selected.getId();
                o.setId(id);
                if (isValid(o)) {
                    if (con.write(o)) {
                        met.msg(pBtn, "Actualizaso!");
                        canClean = true;
                    }
                }
            }
            con.listarNaTabela(tabela, new Natureza_Processo());
            if (canClean) {
                def_limpar();
            }
        }
    }

    public void def_delete() {
        if (selected != null) {
            boolean canClean = false;
            Natureza_Processo o = (Natureza_Processo) selected;
            if (met.confirm(pBtn, "Deseja eliminar?\nVai perder este registo!")) {
                if (con.delete(o)) {
                    canClean = true;
                    met.msg(pBtn, "Eliminado!");
                }
            }
            if (canClean) {
                def_limpar();
            }
        } else {
            met.msg(pBtn, "Clique sobre o registo a eliminar, na tabela!");
        }
    }

    private void def_pesquisa() {
        String txt = met.getTxt(txtSearch);
        ArrayList<String> palavras = met.getPalavrasOf(txt, ".|-; ");
        lstModel = new ArrayList<>();
        for (SuperModel model : con.getLista()) {
            String to = "" + model.getSrc();
            if (met.x_contain_y(to, txt)) {
                lstModel.add(model);
            } else {
                for (String palavra : palavras) {
                    if (met.x_contain_y(to, palavra) && !lstModel.contains(model)) {
                        lstModel.add(model);
                    }
                }
            }
        }
        if (txt.length() > 0) {
            con.listarNaTabela(tabela, new Natureza_Processo(), lstModel);
        } else {
            con.listarNaTabela(tabela, new Natureza_Processo());
        }
    }

    public void preencheDados(SuperModel model) {
        if (model != null) {
            if (model instanceof Natureza_Processo) {
                selected = (Natureza_Processo) model;
                met.setTxt(txtRef, selected.getRef());
                met.setTxt(txtNome, selected.getNome()); 
            }
        } else {
            def_limpar();
        }
    }

    public void def_limpar() {
        met.setTxt(txtRef, "");
        met.setTxt(txtNome, ""); 
        selected = null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtRef = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        pBtn = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(973, 300));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel7.setPreferredSize(new java.awt.Dimension(973, 30));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel1.setText("Natureza do Processo");
        jPanel7.add(jLabel1, java.awt.BorderLayout.CENTER);

        jLabel2.setPreferredSize(new java.awt.Dimension(60, 15));
        jPanel7.add(jLabel2, java.awt.BorderLayout.LINE_START);

        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Refer??ncia");

        txtRef.setEditable(false);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nome");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRef, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRef, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(182, 182, 182))
        );

        jPanel1.add(jPanel8, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(973, 30));
        jPanel3.setLayout(new java.awt.BorderLayout(5, 5));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 15));
        jPanel4.setLayout(new java.awt.BorderLayout(2, 2));

        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search_20px.png"))); // NOI18N
        lblSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSearch.setPreferredSize(new java.awt.Dimension(30, 15));
        jPanel4.add(lblSearch, java.awt.BorderLayout.LINE_END);

        txtSearch.setBorder(null);
        jPanel4.add(txtSearch, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel5.setPreferredSize(new java.awt.Dimension(300, 40));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5, java.awt.BorderLayout.LINE_START);

        pBtn.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        btnSalvar.setText("Salvar");
        pBtn.add(btnSalvar);

        btnRemover.setText("Remover");
        pBtn.add(btnRemover);

        btnLimpar.setText("Limpar");
        pBtn.add(btnLimpar);

        jPanel3.add(pBtn, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "REF", "NOME"
            }
        ));
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(40);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabela.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JPanel pBtn;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtRef;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
