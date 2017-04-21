/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.AIStuff;

import anthropologyapplication.Logger.FileLogger;
import anthropologyapplication.FoodHandler;
import java.io.IOException;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author noone
 */
public class AIHandler extends Service {

    int i = 0;
    
    //Randomize AI personality by writing multiple funcs of the same type and picking one specific one?
    private StateExecution ExpandingFunc;
    
    private final AICampObject myCamp;
    private Stack<StateExecution> myFunctionStack = new Stack<>();
    private StateExecution myCurrentFunctionToExecute;
    boolean isAlive = true;
    
    public AIHandler(AICampObject myCampObject)
    {
        this.myCamp = myCampObject;
        createStateInterfaces(); //Create our interfaces?
    }
   
    @Override
    protected Task createTask() {
        Task aTask = new Task<Object>() 
        {
            protected String call()
            {
                while(isAlive) //loops until we set the dead state then ends;
                {
                    if(myCurrentFunctionToExecute == null)
                    {
                        setStateExecution(ExpandingFunc); //Since Expanding Func is our 'root' node of the state tree it automatically gets selected
                    }
                    else {
                        StateExecution aNewState = myCurrentFunctionToExecute.substateCheck();
                        if(aNewState != null)
                        {
                            aNewState.onEnter();
                            setStateExecution(aNewState);
                        }
                        else if(myCurrentFunctionToExecute.isFinished())
                        {
                            stateHasFinished();
                        }
                    }

                }
                return null;
            }
        };
        return aTask;
    }    

    public void startAI()
    {
        this.start();
    }
    
    

//Lock aThreadLock;
    public StateExecution getStateExecution()
    {
        return myCurrentFunctionToExecute;
    }
   
    
    
    private void setStateExecution(StateExecution myNewState)
    {
        if(myCurrentFunctionToExecute != null)
            myFunctionStack.push(myCurrentFunctionToExecute);
        myCurrentFunctionToExecute.onExit();
        myCurrentFunctionToExecute = myNewState;
        myNewState.onEnter();
    }
    
    private void stateHasFinished()
    {
        if(!myFunctionStack.empty())
        {
            myCurrentFunctionToExecute.onFinish();
            myCurrentFunctionToExecute = myFunctionStack.pop();
            if(myCurrentFunctionToExecute != null)
            {
                myCurrentFunctionToExecute.onEnter();
            } else {
                //Error?
            }
        }
    }
    
   

    private void createStateInterfaces() {
        //We use this private function because it means we can be sure that the camp data is set before building anonymous interfaces
        //Additionally we can create a web of interfaces that can be checked by the state machine
        
        
        //////////////////////////////////
        //START Expanding State Definition
        //////////////////////////////////
        ExpandingFunc = new StateExecution()
        {
            AICampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            public void onEnter() {
                writeToCampAILog("Entering State: Expanding");
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            public void Execute() {
                if(super.shouldExecute && !isFinished())
                {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    //Code to execute goes here. This has to be fast though since it's part of the actual application loop. 
                    //If slow, it'll result in the application freezing.
                
                    if(!isFinished())
                    {
                        onExit();  
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            public void onExit() { //This is to tell us the event is still live but not active.
                writeToCampAILog("Exiting State: Expanding");
               
            }

            
            
            

            @Override
            public void onFinish() {
                writeToCampAILog("Finishing State: Expanding");
            }

            @Override
            public StateExecution substateCheck() {
                //These are the substate conditions that are being checked.
                return null;
            }
            
            
        };
    }
    
    private void writeToCampAILog(String aMessage)
    {
        try {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class, aMessage);
            } catch (InterruptedException ex) {
                Logger.getLogger(AIHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AIHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //////////////////////////////////
    //START Expanding State
    //////////////////////////////////
    
    
}
