/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.RandomEvents;

import anthropologyapplication.GameTime;
import anthropologyapplication.JSON.JSON;
import anthropologyapplication.JSON.Value;
import anthropologyapplication.JSON.aObject;
import anthropologyapplication.JSON.Array;
import anthropologyapplication.SocialValues;
import anthropologyapplication.SocietyChoices;
//import anthropologyapplication.JSON.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;
import javax.naming.OperationNotSupportedException;
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
                
        public RandomEvent(String localFileName) throws IOException, OperationNotSupportedException
        {
                File Location = new File(localFileName);
                InputStream newStream  = null;//getResourceAsStream(); //This will be embedded stuff
                JSON myJSON = new JSON(newStream);
                aObject myObject = myJSON.getObject();
                
                Value EventString = myObject.getValueInObject(new anthropologyapplication.JSON.String("EventString"));
                Value EventChoices = myObject.getValueInObject(new anthropologyapplication.JSON.String("EventChoices"));
                Value EventResults = myObject.getValueInObject(new anthropologyapplication.JSON.String("EventResults"));
                Value EventTimeDelay = myObject.getValueInObject(new anthropologyapplication.JSON.String("Timedelay"));
                Value nextEvents = myObject.getValueInObject(new anthropologyapplication.JSON.String("nextEvents"));
                
                ArrayList<RandomEvent> SubEvents = new ArrayList<>();
                Iterator<Value> myArrayIter = ((Array)nextEvents).getIterator();
                while(myArrayIter.hasNext())
                {
                    SubEvents.add(new RandomEvent((aObject)myArrayIter.next()));
                }
                aNextRandomEvent = new RandomEvent[SubEvents.size()];
                SubEvents.toArray(aNextRandomEvent);
        }
        
        private RandomEvent(aObject anObject)
        {
                
                Value EventString = anObject.getValueInObject(new anthropologyapplication.JSON.String("EventString"));
                Value EventChoices = anObject.getValueInObject(new anthropologyapplication.JSON.String("EventChoices"));
                Value EventResults = anObject.getValueInObject(new anthropologyapplication.JSON.String("EventResults"));
                Value EventTimeDelay = anObject.getValueInObject(new anthropologyapplication.JSON.String("Timedelay"));
                Value nextEvents = anObject.getValueInObject(new anthropologyapplication.JSON.String("nextEvents"));

            ArrayList<RandomEvent> SubEvents = new ArrayList<>();
            Iterator<Value> myArrayIter = ((Array)nextEvents).getIterator();
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
