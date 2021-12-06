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
import Models.Livro_Porta;
import Models.Natureza_Processo;
import Models.Relatorio;
import Models.SuperModel;
import Models.Tribunal;
import Models.Usuario;
import Report.Imprimir;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LEONEL
 */
public class pRelatorio extends javax.swing.JPanel {

    Metodos met = new Metodos();
    ControlaSuperModel con = new ControlaSuperModel(new Livro_Porta());
    Usuario uid = null;
    DH dh = new DH();
    ArrayList<Relatorio> lstRelatorio;

    /**
     * Creates new form pRelatorio
     */
    public pRelatorio(JFrame self, Usuario uid) {
        initComponents();
        this.uid = uid;
        new ShowChild(self, new pCalendar(lblDataDe), lblDataDe, "Periodo Inicial");
        new ShowChild(self, new pCalendar(lblDataAte), lblDataAte, "Periodo Final");
        evt(self);
    }

    private void evt(JFrame self) {
        met.setTxt(lblDataDe, dh.getData());
        met.setTxt(lblDataAte, dh.getData());
        con.addModel(cboTribunal, new Tribunal());
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (lstRelatorio.size() > 0) {
                    new ShowChild(self, new Imprimir().imprimir(lstRelatorio), "Relatorio");
                } else {
                    met.msg(btnImprimir, "Tabela vazia.");
                }
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                def_pesquisa();
            }
        });
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                def_pesquisa();
            }
        });
//        lblDataDe.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
//            public void propertyChange(java.beans.PropertyChangeEvent evt) {
//                def_pesquisa();
//            }
//        });
//        lblDataAte.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
//            public void propertyChange(java.beans.PropertyChangeEvent evt) {
//                def_pesquisa();
//            }
//        });
//        cboTribunal.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                def_pesquisa();
//            }
//        });
    }

    private boolean canAdd(Livro_Porta o, int trib_id, String de, String ate) {
        if (o.getTribunal_id() == trib_id) {
            boolean from = dh.isData_X_MaiorIgualQue_Y(o.data_actualizacao, de);
            boolean to = dh.isData_X_MaiorIgualQue_Y(ate, o.data_actualizacao);
            boolean de_IsGreat = dh.isData_X_MaiorIgualQue_Y(de, o.data_actualizacao);
            if (from && to || (o.getSenteca().equals("Transitado") && de_IsGreat)) {
                return true;
            }
            boolean isTransitedOnThisMonth = dh.isData_X_MaiorIgualQue_Y(de, o.data_actualizacao);
            if (o.getSenteca().equals("Entrado") && isTransitedOnThisMonth) {
                return true;
            }
        }
        return false;
    }

    private void def_pesquisa() {
        String txt = met.getTxt(txtSearch);
        String de = met.getTxt(lblDataDe);
        String ate = met.getTxt(lblDataAte);

        int trib_src = con.getIdByExpressin(new Tribunal(), met.getTxt(cboTribunal));
        Tribunal trib_id = (Tribunal) new ControlaSuperModel(new Tribunal()).search(trib_src);

        ArrayList<String> palavras = met.getPalavrasOf(txt, ".|-; ");
        ArrayList<Integer> natu_ids = new ArrayList<>();
        ArrayList<Livro_Porta> lista = new ArrayList<>();

        for (SuperModel model : con.getLista()) {
            Livro_Porta o = (Livro_Porta) model;
            if (canAdd(o, trib_id.getId(), de, ate)) {
                String to = "" + o.getSrc();
                if (met.trimValue(txt).length() == 0) {
                    lista.add(o);
                    if (!natu_ids.contains(o.getNatureza_id())) {
                        natu_ids.add(o.getNatureza_id());
                    }
                } else {
                    if (met.x_contain_y(to, txt)) {
                        lista.add(o);
                        if (!natu_ids.contains(o.getNatureza_id())) {
                            natu_ids.add(o.getNatureza_id());
                        }
                    } else {
                        for (String palavra : palavras) {
                            if (met.x_contain_y(to, palavra) && !lista.contains(o)) {
                                lista.add(o);
                                if (!natu_ids.contains(o.getNatureza_id())) {
                                    natu_ids.add(o.getNatureza_id());
                                }
                            }
                        }
                    }
                }
            }
        }
        lstRelatorio = new ArrayList<>();
//        met.print("lista: " + lista); 
        for (Integer natu_id : natu_ids) {
            String natureza = ((Natureza_Processo) new ControlaSuperModel(new Natureza_Processo()).search(natu_id)).getNome();
            int pend_ap = 0;
            int pend_anp = 0;
            int entr_ap = 0;
            int entr_anp = 0;
            int find_ps_ap = 0;
            int find_ps_anp = 0;
            int find_pom_ap = 0;
            int find_pom_anp = 0;
            int pt_ap = 0;
            int pt_anp = 0;
            for (Livro_Porta livro : lista) {
                if (natu_id == livro.getNatureza_id()) {
                    String sentenca = livro.getSenteca();
                    if (sentenca.equals("Transitado")) {
                        boolean isTransitedOnThisMonth = dh.isData_X_MaiorIgualQue_Y(livro.data_actualizacao, de);
                        if (isTransitedOnThisMonth) {
                            if (livro.getEstado().equals("AP")) {
                                pt_ap++;
                                entr_ap++;
                            } else {
                                pt_anp++;
                                entr_anp++;
                            }
                        } else {
                            if (livro.getEstado().equals("AP")) {
                                pend_ap++;
                            } else {
                                pend_anp++;
                            }
                        }
                    } else {
                        boolean isTransitedOnThisMonth = dh.isData_X_MaiorIgualQue_Y(de, livro.data_actualizacao);
                        if (sentenca.equals("Entrado") && isTransitedOnThisMonth) {
                            if (livro.getEstado().equals("AP")) {
                                pend_ap++;
                            } else {
                                pend_anp++;
                            }
                        } else {
                            if (livro.getEstado().equals("AP")) {
                                entr_ap += 1;
                            } else {
                                entr_anp += 1;
                            }
                            if (sentenca.equals("Findo por Sentenca")) {
                                if (livro.getEstado().equals("AP")) {
                                    find_ps_ap++;
                                } else {
                                    find_ps_anp++;
                                }
                            } else if (sentenca.equals("Findo por Outros Motivos")) {
                                if (livro.getEstado().equals("AP")) {
                                    find_pom_ap++;
                                } else {
                                    find_pom_anp++;
                                }
                            }
                        }
                    }
                }
            }
            pt_ap += pend_ap + entr_ap - find_ps_ap - find_pom_ap;
            pt_anp += pend_anp + entr_anp - find_ps_anp - find_pom_anp;
//            met.print("2. pt_ap: " + pt_ap + ",  pt_anp: " + pt_anp);
            lstRelatorio.add(new Relatorio(trib_id.getNome(), de, ate, natureza, pend_ap, pend_anp, entr_ap, entr_anp, find_ps_ap, find_ps_anp, find_pom_ap, find_pom_anp, pt_ap, pt_anp));
        }
        listarNaTabela(lstRelatorio);
        txtSearch.requestFocus();
    }

    private void listarNaTabela(ArrayList<Relatorio> lista) {
        ((DefaultTableModel) tabela.getModel()).setNumRows(0);
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        //modelo.addRow(new Object[]{"id", "", "", "", "Sentenca|Outro", "M. M. A", ""});
        //modelo.addRow(new Object[]{"id", "", "AP     |     ANP", "AP     |     ANP", "AP  |  ANP  -  AP  |  ANP", "APJ  |  APAJ  |  ACP", "AP     |     ANP"});
        int i = 1;
        for (Relatorio o : lista) {//"ID", "NATUREZA", "PENDENTES", "ENTRADOS", "FINDOS", "M. M. A", "TRANSITADOS"
            Object[] row = {
                i,
                o.natureza,//NATUREZA
                "AP = " + o.pend_ap + ",  ANP = " + o.pend_anp, // PENDENTES
                "AP = " + o.entr_ap + ",  ANP = " + o.entr_anp, // ENTRADOS                
                "PS-AP = " + o.find_ps_ap + ",  PS-ANP = " + o.find_ps_anp + ",  OM-AP = " + o.find_pom_ap + ",  OM-ANP = " + o.find_pom_anp, // FINDOS
                "APJ = " + o.mma_apj + ",  APAJ = " + o.mma_apaj + ",  ACP = " + o.mma_acp, // M. M. A
                "AP = " + o.pt_ap + ",  ANP = " + o.pt_anp, // TRANSITADOS 
            };
            modelo.addRow(row);
            i++;
        }
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
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cboTribunal = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        pDataDe = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblDataDe = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblDataAte = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout(2, 2));

        jPanel1.setPreferredSize(new java.awt.Dimension(893, 80));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 2));

        jPanel4.setPreferredSize(new java.awt.Dimension(893, 30));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel2.setText("Mapa do Movimento de Processos Crime");
        jPanel4.add(jLabel2, java.awt.BorderLayout.CENTER);

        jLabel3.setPreferredSize(new java.awt.Dimension(60, 15));
        jPanel4.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setLayout(new java.awt.BorderLayout(5, 0));

        jPanel10.setPreferredSize(new java.awt.Dimension(450, 47));
        jPanel10.setLayout(new java.awt.GridLayout(1, 5, 5, 5));

        jPanel3.setLayout(new java.awt.BorderLayout(0, 2));

        jLabel4.setText("Tribunal");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 18));
        jPanel3.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        cboTribunal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Livro de Porta" }));
        jPanel3.add(cboTribunal, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel3);

        jPanel11.setLayout(new java.awt.BorderLayout(0, 2));

        jLabel6.setText("Periodo");
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel11.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jPanel12.setLayout(new java.awt.GridLayout(1, 5, 5, 0));

        pDataDe.setBackground(new java.awt.Color(255, 255, 255));
        pDataDe.setLayout(new java.awt.BorderLayout(2, 0));

        jLabel8.setText("De:");
        pDataDe.add(jLabel8, java.awt.BorderLayout.LINE_START);

        lblDataDe.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        lblDataDe.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDataDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar_16px.png"))); // NOI18N
        lblDataDe.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblDataDe.setIconTextGap(10);
        pDataDe.add(lblDataDe, java.awt.BorderLayout.CENTER);

        jPanel12.add(pDataDe);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.BorderLayout(2, 0));

        jLabel9.setText("Ate:");
        jPanel14.add(jLabel9, java.awt.BorderLayout.LINE_START);

        lblDataAte.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        lblDataAte.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDataAte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar_16px.png"))); // NOI18N
        lblDataAte.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblDataAte.setIconTextGap(10);
        jPanel14.add(lblDataAte, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel14);

        jPanel11.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel11);

        jPanel5.add(jPanel10, java.awt.BorderLayout.LINE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel7.setPreferredSize(new java.awt.Dimension(80, 47));
        jPanel7.setLayout(new java.awt.BorderLayout(0, 2));

        jLabel1.setPreferredSize(new java.awt.Dimension(34, 18));
        jPanel7.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jButton1.setText("Refresh");
        jButton1.setPreferredSize(new java.awt.Dimension(60, 10));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel7, java.awt.BorderLayout.LINE_END);

        jPanel8.setPreferredSize(new java.awt.Dimension(400, 32));
        jPanel8.setLayout(new java.awt.BorderLayout(0, 2));

        jLabel7.setText("Pesquisar");
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 20));
        jPanel8.add(jLabel7, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(100, 15));
        jPanel9.setLayout(new java.awt.BorderLayout(2, 2));

        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search_20px.png"))); // NOI18N
        lblSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSearch.setPreferredSize(new java.awt.Dimension(30, 15));
        jPanel9.add(lblSearch, java.awt.BorderLayout.LINE_END);

        txtSearch.setBorder(null);
        txtSearch.setPreferredSize(new java.awt.Dimension(50, 10));
        jPanel9.add(txtSearch, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "NATUREZA", "PENDENTES", "ENTRADOS", "FINDOS", "M. M. A", "TRANSITADOS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(30);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
            tabela.getColumnModel().getColumn(0).setMaxWidth(40);
            tabela.getColumnModel().getColumn(1).setMinWidth(100);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(130);
            tabela.getColumnModel().getColumn(1).setMaxWidth(200);
            tabela.getColumnModel().getColumn(2).setMinWidth(110);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(110);
            tabela.getColumnModel().getColumn(2).setMaxWidth(110);
            tabela.getColumnModel().getColumn(3).setMinWidth(110);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(110);
            tabela.getColumnModel().getColumn(3).setMaxWidth(110);
            tabela.getColumnModel().getColumn(5).setMinWidth(160);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(170);
            tabela.getColumnModel().getColumn(5).setMaxWidth(200);
            tabela.getColumnModel().getColumn(6).setMinWidth(110);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(110);
            tabela.getColumnModel().getColumn(6).setMaxWidth(110);
        }

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel6.setPreferredSize(new java.awt.Dimension(893, 30));

        btnImprimir.setText("Imprimir");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(757, Short.MAX_VALUE)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        add(jPanel6, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            def_pesquisa();
            con.addModel(cboTribunal, new Tribunal());
        } catch (Exception e) {
            con.addModel(cboTribunal, new Tribunal());
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> cboTribunal;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDataAte;
    private javax.swing.JLabel lblDataDe;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JPanel pDataDe;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
