/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Raid;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Duke
 */
public class RaidEntityAI extends Service{
    RaidEntityObject myObject;
    RaidEntityAI(RaidEntityObject aThis) {
        myObject = aThis;
    }

    @Override
    protected Task createTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
