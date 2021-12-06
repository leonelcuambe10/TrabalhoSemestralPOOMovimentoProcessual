/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jars;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JDialog;
import static javax.swing.JOptionPane.getRootFrame;

/**
 *
 * @author LEONEL
 */
public class ShowChild {

    Frame self;
    private JDialog dialog;
    Metodos val = new Metodos();

    public ShowChild(Frame self, JComponent compToShow, JComponent compToClick, String title) {
        this.self = self;
        compToClick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int x = evt.getXOnScreen() - evt.getX();
                int y = evt.getYOnScreen() - evt.getY() + compToClick.getHeight();
                show(compToShow, title, x, y);
            }
        });
//        val.onClick_Sair(frm);
    }

    public ShowChild(Frame self, JComponent compToShow, String title) {
        this.self = self;
        show(compToShow, title, 0, 0);
    }

    private void show(JComponent compToShow, String title, int x, int y) throws HeadlessException {
        Window window = ShowChild.getWindowForComponent(self);
        dialog = new JDialog((Frame) window, title, true);
        if (compToShow instanceof pCalendar) {
            ((pCalendar) compToShow).dialog = dialog;
        }
        dialog.setLayout(new GridLayout(1, 1, 1, 1));
        dialog.add(compToShow);
        dialog.pack();
        if (title.equals("Relatorio")) {
            dialog.setSize(980, 580);
            dialog.setLocationRelativeTo(self);
        } else {
            dialog.setLocation(x, y);
        }
        dialog.show();
        window.pack();
    }

    public JDialog getDialog() {
        return dialog;
    }

    public void dispose() {
        dialog.dispose();
    }

    private static Window getWindowForComponent(Component self)
            throws HeadlessException {
        if (self == null) {
            return getRootFrame();
        }
        if (self instanceof Frame || self instanceof Dialog) {
            return (Window) self;
        }
        return ShowChild.getWindowForComponent(self.getParent());
    }

}
