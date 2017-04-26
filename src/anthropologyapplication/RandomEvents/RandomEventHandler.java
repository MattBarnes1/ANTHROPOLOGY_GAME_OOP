/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.RandomEvents;

import anthropologyapplication.GameTime;
import anthropologyapplication.SocietyChoices;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.naming.OperationNotSupportedException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class RandomEventHandler extends Service
{
        SocietyChoices thePlayersChoices;
        RandomEvent RandomEventTree;
        RandomEvent CurrentlySelectedNode;
        ArrayList<RandomEvent> UnorderedEvents = new ArrayList<RandomEvent>();
        String FileName;
        public RandomEventHandler(String FileName) throws IOException, OperationNotSupportedException
        {
            this.FileName = FileName;
            RandomEventTree = new RandomEvent(FileName); //TODO: put this in a Service at start, this is just for debug.
            CurrentlySelectedNode = RandomEventTree;
        }
        
        public boolean isFinished()
        {
            return !this.isRunning();
        }
        
        public boolean hadError()
        {
            return(this.getException() != null);
        }
        
        
        public void update(GameTime MS)
        {
            CurrentlySelectedNode.update(MS);
        }

        public boolean isEventReady()
        {
            return CurrentlySelectedNode.isFinished();
        }

        public RandomEvent GetNextRandomEvent()
        {
            CurrentlySelectedNode = CurrentlySelectedNode.getNextEvent();
            return CurrentlySelectedNode;
        }

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
