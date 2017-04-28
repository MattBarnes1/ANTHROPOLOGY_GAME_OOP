/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.AIStuff;

import anthropologyapplication.Buildings.Building;
import anthropologyapplication.Buildings.Field;
import anthropologyapplication.Logger.FileLogger;
import anthropologyapplication.FoodHandler;
import anthropologyapplication.TribalCampObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    private StateExecution InitialState;
    private StateExecution HungryState;
    private StateExecution BuildHomesState;
    private StateExecution GenWarriorsState;

    private final TribalCampObject myCamp;
    private Stack<StateExecution> myFunctionStack = new Stack<>();
    private StateExecution myCurrentFunctionToExecute;
    boolean isAlive = true;

  
    public AIHandler(AICampObject myCampObject) {
        this.myCamp = myCampObject;
        createStateInterfaces(); //Create our interfaces?
    }

    @Override
    protected Task createTask() {
        Task aTask = new Task<Object>() {
            protected String call() {
                while (isAlive) //loops until we set the dead state then ends;
                {
                    if (myCurrentFunctionToExecute == null) {
                        setStateExecution(InitialState); //Since Expanding Func is our 'root' node of the state tree it automatically gets selected
                    } else {
                        StateExecution aNewState = myCurrentFunctionToExecute.substateCheck();
                        if (aNewState != null) {
                            aNewState.onEnter();
                            setStateExecution(aNewState);
                        } else if (myCurrentFunctionToExecute.isFinished()) {
                            stateHasFinished();
                        }
                    }

                }
                return null;
            }
        };
        return aTask;
    }

    public void startAI() {
        this.start();
    }

//Lock aThreadLock;
    public StateExecution getStateExecution() {
        return myCurrentFunctionToExecute;
    }

    private void setStateExecution(StateExecution myNewState) {
        if (myCurrentFunctionToExecute != null) {
            myFunctionStack.push(myCurrentFunctionToExecute);
        }
        myCurrentFunctionToExecute.onExit();
        myCurrentFunctionToExecute = myNewState;
        myNewState.onEnter();
    }

    private void stateHasFinished() {
        if (!myFunctionStack.empty()) {
            myCurrentFunctionToExecute.onFinish();
            myCurrentFunctionToExecute = myFunctionStack.pop();
            if (myCurrentFunctionToExecute != null) {
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
        //START Initial State Definition
        //////////////////////////////////
        InitialState = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand
            
            //Reset builders so we aren't increasing consumption for no reason
            private void buildingClear()
            {
                int numBuilders = myHandle.getBuildingHandler().getBuildersAmount();
                        
                if(myHandle.getBuildingHandler().countBuildingsBeingBuilt() == 0 && numBuilders > 0)
                {
                    //yHandle.addFreeCitizens(numBuilders);
                }
            }
            
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Initial");
                
                
                
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            public void Execute() {
                if (super.shouldExecute && !isFinished()) {
                    
                    buildingClear();

                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            public float estimateConsumption()
            {
               int Consumption = 0;
               Consumption += (myCamp.getPopulationHandler().getWarriorsFoodConsumption()*myCamp.getWarriorHandler().getWarriorsAmount());
               Consumption += (myCamp.getFreeCitizens() * myCamp.getPopulationHandler().getFreeCitizensConsumption());
               Consumption += (myCamp.getBuildingHandler().getBuildersAmount() * myCamp.getPopulationHandler().getBuildersFoodConsumption());
               Consumption += (myCamp.getProductionHandler().getProducersAmount() * myCamp.getPopulationHandler().getProducersFoodConsumption());
               Consumption += (myCamp.getFoodHandler().getFarmersAmount() * myCamp.getPopulationHandler().getFarmersFoodConsumption());
               return Consumption;
            }
            
            public float estimateProduction()
            {
                int Production = 0;
                ArrayList<Building> aBuildingHandler = myCamp.getBuildingHandler().getAllBuiltBuildingsByType(Field.class);
                int Check = myCamp.getFoodHandler().getFarmersAmount();
                Iterator<Building> BuildingIterator = aBuildingHandler.iterator();
                int RequiredFarmers = 0;
                float maxFoodProduced = 0;
                float maxGhostFoodProduced = 0;
                float TotalFoodProducedPerDay = 0;
                while(BuildingIterator.hasNext())
                {
                         Building aBuilding = BuildingIterator.next();
                         RequiredFarmers += (((Field)aBuilding).getRequiredNumberOfFarmers());
                         maxFoodProduced += (((Field)aBuilding).getYield());
                }
                aBuildingHandler = myCamp.getBuildingHandler().getBuildingsCurrentlyBeingBuiltByType(Field.class);
                BuildingIterator = aBuildingHandler.iterator();
                while(BuildingIterator.hasNext())
                {
                         Building aBuilding = BuildingIterator.next();
                         RequiredFarmers += (((Field)aBuilding).getRequiredNumberOfFarmers());
                         maxGhostFoodProduced += (((Field)aBuilding).getYield());
                }
                
                if(RequiredFarmers != 0)
                {
                    if(((float)Check/(float)RequiredFarmers) > 1)
                    {
                        TotalFoodProducedPerDay = ((float)Check/(float)RequiredFarmers)*(maxFoodProduced);
                        TotalFoodProducedPerDay += maxGhostFoodProduced;
                    } else {
                        
                    }
                }
                
                return TotalFoodProducedPerDay;
            }
            
            private boolean isHungry()
            {
               return (estimateConsumption() > estimateProduction());
            }

            private boolean isNuffWarriors()
            {
                //Things will happen here

                return true;
            }

            private boolean isNuffHomes()
            {
                //Things will happen here

                return true;
            }
            
            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Initial");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Initial");
            }

            @Override
            public StateExecution substateCheck() {

                //These are the substate conditions that are being checked.
                
                 //(population - population%2)/2 = number of homes needed to increase population, so this is ideal 
                if(isHungry() == true)
                {
                    return HungryState;
                }
                else if (!isNuffHomes() == true)
                {
                    setStateExecution(BuildHomesState);
                }
                else if (!isNuffWarriors() == true)
                {
                    setStateExecution(GenWarriorsState);
                }
                       
                
                //There will also be an if statement here checking if we need to switch to PrepareRaid
                
                return null;
            }
        //////////////////////////////////
        //End Initial State
        //////////////////////////////////
        };
        
        //////////////////////////////////
        //START Hungry State Definition
        //////////////////////////////////
        HungryState = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            
            private boolean checkPsuedoBool()
            {
                return true;
            }
            
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Fixing Hungry");
                //float myFoodAmountProducedPerDay = myCamp.getFoodHandler().getFoodProducedPerDay();
                //float myFoodConsumptionPerDay = myCamp.
                
                //Possibly psudeo check AI here
                
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            public void Execute() {
                if (super.shouldExecute && !isFinished()) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    //Code to execute goes here. This has to be fast though since it's part of the actual application loop. 
                    //If slow, it'll result in the application freezing.
                    
                    if(checkPsuedoBool())
                    {
                        //There is AI code that was passed to it and needs to be interpreted
                        //If there is no AI threading occuring, will return false
                        
                        //DoBuildingThings()
                            //Will execute CanBuildFields()
                    }
                    else
                       //modify people function
                            //-1 unable to do so for not enough people
                            // 1 successful
                            // 0 means things?
                    
                    //DoBuildingThings()
                    
                    
   

                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Fixing Hungry");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Fixing Hungry");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                
                //Possibly PrepareRaid will be substate
                return null;
            }
        //////////////////////////////////
        //End Hungry State
        //////////////////////////////////
        };
        
        /////////////////////////////////////
        //START GenWarriors State Definition
        ////////////////////////////////////
        GenWarriorsState = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Generating Warriors");
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            public void Execute() {
                if (super.shouldExecute && !isFinished()) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    //Code to execute goes here. This has to be fast though since it's part of the actual application loop. 
                    //If slow, it'll result in the application freezing.
                    
                    //THINGS TO DO/CHECK FOR:
                    //Produce Warriors
                    //Actually Raid

                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Generating Warriors");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Generating Warriors");
            }

            @Override
            public StateExecution substateCheck() {

                //These are the substate conditions that are being checked.
                
                //Not too sure if PrepareRaid will have a substate
                return null;
            }
        //////////////////////////////////
        //End GenWarriors State
        //////////////////////////////////
        };
        
        //////////////////////////////////
        //START BuildHomes State Definition
        //////////////////////////////////
        BuildHomesState = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Building Homes");
                //float myFoodAmountProducedPerDay = myCamp.getFoodHandler().getFoodProducedPerDay();
                //float myFoodConsumptionPerDay = myCamp.
                
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            public void Execute() {
                if (super.shouldExecute && !isFinished()) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    //Code to execute goes here. This has to be fast though since it's part of the actual application loop. 
                    //If slow, it'll result in the application freezing.
                    

                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Building Homes");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Building Homes");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                
                return null;
            }
        //////////////////////////////////
        //End BuildHomes State
        //////////////////////////////////
        };
        
        
    }


}
