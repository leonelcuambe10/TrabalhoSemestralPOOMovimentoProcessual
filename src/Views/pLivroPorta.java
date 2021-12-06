/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.ControlaSuperModel;
import Jars.DH;
import Jars.Metodos;
import Jars.ShowChild;
import Jars.pCalendar;
import Models.SuperModel;
import Models.Livro_Porta;
import Models.Natureza_Processo;
import Models.Tribunal;
import Models.Usuario;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author LEONEL
 */
public class pLivroPorta extends javax.swing.JPanel {

    ControlaSuperModel con = new ControlaSuperModel(new Livro_Porta());
    Metodos met = new Metodos();
    Usuario uid = null;
    Livro_Porta selected = null;
    DH dh = new DH();
    ArrayList<SuperModel> lstModel;

    /**
     * Creates new form pUsuari
     */
    public pLivroPorta(JFrame self, Usuario uid) {
        this.uid = uid;
        initComponents();
        evt();
        con.addModel(cboTribunal, new Tribunal());
        con.addModel(cboNatureza, new Natureza_Processo());
        new ShowChild(self, new pCalendar(txtDataEntrada), txtDataEntrada, "Data de Entrada");
    }

    private void evt() {
        cboSenteca.setModel(new DefaultComboBoxModel<>(sentenca_entr));
        con.listarNaTabela(tabela, new Livro_Porta());
        JButton[] btns = new JButton[]{btnSalvar, btnRemover, btnLimpar, btnTransitar};
        for (JButton btn : btns) {
            btn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (btn == btnSalvar) {
                        def_salvar();
                    } else if (btn == btnRemover) {
                        def_delete();
                    } else if (btn == btnLimpar) {
                        def_limpar();
                    } else if (btn == btnTransitar) {
                        def_transitar();
                    }
                }

                private void def_transitar() {
                    if (selected != null) {
                        Livro_Porta o = getDados();
                        if (o != null) {
                            o.setId(selected.getId());
                            o.setSenteca("Transitado");
                            if (isValid(o)) {
                                o.ponto = 0;
                                if (con.write(o)) {
                                    met.msg(pBtn, "Transitado!");
                                }
                                con.listarNaTabela(tabela, new Livro_Porta());
                                def_limpar();
                            }
                        }
                    }
                }
            });
        }
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                def_pesquisa();
            }
        });
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearch.requestFocus();
                def_pesquisa();
            }
        });
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                def_tabela();
            }
        });
        tabela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                def_tabela();
            }
        });
    }

    private void def_tabela() {
        if (tabela.getSelectedRow() != -1) {
            int id = (Integer) tabela.getValueAt(tabela.getSelectedRow(), 0);
            selected = (Livro_Porta) con.search(id);
            if (selected != null) {
                preencheDados(selected);
            }
        }
    }

    private void enableCbo(boolean enable) {
        cboTribunal.setEnabled(enable);
        cboNatureza.setEnabled(enable);
        cboSenteca.setEnabled(enable);
        txtRef.setEnabled(enable);
        txtNome.setEnabled(enable);
        txtAutor.setEnabled(enable);
        txtArguido.setEnabled(enable);
        txtDataEntrada.setEnabled(enable);
        cboSituacao.setEnabled(enable);

        btnSalvar.setEnabled(enable);
        btnRemover.setEnabled(enable);
        btnTransitar.setEnabled(enable);
    }

    String[] sentenca_trans = new String[]{"Transitado",};
    String[] sentenca_entr = new String[]{"Entrado",};
    String[] sentenca_find = new String[]{"", "Findo por Sentenca", "Findo por Outros Motivos"};

    public void preencheDados(SuperModel model) {
        if (model != null) {
            if (model instanceof Livro_Porta) {
                enableCbo(true);
                String senteca = selected.getSenteca();
                cboSenteca.setModel(new DefaultComboBoxModel<>(sentenca_find));
                if (!senteca.equals("Entrado") && !senteca.equals("Transitado")) {
                    enableCbo(false);
                }
                String[] din_vals = dh.getDataVals(selected.getData_entrada());
                String[] dhj_vals = dh.getDataVals(dh.getData());
                if (senteca.equals("Transitado") && (din_vals[1] + "/" + din_vals[2]).equals((dhj_vals[1] + "/" + dhj_vals[2]))) {
                    cboSenteca.setModel(new DefaultComboBoxModel<>(sentenca_trans));
                    enableCbo(false);
                }
                if (senteca.equals("Transitado")) {
                    btnTransitar.setEnabled(false);
                }
                selected = (Livro_Porta) model;
                met.setTxt(txtRef, selected.getRef());
                met.setTxt(txtNome, selected.getNome());
                met.setTxt(cboTribunal, con.getExpressinById(new Tribunal(), selected.getTribunal_id()));
                met.setTxt(cboNatureza, con.getExpressinById(new Natureza_Processo(), selected.getNatureza_id()));
                met.setTxt(txtAutor, selected.getAutor());
                met.setTxt(txtArguido, selected.getArguido());
                met.setTxt(txtDataEntrada, selected.getData_entrada());
                met.setTxt(txtDataEntrada, selected.getData_entrada());
                met.setTxt(cboSenteca, selected.getSenteca());
                met.setTxt(cboSituacao, selected.getEstado());
            }
        } else {
            def_limpar();
        }
    }

    public void def_limpar() {
        enableCbo(true);
        cboSenteca.setModel(new DefaultComboBoxModel<>(sentenca_entr));
        con.addModel(cboTribunal, new Tribunal());
        con.addModel(cboNatureza, new Natureza_Processo());
        met.setTxt(txtRef, "");
        met.setTxt(txtNome, "");
        met.setTxt(cboTribunal, "");
        met.setTxt(cboNatureza, "");
        met.setTxt(txtAutor, "");
        met.setTxt(txtArguido, "");
        met.setTxt(txtDataEntrada, "");
        met.setTxt(txtDataEntrada, "");
        met.setTxt(cboSenteca, "");
        met.setTxt(cboSituacao, "");
        selected = null;
    }

    private Livro_Porta getDados() {
        String ref = met.getTxt(txtRef);
        String nome = met.getTxt(txtNome);
        boolean activo = true;

        int tribunal_id = con.getIdByExpressin(new Tribunal(), met.getTxt(cboTribunal));
        int natureza_id = con.getIdByExpressin(new Natureza_Processo(), met.getTxt(cboNatureza));
        String data_entrada = met.getTxt(txtDataEntrada);
        String data_findo = "";
        String senteca = met.getTxt(cboSenteca);//(=estado findo)
        String nr_processo = ref;

        String autor = met.getTxt(txtAutor);//(do processo)
        String arguido = met.getTxt(txtArguido);
        String estado = met.getTxt(cboSituacao);
        if (uid != null) {
            if (met.isValid(txtRef, 3, 10, "Nr do Processo Inválido!")) {
                if (met.isValid(txtNome, 3, 48, "Descricao Inválida!")) {
                    if (met.isValid(txtDataEntrada, 3, 15, "Data de entrada!")) {
                        if (met.isValid(txtAutor, 3, 25, "Autor Inválido!")) {
                            if (met.isValid(txtArguido, 3, 25, "Autor Inválido!")) {
                                return new Livro_Porta(ref, nome, tribunal_id, natureza_id, data_entrada, data_findo, senteca, nr_processo, autor, arguido, estado, dh.getData());
                            }
                        }
                    }
                }
            }
        } else {
            met.msg(pBtn, "Usuário Logado Inválido!");
        }
        return null;
    }

    private boolean isValid(Livro_Porta o) {
        for (SuperModel sm : con.getLista()) {
            Livro_Porta id = (Livro_Porta) sm;
            if (id.getId() != o.getId()) {
                if (id.getRef().equalsIgnoreCase(o.getRef())) {
                    met.msg(pBtn, "Ja existe um usuario com esta referencia!\nID: " + id.getId() + ", [" + id.getRef() + "]" + id.getNome());
                    return false;
                }
            }
        }
        return true;
    }

    public void def_salvar() {
        Livro_Porta o = getDados();
        boolean canClean = false;
        if (o != null) {
            if (selected == null) {//pesquisaPrimaria() nuit user
                if (isValid(o)) {
                    o.setCreate_date(dh.getData());
                    o.setUid(uid.getId());
                    o.ponto = 1;
                    if (con.create(o)) {
                        met.msg(pBtn, "Salvo!");
                        canClean = true;
                    }
                }
            } else {
                int id = selected.getId();
                o.setId(id);
                if (isValid(o)) {
                    if (selected.getSenteca().equals("Entrado") && o.getSenteca().equals("")) {
                        o.setSenteca("Entrado");
                    }
                    if (!selected.getSenteca().equals("Transitado")) {
                        o.ponto = 1;
                    }
                    if (con.write(o)) {
                        met.msg(pBtn, "Actualizaso!");
                        canClean = true;
                    }
                }
            }
            con.listarNaTabela(tabela, new Livro_Porta());
            if (canClean) {
                def_limpar();
            }
        }
    }

    public void def_delete() {
        if (selected != null) {
            boolean canClean = false;
            Livro_Porta o = (Livro_Porta) selected;
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
            con.listarNaTabela(tabela, new Livro_Porta(), lstModel);
        } else {
            con.listarNaTabela(tabela, new Livro_Porta());
        }
        con.addModel(cboTribunal, new Tribunal());
        con.addModel(cboNatureza, new Natureza_Processo());
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDataEntrada = new javax.swing.JTextField();
        cboNatureza = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboSenteca = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtArguido = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cboTribunal = new javax.swing.JComboBox<>();
        cboSituacao = new javax.swing.JComboBox<>();
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
        btnTransitar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(973, 300));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel7.setPreferredSize(new java.awt.Dimension(973, 30));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel1.setText("Livro de Porta");
        jPanel7.add(jLabel1, java.awt.BorderLayout.CENTER);

        jLabel2.setPreferredSize(new java.awt.Dimension(60, 15));
        jPanel7.add(jLabel2, java.awt.BorderLayout.LINE_START);

        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Nr Processo");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Descricao");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Tribunal");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Data de Entrada");

        txtDataEntrada.setEditable(false);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Situacao do Arguido");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Senteca");

        cboSenteca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrado" }));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Autor");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Arguido");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Natureza");

        cboSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AP", "ANP" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRef, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTribunal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboNatureza, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtArguido, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDataEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                                .addComponent(cboSenteca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(41, 41, 41))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRef, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboSenteca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTribunal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboNatureza)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtArguido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );

        jPanel1.add(jPanel8, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(973, 30));
        jPanel3.setLayout(new java.awt.BorderLayout(5, 5));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(260, 40));
        jPanel4.setLayout(new java.awt.BorderLayout(2, 2));

        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search_20px.png"))); // NOI18N
        lblSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSearch.setPreferredSize(new java.awt.Dimension(30, 15));
        jPanel4.add(lblSearch, java.awt.BorderLayout.LINE_END);

        txtSearch.setBorder(null);
        txtSearch.setPreferredSize(new java.awt.Dimension(40, 15));
        jPanel4.add(txtSearch, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel5.setPreferredSize(new java.awt.Dimension(260, 40));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
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

        btnTransitar.setText("Transitar");
        pBtn.add(btnTransitar);

        jPanel3.add(pBtn, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NR PROCESSO", "DESCR", "NATUREZA", "AUTOR", "ARGUIDO", "D.ENTRADA", "SENTECA"
            }
        ));
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(40);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabela.getColumnModel().getColumn(0).setMaxWidth(40);
            tabela.getColumnModel().getColumn(3).setMinWidth(70);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(70);
        }

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnTransitar;
    private javax.swing.JComboBox<String> cboNatureza;
    private javax.swing.JComboBox<String> cboSenteca;
    private javax.swing.JComboBox<String> cboSituacao;
    private javax.swing.JComboBox<String> cboTribunal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JTextField txtArguido;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtDataEntrada;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtRef;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
