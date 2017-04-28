/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.AIStuff;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Buildings.Building;
import anthropologyapplication.Buildings.Field;
import anthropologyapplication.Logger.FileLogger;
import anthropologyapplication.FoodHandler;
import anthropologyapplication.TribalCampObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
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
    private StateExecution initialState;
    private StateExecution hungryState;
    private StateExecution buildHomesState;
    private StateExecution genWarriorsState;

    private final TribalCampObject myCamp;
    private Stack<StateExecution> myFunctionStack = new Stack<>();
    private StateExecution myCurrentFunctionToExecute;
    boolean isAlive = true;
    private Random DrunkardWalkran = new Random(); 
  
    
    
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
                        setStateExecution(initialState); //Since Expanding Func is our 'root' node of the state tree it automatically gets selected
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
        hungryState = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand
            
            
            int FieldNumber = 0;
            int GranaryNumber = 0;
            
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Initial");               
                
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            public void Execute() {
                if (super.shouldExecute && !isFinished()) {
                    
                    int numBuilders = myHandle.getBuildingHandler().getBuildersAmount();
                    
                    //Make sure that if we are in intial state, we are no longer wasting food on non-working builders
                    if(myHandle.getBuildingHandler().countBuildingsBeingBuilt() == 0 && numBuilders > 0)
                    {
                        myHandle.getBuildingHandler().removeBuilders(numBuilders);
                        myHandle.addFreeCitizens(numBuilders);
                        myHandle.getBuildingHandler().clearBuildingList();
                    }

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
                float Check = myCamp.getFoodHandler().getFarmersAmount();
                Iterator<Building> BuildingIterator = aBuildingHandler.iterator();
                int RequiredFarmers = 0;
                float maxFoodProduced = 0;
                float maxGhostFoodProduced = 0;
                float TotalFoodProducedPerDay = 0;
                while(BuildingIterator.hasNext())
                {
                         Building aBuilding = BuildingIterator.next();
                         RequiredFarmers += (((Field)aBuilding).getRequiredNumberOfFarmers());
                         maxFoodProduced += (((Field)aBuilding).getDailyYield());
                }
                aBuildingHandler = myCamp.getBuildingHandler().getBuildingsCurrentlyBeingBuiltByType(Field.class);
                BuildingIterator = aBuildingHandler.iterator();
                while(BuildingIterator.hasNext())
                {
                         Building aBuilding = BuildingIterator.next();
                         RequiredFarmers += (((Field)aBuilding).getRequiredNumberOfFarmers());
                         maxGhostFoodProduced += (((Field)aBuilding).getDailyYield());
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
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Initial");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Initial");
            }
            
            private boolean isHungry()
            {
                //Make sure math here is correct
                return((estimateProduction() - estimateConsumption()) < 0);
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

            @Override
            public StateExecution substateCheck() {

                //These are the substate condition functions that are being checked.             
                if(isHungry())
                {
                    return hungryState;
                }
                else if(isNuffWarriors())
                {
                    return genWarriorsState;
                }
                else if(isNuffHomes())
                {
                    return buildHomesState;
                }
                return null;
            }
        //////////////////////////////////
        //End Initial State
        //////////////////////////////////
        };
        
        //////////////////////////////////
        //START Hungry State Definition
        //////////////////////////////////
        hungryState = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand
            float FarmersAmount = ((Field)myHandle.getBuildingHandler().getInternalBuildingByClass(Field.class)).getRequiredNumberOfFarmers();
            float BuildersAmount = ((Field)myHandle.getBuildingHandler().getInternalBuildingByClass(Field.class)).getRequiredBuildersAmount();
            float fieldDailyYield = ((Field)myHandle.getBuildingHandler().getInternalBuildingByClass(Field.class)).getDailyYield();
            float calInitialFoodDifference = calculateInitialFoodDifferenceToSolveFor();
            
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Fixing Hungry");
                float myFoodAmountProducedPerDay = myCamp.getFoodHandler().getFoodProducedPerDay();
                //float myFoodConsumptionPerDay = myCamp.
                
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            public void Execute() {
                if (super.shouldExecute && !isFinished()) {
                    if(isDoingPseudoCalculations())
                    {
                        if(rebalancePeople() == -1) //FailedToRebalance
                        {
                            doFieldBuilding();
                        } else {
                            //Do the pseudo rebalanceing thing here.
                            
                        }
                    } else {
                        doFieldBuilding();
                        
                    }
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
            
            private void doFieldBuilding()
            {
                        float neededFarmsToCorrectFor = calInitialFoodDifference/fieldDailyYield;
                        float calculateAdjustedYield = 
                        int CurrentlyRequired = myHandle.getBuildingHandler().getRequiredBuildersAmountInConstructionQueue();
                        int NextRequired = CurrentlyRequired + 
                        float FoodRequirements = TotalPopulation
                        if(myHandle.getBuildingHandler().getBuildersAmount() > )
                        {
                            
                        }
                        
                        
                        this.doCanBuild();
                        
                        if(isHungry())
                        {
                            
                        }
            }
            
            
            private boolean isDoingPseudoCalculations()
            {
                return false;
            }
            
            

            private MapTile drunkenWalker(MapTile myTile)
            {
                int Amount = DrunkardWalkran.nextInt(3)
                MapTile myNextTile = myTile;
                for(int i = 0; i < Amount; i++)
                {
                  int Choice = DrunkardWalkran.nextInt(8) + 1; //So we ignore 0
                  MapTile nextTile = getMapTileByRandomWalking(Choice, myNextTile);
                  if(nextTile == null)
                  {
                      i++;
                      continue;
                  } else {
                    if(nextTile.isTerritoryOf(myHandle))
                    {
                        myNextTile = nextTile;
                    } else {
                        i++;
                        continue;
                    }
                  }
                }
                return myNextTile;
            }
            
            private int rebalancePeople()
            {
                int Builders = myHandle.getBuildingHandler().getBuildersAmount();
                
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
                

                return null;
            }
        //////////////////////////////////
        //End Hungry State
        //////////////////////////////////

            private MapTile getMapTileByRandomWalking(int Choice, MapTile myTile) {
               switch(Choice)
               {
                   case 1:
                       return myTile.getNorth();
                       //break;
                   case 2:
                       return myTile.getEast();
                    case 3:
                       return myTile.getSouth();
                    case 4:
                       return myTile.getWest();
                    case 5:
                       return myTile.getNortheast();
                    case 6:
                       return myTile.getNorthwest();
                    case 7:
                       return myTile.getSoutheast();
                    case 8:
                       return myTile.getSouthwest();
               }
            }
        };
        
        /////////////////////////////////////
        //START Building Homes State Definition
        ////////////////////////////////////
        buildHomesState = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Building Homes");
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
        //End Build Homes State
        //////////////////////////////////
        };
        
        /////////////////////////////////////
        //START GenWarrior State Definition
        ////////////////////////////////////
        genWarriorsState = new StateExecution() {
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
                

                return null;
            }
        //////////////////////////////////
        //End GenWarriors Homes State
        //////////////////////////////////
        };
        
        
    }


}
