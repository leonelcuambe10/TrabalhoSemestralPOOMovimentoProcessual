/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jars;
 
import java.awt.Color; 
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog; 
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author LEONEL
 */
public class pCalendar extends javax.swing.JPanel {

    DH dh = new DH();
    int dia = dh.getDia(), mes = dh.getMes(), ano = dh.getAno();
    Metodos met = new Metodos();
    JTextField txtToPutInfo =new JTextField();
    JLabel lblToPutInfo=new JLabel();
    public ShowChild show;
    public JDialog dialog;
    int spnAnt = 0;

    public ArrayList<JButton> lstBtn = new ArrayList<>();

    /**
     * Creates new form pCalendar
     */

    public pCalendar(JTextField txtToPutInfo) {
        initComponents();
        this.txtToPutInfo = txtToPutInfo;
        carregarCBOs();
        spinnerM.setValue(cboM.getSelectedIndex());
        spnAnt = (int) spinnerM.getValue(); 
    }

    public pCalendar(JLabel lblToPutInfo) {
        initComponents();
        this.lblToPutInfo = lblToPutInfo;
        carregarCBOs();
        spinnerM.setValue(cboM.getSelectedIndex());
        spnAnt = (int) spinnerM.getValue(); 
    }
 
    void carregarCBOs() {
        for (int i = 1; i <= 12; i++) {
            cboM.addItem(dh.mesPorExtenco(i));
        }
        met.addNumerosNaCbo(cboA, 1980, dh.getAno() + 40);
        met.setTxt(cboA, "" + ano);
        met.setTxt(cboM, dh.mesPorExtenco(mes - 1));
        criaBtns();
        evts();
    }

    void msg(String msg) {
        met.msg(null, msg);
    }

    ArrayList<JButton> btns = new ArrayList<>();

    private void criaBtns() {
        mes = dh.mesPorNumero(met.getTxt(cboM));
        poe(mes - 1);
        poe(mes);
    }

    private javax.swing.JLabel lbl(String txt) {
        javax.swing.JLabel l = new javax.swing.JLabel(txt);
        l.setFont(new java.awt.Font("Times New Roman", 0, 14));
        l.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        return l;
    }

    private String _01(int i) {
        if (i < 10) {
            return ("0" + i);
        }
        return "" + i;
    }

    int quantDiasDoMesAnterior(int mesAct, int anoAct) {
        int mes = mesAct - 1, ano = anoAct;
        if (mesAct == 1) {
            mes = 12;
            ano--;
        }
        if (ano == -1) {
            ano = dh.getAno();
        }
        int qAnt = dh.quantDiasDoMes(mes, ano);
        return qAnt;
    }

    void poe(int mes) {
        pBtns.removeAll();
        lstBtn = new ArrayList<>();
        ano = met.toInt(met.getTxt(cboA));
        if (ano == 0) {
            ano = dh.getAno();
            met.setTxt(cboA, "" + ano);
        }
        pBtns.setLayout(new GridLayout(6, 7, 1, 1));
        int qd = dh.quantDiasDoMes(mes, ano), qAnt = quantDiasDoMesAnterior(mes, ano);
        int inicio = dh.getDiaStr(dia, mes, ano);
        String data = mes_01(dia) + "/" + mes_01(mes) + "/" + ano;
        pBtns.removeAll();
        inicio = dh.getDiaStr(dia, mes - 1, ano);
        int k = 0, j = 1;
//        met.logg("inicio - 1: " + (inicio - 1));
        for (int i = 0; i < inicio - 1; i++) {
            pBtns.add(lbl("" + (qAnt - inicio + i + 2)));
            k++;
        }
        k += 1;
//        met.logg("k: " + k + ", 41-k = " + (41 - k) + "\n");
        for (int i = 1; k < 43; i++) {
            if (i > 0 && i <= qd) {
                JButton btn = on_click_dia(new javax.swing.JButton(), i);
                if (k == 1 || k == 8 || k == 15 || k == 22 || k == 29 || k == 36) {
                    btn.setOpaque(true);
                    btn.setBackground(new Color(255, 132, 90));
                }
                lstBtn.add(btn);
                pBtns.add(btn);
            } else {
                pBtns.add(lbl(""));// + j
                j++;
            }
            k++;
        }
        lb.setText("" + data);
    }

    String mes_01(int m) {
        if (m < 10) {
            return "0" + m;
        }
        return "" + m;
    }

    private JButton on_click_dia(JButton btn, int i) {
        // = new javax.swing.JButton();
        String d = "" + i;
        if (i < 10) {
            d = "0" + i;
        }
        btn.setName("btn_" + d);
        btn.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btn.setText("" + d);
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dia = met.toInt(btn.getText());
                mes = dh.mesPorNumero(met.getTxt(cboM));
                ano = met.toInt(met.getTxt(cboA));
                if (ano == 0) {
                    ano = dh.getAno();
                    met.setTxt(cboA, "" + ano);
                }
                String data = mes_01(dia) + "/" + mes_01(mes) + "/" + ano;
                met.setTxt(lb, data);
                met.setTxt(txtToPutInfo, data);
                met.setTxt(lblToPutInfo, data);
                if (show != null) {
                    show.dispose();
                } 
                if (dialog != null) {
                    dialog.dispose();
                }
            }
        });
        return btn;
    }

    void evts() {
        cboA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criaBtns();
            }
        });
        cboM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criaBtns();
            }
        });
        cboA.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                int swm = cboA.getSelectedIndex() - evt.getWheelRotation(), all = cboA.getItemCount();
                if (swm < 0) {
                    swm = all - 1;
                } else if (swm >= all) {
                    swm = 0;
                }
                cboA.setSelectedIndex(swm);
            }
        });
        cboM.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                int wheel = evt.getWheelRotation();//cima = -1, baixo = 1
                spinner(wheel);
            }
        });
        spinnerM.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                int wheel = evt.getWheelRotation();//cima = -1, baixo = 1
                spinner(wheel);
            }
        });

        spinnerM.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                int spn = (int) spinnerM.getValue();
                int w = -1;
                if (spn < spnAnt) {
                    w = 1;
                }
                spnAnt = spn;
                spinner(w);
            }
        });
    }

    void spinner(int n) {
        int swm = cboM.getSelectedIndex() - n;
        if (swm < 0) {
            swm = 11;
        } else if (swm >= 12) {
            swm = 0;
        }
//        met.logg("n: " + n + ", swm: " + swm);
        cboM.setSelectedIndex(swm);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pFundoGeral = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        cboA = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        spinnerM = new javax.swing.JSpinner();
        cboM = new javax.swing.JComboBox<>();
        lb = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pBtns = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout(2, 2));

        pFundoGeral.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setPreferredSize(new java.awt.Dimension(354, 50));
        jPanel2.setLayout(new java.awt.GridLayout(2, 0, 0, 2));

        jPanel4.setLayout(new java.awt.BorderLayout(2, 0));

        jPanel6.setPreferredSize(new java.awt.Dimension(70, 29));
        jPanel6.setLayout(new java.awt.BorderLayout());

        cboA.setEditable(true);
        cboA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAActionPerformed(evt);
            }
        });
        jPanel6.add(cboA, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6, java.awt.BorderLayout.LINE_END);

        jPanel7.setLayout(new java.awt.BorderLayout(2, 0));

        jPanel8.setPreferredSize(new java.awt.Dimension(90, 29));
        jPanel8.setLayout(new java.awt.BorderLayout());

        spinnerM.setPreferredSize(new java.awt.Dimension(20, 20));
        spinnerM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                spinnerMMousePressed(evt);
            }
        });
        jPanel8.add(spinnerM, java.awt.BorderLayout.LINE_END);

        jPanel8.add(cboM, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel8, java.awt.BorderLayout.LINE_END);

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("dd/mm/aaa");
        jPanel7.add(lb, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridLayout(1, 7, 2, 2));

        jLabel7.setBackground(new java.awt.Color(153, 153, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Dom");
        jLabel7.setOpaque(true);
        jPanel5.add(jLabel7);

        jLabel1.setBackground(new java.awt.Color(153, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seg");
        jLabel1.setOpaque(true);
        jPanel5.add(jLabel1);

        jLabel2.setBackground(new java.awt.Color(153, 153, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ter");
        jLabel2.setOpaque(true);
        jPanel5.add(jLabel2);

        jLabel3.setBackground(new java.awt.Color(153, 153, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Qua");
        jLabel3.setOpaque(true);
        jPanel5.add(jLabel3);

        jLabel4.setBackground(new java.awt.Color(153, 153, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Qui");
        jLabel4.setOpaque(true);
        jPanel5.add(jLabel4);

        jLabel5.setBackground(new java.awt.Color(153, 153, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Sex");
        jLabel5.setOpaque(true);
        jPanel5.add(jLabel5);

        jLabel6.setBackground(new java.awt.Color(153, 153, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Sab");
        jLabel6.setOpaque(true);
        jPanel5.add(jLabel6);

        jPanel2.add(jPanel5);

        pFundoGeral.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        pBtns.setLayout(new java.awt.GridLayout(6, 7, 2, 2));
        pFundoGeral.add(pBtns, java.awt.BorderLayout.CENTER);

        add(pFundoGeral, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void cboAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboAActionPerformed

    private void spinnerMMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spinnerMMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_spinnerMMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboA;
    private javax.swing.JComboBox<String> cboM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lb;
    private javax.swing.JPanel pBtns;
    private javax.swing.JPanel pFundoGeral;
    private javax.swing.JSpinner spinnerM;
    // End of variables declaration//GEN-END:variables

}
