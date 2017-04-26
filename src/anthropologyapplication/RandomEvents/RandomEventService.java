/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.RandomEvents;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Duke
 */
class RandomEventService extends Service {

    
    
    @Override
    protected Task createTask() {  
        Task retTask = new Task<String>()
        {
            @Override
            protected String call() throws Exception {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               return null;
            }

        };
        return retTask;
    }
    
}
