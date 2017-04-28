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
    private StateExecution ExpandingFunc;
    private StateExecution StarvingFunc;
    private StateExecution PrepareRaidFunc;

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
                        setStateExecution(ExpandingFunc); //Since Expanding Func is our 'root' node of the state tree it automatically gets selected
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
        //START Expanding State Definition
        //////////////////////////////////
        ExpandingFunc = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand
            
            
            int FieldNumber = 0;
            int GranaryNumber = 0;
            
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Expanding");
                
                
                
                
                
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
                    //Building
                    //Training warriors
                    //Not enough housing
                    //Destroyed building
                    //Depleted Resources

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
            
            
            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Expanding");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Expanding");
            }

            @Override
            public StateExecution substateCheck() {

                //These are the substate conditions that are being checked.
                
                if(myHandle.getFoodHandler().isStarving() == true)
                {
                    return StarvingFunc;
                }
                //There will also be an if statement here checking if we need to switch to PrepareRaid
                
                return null;
            }
        //////////////////////////////////
        //End Expanding State
        //////////////////////////////////
        };
        
        //////////////////////////////////
        //START Starving State Definition
        //////////////////////////////////
        StarvingFunc = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Starving");
                float myFoodAmountProducedPerDay = myCamp.getFoodHandler().getFoodProducedPerDay();
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
                    
                    //THINGS TO DO/CHECK FOR:
                    //Build Farms
                    //Reduce Workforce
                    //Raid for food
                    //Die

                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Starving");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Starving");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                
                //Possibly PrepareRaid will be substate
                return null;
            }
        //////////////////////////////////
        //End Starving State
        //////////////////////////////////
        };
        
        /////////////////////////////////////
        //START PrepareRaid State Definition
        ////////////////////////////////////
        PrepareRaidFunc = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Prepare Raid");
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
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Prepare Raid");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Prepare Raid");
            }

            @Override
            public StateExecution substateCheck() {

                //These are the substate conditions that are being checked.
                
                //Not too sure if PrepareRaid will have a substate
                return null;
            }
        //////////////////////////////////
        //End PrepareRaid State
        //////////////////////////////////
        };
        
        
    }


}
