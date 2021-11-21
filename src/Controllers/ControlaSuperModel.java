/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.SuperModel;
import Models.Tribunal_Distrital;

/**
 *
 * @author LEONEL
 */
public class ControlaSuperModel extends ControlaGenerico<SuperModel> {

    SuperModel model;

    public ControlaSuperModel(SuperModel model) {
        super(model);
        this.model = model;
    }

    public void print(Object msg) {
        System.out.println(msg);
    }
}
