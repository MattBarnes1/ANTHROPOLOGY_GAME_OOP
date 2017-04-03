/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import JSON.JSON;
import JSON.Value;
import JSON.aObject;
import JSON.array;
import JSON.string;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class RandomEvent
{
        RandomEvent[] aNextRandomEvent;
        ArrayList<RandomEvent> generatingEvents = new ArrayList<RandomEvent>();
        RandomEvent Previous;
        int TimeDelayTimerMS = 0;
        String theEventString;
        String[] theEventChoices;
        SocialValues[] SocialValueForChoices;
                
        public RandomEvent(String localFileName) throws IOException
        {
                File Location = new File(localFileName);
                InputStream newStream  = null;//getResourceAsStream(); //This will be embedded stuff
                JSON myJSON = new JSON(newStream);
                aObject myObject = myJSON.getObject();
                
                Value EventString = myObject.getValueInObject(new string("EventString"));
                Value EventChoices = myObject.getValueInObject(new string("EventChoices"));
                Value EventResults = myObject.getValueInObject(new string("EventResults"));
                Value EventTimeDelay = myObject.getValueInObject(new string("Timedelay"));
                Value nextEvents = myObject.getValueInObject(new string("nextEvents"));
                
                ArrayList<RandomEvent> SubEvents = new ArrayList<>();
                Iterator<Value> myArrayIter = ((array)nextEvents).getIterator();
                while(myArrayIter.hasNext())
                {
                    SubEvents.add(new RandomEvent((aObject)myArrayIter.next()));
                }
                aNextRandomEvent = new RandomEvent[SubEvents.size()];
                SubEvents.toArray(aNextRandomEvent);
        }
        
        private RandomEvent(aObject anObject)
        {
                
                Value EventString = anObject.getValueInObject(new string("EventString"));
                Value EventChoices = anObject.getValueInObject(new string("EventChoices"));
                Value EventResults = anObject.getValueInObject(new string("EventResults"));
                Value EventTimeDelay = anObject.getValueInObject(new string("Timedelay"));
                Value nextEvents = anObject.getValueInObject(new string("nextEvents"));

            ArrayList<RandomEvent> SubEvents = new ArrayList<>();
            Iterator<Value> myArrayIter = ((array)nextEvents).getIterator();
            while(myArrayIter.hasNext())
            {
                SubEvents.add(new RandomEvent((aObject)myArrayIter.next()));
            }
            aNextRandomEvent = new RandomEvent[SubEvents.size()];
            SubEvents.toArray(aNextRandomEvent);
        }
        
        

        public String getTextBody()
        {
            return theEventString;
        }

        public String[] getChoices()
        {
            return  theEventChoices;
        }

        public void selectChoice(int ChoiceSelection, SocietyChoices myPlayersChoices)
        {
            //myPlayersChoices.adjustCohesionByTerms();
        }

        public boolean isFinished(){
             return (TimeDelayTimerMS == 0);
            
        }

    RandomEvent getNextEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void update(GameTime MS) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        
}
