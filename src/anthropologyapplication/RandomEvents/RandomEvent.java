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
import anthropologyapplication.JSON.True;
import anthropologyapplication.JSON.Number;
import anthropologyapplication.Logger.FileLogger;
import anthropologyapplication.SocialValues;
import anthropologyapplication.SocietyChoices;
import anthropologyapplication.Timer;
//import anthropologyapplication.JSON.String;
import java.io.File;
import java.io.FileInputStream;
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
        private RandomEvent[] aNextRandomEvent;
        private ArrayList<RandomEvent> generatingEvents = new ArrayList<RandomEvent>();
        private RandomEvent Previous;
        private Timer DelayTimer;
        private String theEventString;
        private String[] theEventChoices;
        private String InternalName;
        private SocialValues[] SocialValueForChoices;
        private boolean fireOnce = false;
        public RandomEvent(String localFileName) throws IOException, OperationNotSupportedException
        {
                InternalName = "Root";
                File Location = new File(localFileName);
                InputStream newStream  = new FileInputStream(Location);
                JSON myJSON = new JSON(newStream);
                Array myObject = myJSON.getArray();                
                ArrayList<RandomEvent> Events = new ArrayList<>();
                Iterator<Value> myArrayIter = (myObject).getIterator();
                while(myArrayIter.hasNext())
                {
                    Events.add(new RandomEvent((aObject)myArrayIter.next()));
                }
                aNextRandomEvent = new RandomEvent[Events.size()];
                Events.toArray(aNextRandomEvent);
                isFinishedEvent = true; //so it ignores the very first event which is the start of the tree;
                FileLogger.writeToLog(FileLogger.LOGTO.RANDOM_EVENTS_CREATOR, "Finished Generating Random Events: ", getRandomEventTree(0));
        }
        
        private RandomEvent(aObject anObject)
        { 
            Value EventString = anObject.getValueInObject(new anthropologyapplication.JSON.String("EventScenarioString"));
            theEventString = EventString.toString();
            
            String InternalName = anObject.getValueInObject(new anthropologyapplication.JSON.String("InternalName")).toString();
            
            Array EventChoices = (Array)anObject.getValueInObject(new anthropologyapplication.JSON.String("EventChoices"));
            
            Array eventSocialChoiceValues = (Array)anObject.getValueInObject(new anthropologyapplication.JSON.String("EventChoicesSocialValues"));
            
            Array EventResults = (Array)anObject.getValueInObject(new anthropologyapplication.JSON.String("EventResults"));
            
            Number EventTimeDelay = (Number)anObject.getValueInObject(new anthropologyapplication.JSON.String("EventTimeDelayMS"));
           
            Value FireOnce = anObject.getValueInObject(new anthropologyapplication.JSON.String("FireOnce"));
             if(FireOnce.getClass() == True.class)
             {
                fireOnce = true;
             } else {
                fireOnce = false;
             }
            
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

        boolean isFinishedEvent = false;
        public boolean isFinished(){
           return isFinishedEvent;
        }

        RandomEvent getNextEvent() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    void update(GameTime MS) {
        if(!isFinished())
        {
            DelayTimer.subtract(MS.getElapsedTime());
            isFinishedEvent = (DelayTimer.EqualTo(Timer.Zero));
        }
    }

    private String getRandomEventTree(int par) {
        String retVal = createTabs(par);
        for(RandomEvent aVal : aNextRandomEvent)
        {
            retVal+= aVal.getRandomEventTree(par + 1);
        }
        return retVal;
    }

    private String createTabs(int par) {
        String retString = "";
        for(int i = 0 ; i < par; i++)
        {
            retString += "\t";
        }
        return retString;
    }

        
}
