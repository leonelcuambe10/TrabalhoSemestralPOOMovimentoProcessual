/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.ControlaSuperModel;
import Models.Tribunal_Distrital;

/**
 *
 * @author LEONEL
 */
public class NewClass {

    public static void main(String[] args) {
        ControlaSuperModel con = new ControlaSuperModel(new Tribunal_Distrital());

        //con.create(new Tribunal_Distrital("T002", "Mahotas", "S2"));
//        con.delete(con.search(2));
        con.print(((Tribunal_Distrital)con.search(1)).getNome());
    }
}
