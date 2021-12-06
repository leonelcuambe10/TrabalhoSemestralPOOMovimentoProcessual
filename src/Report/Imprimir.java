package Report;

import Models.Relatorio;
import java.awt.GridLayout;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Imprimir {

    public JPanel imprimir(ArrayList<Relatorio> lista) {
        JPanel p = new JPanel();
        if (lista.size() > 0) {
            String caminho = "/Report/relatorio.jasper";
            InputStream relJasper = getClass().getResourceAsStream(caminho);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
            Map parametros = new HashMap();
            try {
                JasperPrint impressao = JasperFillManager.fillReport(relJasper, parametros, ds);
                JasperViewer viewer = new JasperViewer(impressao, true);
                viewer.setTitle("Gestão de Stock - Relatório de produtos");
                viewer.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
                p = new JPanel(new GridLayout(1, 1));
                p.add(viewer.getContentPane());
            } catch (Exception e) {
                System.out.println("Erro ao imprimir.\n" + e);
            }
        }
        return p;
    }
}
