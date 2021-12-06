/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jars;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;
import static jdk.nashorn.internal.objects.NativeString.trim;

/**
 *
 * @author LEONEL
 */
public class Metodos {

    public int random_0_1000(int min, int max) {
        int n = 0;
        do {
            double a = (double) (Math.random()) * 1000;
            n = (int) a;
        } while (min > n || n > max);
        return n;
    }
    
    public String _add_digitos(int valor) {
        String str_nr = "" + valor;
        while (str_nr.length() < 5) {
            str_nr = "0" + str_nr;
        }
        return str_nr;
    }
    
    public String _add_digitos(int valor,int maxLen) {
        String str_nr = "" + valor;
        while (str_nr.length() < maxLen) {
            str_nr = "0" + str_nr;
        }
        return str_nr;
    }
    public void print(Object msg) {
        System.out.println(msg);
    }

    public void msg(JComponent parent, Object msg) {
        JOptionPane.showMessageDialog(parent, msg);
    }

    public int abs(int valor) {
        if (valor < 0) {
            return valor * -1;
        }
        return valor;
    }

    public boolean confirm(JComponent parent, String msg) {
        if (JOptionPane.showConfirmDialog(parent, msg, "Confirme", JOptionPane.YES_NO_OPTION) == 0) {
            return true;
        }
        return false;
    }

    public void configTable(JTable tabela, String[] titulos, int[][] colunas) {
        tabela.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{}, titulos) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        int count = tabela.getColumnModel().getColumnCount();
        int column = 0;
        int[] wvals = new int[]{};
        if (count > 0 && column < count) {
            try {
                for (int[] col : colunas) {// = new int[][]{{10,30,70}};
                    wvals = col;
                    tabela.getColumnModel().getColumn(column).setResizable(true);
                    tabela.getColumnModel().getColumn(column).setMinWidth(wvals[0]);//minWidth
                    tabela.getColumnModel().getColumn(column).setPreferredWidth(wvals[1]);//preferredWidth
                    tabela.getColumnModel().getColumn(column).setMaxWidth(wvals[2]);//maxWidth
                    column++;
                }
            } catch (Exception e) {
                print("Posicao nao encotrada: " + wvals + "\n" + e);
            }
        }
    }

    /**
     * @param pressedComp = componente(que tiver foco) a ser pressionado
     * @param nextComp = componente que recebera foco
     */
    public void onEnter(JComponent pressedComp, JComponent nextComp) {
        pressedComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nextComp.requestFocus();
            }
        });
    }

    public String trimValue(String str) {
        return trim(str);
    }

    public int len(String str) {
        return str.length();
    }

    public ArrayList<String> getCharsOf(String str) {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < len(str); i++) {
            ids.add("" + str.charAt(i));
        }
        return ids;
    }

    public ArrayList<String> getPalavrasOf(String string, String separadores) {
        ArrayList<String> ids = new ArrayList<>();
        String str = "";
        string += separadores.charAt(0);
        for (String s : getCharsOf(string)) {
            if (separadores.contains(s)) {
                str = trimValue(str);
                if (str.length() == 0) {
                    str = " ";
                }
                ids.add(str);
                str = "";
            } else {
                str += s;
            }
        }
        for (int i = 0; i < 10; i++) {
            ids.add("");
        }
        return ids;
    }

    public String getClassName(Object classe) {//getObjClass(model)
        ArrayList<String> aux_path = getPalavrasOf("" + classe.getClass(), ".|-;");
        return aux_path.get(aux_path.size() - 1);
    }

    public boolean isNumeric(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int toInt(Object ob) {
        return Integer.parseInt(getVal(ob));
    }

    public double toDouble(Object ob) {
        return Double.parseDouble(getVal(ob));
    }

    public String getVal(Object ob) {
        String res = "0", txt = "";
        if (ob instanceof JComponent) {
            txt = getTxt((JComponent) ob);
        } else if (ob instanceof String) {
            txt = (String) ob;
        }
        if (txt.length() > 0) {
            res = txt;
        }
        if (!isNumeric(res)) {
            res = "0";
        }
        return res;
    }

    public void setTxt(JComponent comp, String txt) {
        if (comp instanceof JLabel) {
            ((JLabel) comp).setText(txt);
        } else if (comp instanceof JTextComponent) {
            ((JTextComponent) comp).setText(txt);
        } else if (comp instanceof JComboBox) {
            if (txt.length() == 0) {
                ((JComboBox) comp).setSelectedIndex(0);
            } else {
                ((JComboBox) comp).setSelectedItem(txt);
            }
        }
    }

    public String getTxt(JComponent comp) {
        String txt = "";
        if (comp instanceof JLabel) {
            txt = ((JLabel) comp).getText();
        } else if (comp instanceof JTextComponent) {
            txt = ((JTextComponent) comp).getText();
            if (comp instanceof JPasswordField) {
                txt = new String(((JPasswordField) comp).getPassword());
            }
        } else if (comp instanceof JComboBox) {
            txt = ((JComboBox) comp).getSelectedItem().toString();
        }
        return txt;
    }

    public void addNumerosNaCbo(JComboBox cbo, int min, int max) {
        cbo.removeAllItems();;
        if (min > max) {
            max += min - max;
        }
        for (int i = min; i < (max + 1); i++) {
            cbo.addItem("" + i);
        }
    }

    public boolean isValid(JTextField txt, int minLen, int maxLen, String errorMsg) {
        String str = getTxt(txt);
        if (len(str) >= minLen && len(str) <= maxLen) {
            return true;
        }
        msg(txt, errorMsg + "\nTamanho aceite\nMin: " + minLen + ",   Max: " + maxLen);
        return false;
    }

    public boolean x_contain_y(String x, String str) {
        if (trimValue(x).length() > 0 && trimValue(str).length() > 0) {
            str = upcaseValue(str);
            x = upcaseValue(x);
            if (x.contains(str) || x.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public String upcaseValue(String str) {
        return toUpperCase(str);
    }
}
