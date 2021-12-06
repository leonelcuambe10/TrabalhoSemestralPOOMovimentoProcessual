/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;

/**
 *
 * @author LEONEL
 */
public abstract class SuperModel implements Serializable {
    public SuperModel() {
    }
    public abstract String getSrc();
 }
