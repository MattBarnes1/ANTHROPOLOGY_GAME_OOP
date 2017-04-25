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
import javax.naming.OperationNotSupportedException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class RandomEventHandler implements Runnable
	{
		SocietyChoices thePlayersChoices;
		RandomEvent RandomEventTree;
		RandomEvent CurrentlySelectedNode;
                ArrayList<RandomEvent> UnorderedEvents = new ArrayList<RandomEvent>();
                String FileName;
		public RandomEventHandler(String FileName) throws IOException
		{
                    this.FileName = FileName;
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
    public void run() {
        try {
            RandomEventTree = new RandomEvent(FileName);
        } catch (IOException ex) {
            Logger.getLogger(RandomEventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }           catch (OperationNotSupportedException ex) {
                        Logger.getLogger(RandomEventHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }
}
