/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AIStuff;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author noone
 */
public class AIHandler extends Service {

    private final AICampObject myCamp;
 
    public AIHandler(AICampObject myCampObject)
    {
        this.myCamp = myCampObject;
    }

    @Override
    protected Task createTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
