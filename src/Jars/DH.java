package Jars;

import java.io.Serializable;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DH implements Serializable {

    Calendar c;
    String data, horas;

    public DH() {
        c = Calendar.getInstance();
    }

    public int getDia() {
        c = Calendar.getInstance();
        return c.get(c.DAY_OF_MONTH);
    }

    public int getDiaSemana() {
        c = Calendar.getInstance();
        return c.get(c.DAY_OF_WEEK);
    }

    public int getDiaMes() {
        c = Calendar.getInstance();
        return c.get(c.DAY_OF_MONTH);
    }

    public int mes(int mesActual, boolean posterior) {
        int inc = -1;
        if (posterior) {
            inc = 1;
        }
        int mes = mesActual + inc;
        if (mes < 0) {
            mes = 11;
        } else if (mes >= 12) {
            mes = 0;
        }
        return mes;
    }

    public int getDiaAno() {
        c = Calendar.getInstance();
        return c.get(c.DAY_OF_YEAR);
    }

    public int get_April() {
        c = Calendar.getInstance();
        return c.get(c.APRIL);
    }

    public int get_Date() {
        c = Calendar.getInstance();
        return c.get(c.DATE);
    }

    public int getMes() {
        c = Calendar.getInstance();
        return ((c.get(c.MONTH) + 1));
    }

    public int getAno() {
        c = Calendar.getInstance();
        return c.get(c.YEAR);
    }

    public int getDiaStr(int d, int m, int a) {
        c = Calendar.getInstance();
        d = 1;
        c.set(a, m, d);
        int ds = c.get(c.DAY_OF_WEEK);
        switch (ds) {
            case Calendar.SUNDAY: {
                return 1;
            }
            case Calendar.MONDAY: {
                return 2;
            }
            case Calendar.TUESDAY: {
                return 3;
            }
            case Calendar.WEDNESDAY: {
                return 4;
            }
            case Calendar.THURSDAY: {
                return 5;
            }
            case Calendar.FRIDAY: {
                return 6;
            }
            case Calendar.SATURDAY: {
                return 7;
            }
        }
        return ds;
    }

    public String _01(int nr) {
        if (nr < 10) {
            return "0" + nr;
        }
        return "" + nr;
    }

    public String getData() {
        c = Calendar.getInstance();
        return "" + (_01(c.get(c.DAY_OF_MONTH)) + "/" + _01((c.get(c.MONTH) + 1)) + "/" + c.get(c.YEAR));
    }

    public String[] getDataVals(String data) {
        return new String[]{data.substring(0, 2), data.substring(3, 5), data.substring(6, 10)};
    }

    public String getDataAndHoras() {
        c = Calendar.getInstance();
        return "" + (c.get(c.DAY_OF_MONTH) + "/" + mesPorExtenco((c.get(c.MONTH) + 1)) + "/" + c.get(c.YEAR) + " - " + getHoras());
    }

    public String getNowData(int addDia) {
        c = Calendar.getInstance();
        return "" + ((c.get(Calendar.DAY_OF_MONTH) + addDia) + "/" + mesPorExtenco((c.get(Calendar.MONTH) + 1)) + "/" + c.get(Calendar.YEAR));
    }

    public String getHoras() {
        c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY), m = c.get(Calendar.MINUTE), s = c.get(Calendar.SECOND);
        String H = "" + h, M = "" + m, S = "" + s;
        if (h < 10) {
            H = "0" + h;
        }
        if (m < 10) {
            M = "0" + m;
        }
        if (s < 10) {
            S = "0" + s;
        }
        return "" + (H + ":" + M + ":" + S);
    }

    @Override
    public String toString() {
        return this.getData() + "-" + this.getHoras();
    }

    public String mesPorExtenco(int mesPorNumero) {
        switch (mesPorNumero) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
        }
        return "";

    }

    public int mesPorNumero(String mesPorExtenco) {
        switch (mesPorExtenco) {
            case "Jan":
                return 1;
            case "Fev":
                return 2;
            case "Mar":
                return 3;
            case "Abr":
                return 4;
            case "Mai":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Ago":
                return 8;
            case "Set":
                return 9;
            case "Out":
                return 10;
            case "Nov":
                return 11;
            case "Dez":
                return 12;
        }
        return -1;

    }

    public int quantDiasDoMes(int m, int a) {
        if (m > 0 && m <= 12) {
            if (m == 4 || m == 6 || m == 9 || m == 11) {
                return 30;
            } else if (m == 2) {
                if (a % 2 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            } else {
                return 31;
            }
        }
        return 28;
    }

    public void mostaHoras(JLabel lblHoras) {
        new Thread() {
            public void run() {
                do {
                    try {
                        sleep(1000);
                        lblHoras.setText(getHoras());
                    } catch (Exception e) {
                    }
                } while (true);
            }
        }.start();
    }

//    _________________________________
//    _________________________________
////    _________________________________
//    Validacoes val = new Validacoes();
    JFrame frmPai = new JFrame();

    public Object aaa(int m, int a) {
        int dActual = getDia();
        int mActual = getMes();
        int aActual = getAno();
        System.out.println("getData: " + getData());
        System.out.println("getNowData: " + getNowData(dActual));
        System.out.println("getDiaAno: " + getDiaAno());
        System.out.println("getDiaSemana: " + getDiaSemana());
        System.out.println("getDiaMes: " + getDiaMes());
        System.out.println("totaDeDias: " + totaDeDias(01, 1, 2018));
        if (a >= 1000 && a <= 3000) {
            if (m > 0 && m <= 12) {

            } else {
                //   val.msg(frmPai, null, "?", "Erro!", "O mes deve estar entre [1 - 12]");
            }
        } else {
            //val.msg(frmPai, null, "?", "Erro!", "Deve inserir um ano entre [1000 - 3000]");
        }
        return null;
    }

    public String diaDaSemana() {
        switch (getDiaSemana()) {
            case 1:
                return "Dom";
            case 2:
                return "Seg";
            case 3:
                return "Ter";
            case 4:
                return "Qua";
            case 5:
                return "Qui";
            case 6:
                return "Sex";
            case 7:
                return "Sab";
        }
        return "Erro";
    }

    public int totaDeDias(int d, int m, int a) {
        int dActual = getDia();
        int mActual = getMes();
        int aActual = getAno();
        int total = 0;
        if (a >= 1000 && a <= 3000) {
            if (m > 0 && m <= 12) {
                int maxDia = quantDiasDoMes(m, a);
                if (d > 0 && d <= maxDia) {
                    if (a > aActual) {
                        for (int i = aActual; i < a; i++) {
                            for (int j = 1; j < 13; j++) {
                                total += quantDiasDoMes(j, i);
                            }
                        }
                    } else {
                        for (int i = a; i < aActual; i++) {
                            for (int j = 1; j < 13; j++) {
                                total += quantDiasDoMes(j, i);
                                System.out.println("" + i + " ... " + quantDiasDoMes(j, i));
                            }
                        }
                    }
                    int d1 = 0, d2 = 0;
                    if (true) {

                    }
                    for (int i = 1; i < m; i++) {
                        total += quantDiasDoMes(i, aActual);
                        System.out.println("" + i + " ... " + quantDiasDoMes(i, aActual));
                    }
                    total += d;
                } else {
                    JOptionPane.showMessageDialog(null, "Dia invalido!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Mes invalido!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ano invalido!");
        }
        return total;
    }

    ////////////////////////////////////////////////////////////
    public boolean isDataMaiorQueHoje(String data) {//Formato: "19/08/2021"; 
        Metodos val = new Metodos();
        int diaDataY = getDia(), mesDataY = getMes(), anoDataY = getAno();
        int diaData = val.toInt(data.substring(0, 2)), mesData = val.toInt(data.substring(3, 5)), anoData = val.toInt(data.substring(6, 10));
        if (anoData >= anoDataY) {
            if (anoData > anoDataY) {
                return true;
            }
            if (mesData >= mesDataY) {
                if (mesData > mesDataY) {
                    return true;
                }
                if (diaData >= diaDataY) {
                    if (diaData > diaDataY) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isData_X_MaiorIgualQue_Y(String dataX, String dataY) {//Formato: "19/08/2021";
        Metodos val = new Metodos();
        int diaDataX = val.toInt(dataX.substring(0, 2)), mesDataX = val.toInt(dataX.substring(3, 5)), anoDataX = val.toInt(dataX.substring(6, 10));
        int diaDataY = val.toInt(dataY.substring(0, 2)), mesDataY = val.toInt(dataY.substring(3, 5)), anoDataY = val.toInt(dataY.substring(6, 10));
        if (diaDataX == diaDataY && mesDataX == mesDataY && anoDataX == anoDataY) {
            return true;
        }
        if (anoDataX >= anoDataY) {
            if (anoDataX > anoDataY) {
                return true;
            }
            if (mesDataX >= mesDataY) {
                if (mesDataX > mesDataY) {
                    return true;
                }
                if (diaDataX >= diaDataY) {
                        return true;
                }
            }
        }
        return false;
    }

    public String add(int nr) {
        if (nr < 10) {
            return "0" + nr;
        }
        return "" + nr;
    }

    public String is(String data) {
        boolean is = isDataMaiorQueHoje(data);
        if (is) {//É maior Não é Maior
            return data + " # É maior que hoje.";
        }
        return data + " # Não é maior que hoje.";
    }

    public void main(String[] args) {
        DH hora = new DH();
        String dataX = "19/12/2020", dataY = "19/07/2021";
        System.out.println(isData_X_MaiorIgualQue_Y(dataX, dataY));
//        for (int i = 2020; i < 2050; i++) {
//            for (int j = 1; j < 13; j++) {
//                int d = 32;
//                if (j == 2) {
//                    d = 29;
//                }
//                for (int k = 1; k < 32; k++) {
//                    String data = add(k) + "/" + add(j) + "/" + add(i);
//                    val.logg(is(data));
//                }
//            }
//        }
//        val.logg(val.e_dataInferiorQueHoje(data));
    }
}
