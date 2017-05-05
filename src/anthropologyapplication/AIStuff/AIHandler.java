 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.AIStuff;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Buildings.Building;
import anthropologyapplication.Buildings.Field;
import anthropologyapplication.Buildings.Homes;
import anthropologyapplication.Logger.FileLogger;
import anthropologyapplication.FoodHandler;
import anthropologyapplication.PopulationHandler;
import anthropologyapplication.Timer;
import anthropologyapplication.TradeGoods.ResourceArray;
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
    
    //Main states
    private StateExecution initialState;
    private StateExecution hungryState;
    private StateExecution buildHomesState;
    private StateExecution genWarriorsState;
    
    //Substates
    private StateExecution NER_Builders;
    private StateExecution NER_Workers;
    private StateExecution NER_Clay;
    private StateExecution NER_Stone;
    private StateExecution NER_Wood;
    private StateExecution NER_UnsmCopper;
    private StateExecution NER_SmCopper;
    
    private final TribalCampObject myCamp;
    private Stack<StateExecution> myFunctionStack = new Stack<>();
    private StateExecution myCurrentFunctionToExecute;
    boolean isAlive = true;
    private Random DrunkardWalkran = new Random(); 
//Warriors, Builders, 
    
    
    public AIHandler(AICampObject myCampObject) {
        this.myCamp = myCampObject;
        createStateInterfaces(); //Create our interfaces?
    }

    @Override
    protected Task createTask() {
        Task aTask = new Task<Object>() {
            protected String call() {
                setStateExecution(initialState); //Since Initial is our 'root' node of the state tree it automatically gets selected
                while (isAlive) //loops until we set the dead state then ends;
                {
                    //if (myCurrentFunctionToExecute == null) {

                   // } else {
                        StateExecution aNewState = myCurrentFunctionToExecute.substateCheck();
                        if (aNewState != null) {
                            aNewState.onEnter();
                            setStateExecution(aNewState);
                        } else if (myCurrentFunctionToExecute.isFinished()) {
                            stateHasFinished();
                        }
                    //}

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
            myCurrentFunctionToExecute.onExit();
        }
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
        initialState = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand
            
            //There will probably be more things to set to zero here
            int FieldNumber = 0;
            int GranaryNumber = 0;
            
            @Override
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Initial");               
                
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            @Override
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

            @Override
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
                //I am not 100% positive what we are going to be checking for here to have them
                //generate warriors
                //Maybe isHungry and isNuff homes are both false, then just generaate warriors if there are none?
                
                //This is temporary until we figure out exactly what to check for here
                return(isHungry() && isNuffHomes() && myHandle.getWarriorHandler().getWarriorsAmount() == 0);

            }

            private boolean isNuffHomes()
            {
                int numHomes = myHandle.getBuildingHandler().countBuildingsInList(Homes.class);
                
                return ((numHomes*2) >= myHandle.getPopulationHandler().getTotalPopulation());

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
            
            
            float FarmersAmount;
            float ProducersAmount;
            float WarriorsAmount;
            float FarmersRequiredAmountPerField; 
            float BuildersRequiredAmount;
            float maxFieldDailyYield;
            float calInitialFoodDifference;
            float myFoodAmountProducedPerDay;
            float FreeCitizensAmount;
            float totalFoodStores;
            float buildersAmount;
            float FieldsAmount;
            float FieldsInProduction;
            float TotalFarmersRequired;
            float farmersRequiredAmountTotal;
            float FarmersNegativeAdjustment = 0;
            float FarmersAdjustment = 0;
            @Override
            public void onEnter() {
                isBuildingFieldsNow = false;
                maxFieldDailyYield = 0;
                //FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Food (Hungry)");
                ArrayList<Building> myBuildings = myHandle.getBuildingHandler().getAllBuiltBuildingsByType(Field.class);
                BuildersRequiredAmount = 0;
                for(int i = 0; i < myBuildings.size(); i++)
                {
                    Field myField = (Field)myBuildings.get(i);
                    maxFieldDailyYield += myField.getDailyYield();
                    TotalFarmersRequired += myField.getRequiredNumberOfFarmers();
                    BuildersRequiredAmount += myField.getRequiredBuildersAmount();
                }
                
                
                myFoodAmountProducedPerDay = myCamp.getFoodHandler().getFoodProducedPerDay();
                FieldsAmount = myCamp.getBuildingHandler().getAllBuiltBuildingsByType(Field.class).size();
                ProducersAmount = myHandle.getProductionHandler().getProducersAmount();
                buildersAmount = myHandle.getBuildingHandler().getBuildersAmount();
                FreeCitizensAmount = myHandle.getFreeCitizens();
                calInitialFoodDifference = myFoodAmountProducedPerDay - estimateConsumption(0,0,0,0);
                totalFoodStores = myCamp.getFoodHandler().getTotalFood();
                WarriorsAmount = myCamp.getWarriorHandler().getWarriorsAmount();
                FarmersNegativeAdjustment = 0;
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Food (Hungry)" +
                        "\nReported Field Yield: "    + maxFieldDailyYield 
                        + "\nFood Produced Per Day: "   + myFoodAmountProducedPerDay
                        + "\nFarmer Amount: "           + FarmersAmount
                        + "\nProducion - Consumption Reported: "    + calInitialFoodDifference
                        + "\nTotal Food: "    + totalFoodStores);
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }
            boolean isBuildingFieldsNow = false;
            @Override
            public void Execute() {
                if (super.shouldExecute && !isFinished()) {
                    if(!isDoingPseudoCalculations())
                    {
                        int result = rebalancePeople();
                        
                        if(result == -1) //FailedToRebalance
                        {
                            if(!isBuildingFieldsNow)
                            {
                                doFieldBuilding();
                            }
                        } 
                        else if(result == 0) //success but recalculation conflict could occur
                        {
                            //Do the pseudo rebalancing thing here.
                            
                        }
                        else if(result == 1) //aboslute success;
                        {
                            isFinished = true;
                        }
                    } else {
                        doFieldBuilding(); 
                    }
                    if(this.estimateProduction(0) - this.estimateConsumption(0, 0, 0, 0) > 0)
                    {
                        this.isFinished = true;
                    }
                    
                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }
            
            private void doFieldBuilding()
            {
                this.myHandle.getBuildingHandler().clearBuildingList();
                int iterations = (int)Math.abs(Math.ceil(this.calInitialFoodDifference/2));
                for(int i = 0 ; i < iterations; i++)
                {
                    MapTile myHomeLocation = myHandle.getMapTileLocation();
                    MapTile myNewBuildLocation = drunkenWalker(myHomeLocation);
                    myHandle.getBuildingHandler().startBuilding("Field", myNewBuildLocation); 
                }
                int BuildersRequiredForOptimalSpeed = iterations * 2;
                int FarmersRequiredForOptimalSpeed = iterations * 2;
                //Can we support this many builders without running out of total food?
                int buildersDifference = (int)(BuildersRequiredForOptimalSpeed + this.BuildersRequiredAmount);
                float foodChangeDaily = estimateConsumption(0, buildersDifference, 0, 0);
                float NumberOfDaysForSimultaneousFieldCompletion =  0;
                if(this.BuildersRequiredAmount == 0)
                {
                    NumberOfDaysForSimultaneousFieldCompletion =  (float)myHandle.getBuildingHandler().getBuildTimeByClass(Field.class).dividedBy(Timer.Day); //TODO: thi
                }
                if(foodChangeDaily * NumberOfDaysForSimultaneousFieldCompletion < this.totalFoodStores  && buildersDifference <= this.buildersAmount && FarmersRequiredForOptimalSpeed <= this.FarmersAmount)
                {
                    adjustPopulation(0, (int)buildersDifference, 0, 0);
                    isFinished = true;
                    //We are okay; //Adjust Builders and run with it.
                }
                else {
                    foodChangeDaily = estimateConsumption(0, 0, 0, 0);
                    NumberOfDaysForSimultaneousFieldCompletion = (float)myHandle.getBuildingHandler().getBuildTimeByClass(Field.class).dividedBy(Timer.Day); //TODO: thi //restimate this to determine if we are screwed or not 
                    
                    if(Math.min(foodChangeDaily, foodChangeDaily * NumberOfDaysForSimultaneousFieldCompletion) < this.totalFoodStores)
                    {                    
                        //FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, "AI CAMP", "I was close to starving!");
                        //We can survive with the current number of builders, but can we get it to go faster?
                        float BuildersTemp = this.buildersAmount;
                        float differenceCount = 0;
                        float farmersTemp = this.FarmersAmount;
                        float farmersDifference = 0;
                        while(foodChangeDaily * NumberOfDaysForSimultaneousFieldCompletion < this.totalFoodStores && buildersDifference > BuildersTemp && FarmersRequiredForOptimalSpeed > farmersDifference)
                        {
                             foodChangeDaily = estimateConsumption(0, (int)BuildersTemp, (int)farmersTemp, 0);
                             NumberOfDaysForSimultaneousFieldCompletion = (float)myHandle.getBuildingHandler().getBuildTimeByClass(Field.class).dividedBy(Timer.Day) * (BuildersTemp/buildersDifference); //TODO: thi; //restimate this to determine if we are screwed or not 
                             BuildersTemp++;
                             float checkfoodChangeDaily = estimateConsumption(0, (int)BuildersTemp, (int)farmersTemp, 0);
                             if(checkfoodChangeDaily * NumberOfDaysForSimultaneousFieldCompletion > this.totalFoodStores)
                             {
                                 break;
                             }
                             differenceCount++;
                             
                             farmersTemp++;
                             checkfoodChangeDaily = estimateConsumption(0, (int)BuildersTemp, (int)farmersTemp, 0);
                             if(checkfoodChangeDaily * NumberOfDaysForSimultaneousFieldCompletion > this.totalFoodStores)
                             {
                                 break;
                             }
                             farmersDifference++;
                        }
                       // if(foodChangeDaily * NumberOfDaysForSimultaneousFieldCompletion > this.totalFoodStores)
                       // {
                       //     differenceCount--;
                        //}
                        writeSuccessfulRebalanceToLog();
                        adjustPopulation(0, (int)differenceCount, 0, (int)farmersDifference);
                        //FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, "AI CAMP", "Starvation: Found a happy median");
                        this.isBuildingFieldsNow = true;
                    }
                    else {
                        FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, "AI CAMP", "Starvation: No Hope");
                    }
                }

            }
            
            private int rebalancePeople()
            {
                /*FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, "AI CAMP", "Attempting to Rebalance Population: " 
                      + "\nFarmers: "       + this.FarmersAmount
                      + "\nBuilders: "      + this.buildersAmount
                      + "\nWorkers: "       + this.ProducersAmount
                      + "\nFree Citizens: " + this.FreeCitizensAmount
                      + "\nWarriors: "      + this.WarriorsAmount
                      + "\nFood Difference " + this.calInitialFoodDifference);*/
                float buildersAdjustment = 0;
                float FarmersAdjustment = 0;
                float freeCitizensAdjustment = 0;
                float producersAdjustment = 0;
                
                ////////////////////////////////
                //Free non-essential People/////
                ////////////////////////////////
                if(this.BuildersRequiredAmount < this.buildersAmount)
                {
                    buildersAdjustment = buildersAmount - BuildersRequiredAmount;
                    freeCitizensAdjustment = buildersAdjustment;
                    if(estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment, 0, 0) < this.myFoodAmountProducedPerDay)
                    {
                        writeSuccessfulRebalanceToLog();
                        adjustPopulation((int)freeCitizensAdjustment, (int)buildersAdjustment, (int)producersAdjustment, (int)FarmersAdjustment);
                        calInitialFoodDifference = myFoodAmountProducedPerDay - estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment,(int)-producersAdjustment,(int)-FarmersAdjustment);
                        return 1; //success && no chance of recalculation errors
                    }
                }
                
                if(myHandle.getProductionHandler().getRequiredProducerAmount() < myHandle.getProductionHandler().getProducersAmount())
                {
                    producersAdjustment = myHandle.getProductionHandler().getProducersAmount() - myHandle.getProductionHandler().getRequiredProducerAmount();
                    freeCitizensAdjustment += producersAdjustment;
                    if(estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment, (int)-producersAdjustment, 0) < this.myFoodAmountProducedPerDay)
                    {
                        writeSuccessfulRebalanceToLog();
                        adjustPopulation((int)freeCitizensAdjustment, (int)buildersAdjustment, (int)producersAdjustment, (int)FarmersAdjustment);
                        calInitialFoodDifference = myFoodAmountProducedPerDay - estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment,(int)-producersAdjustment,(int)-FarmersAdjustment);
                        return 1; //success && no chance of recalculation errors
                    }
                }                
                               
                if(this.farmersRequiredAmountTotal < this.FarmersAmount)
                {
                    FarmersNegativeAdjustment = this.FarmersAmount - this.farmersRequiredAmountTotal;
                    freeCitizensAdjustment += FarmersAdjustment;
                    if(estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment, (int)-producersAdjustment, (int)-FarmersNegativeAdjustment) < this.myFoodAmountProducedPerDay)
                    {
                        writeSuccessfulRebalanceToLog();
                        adjustPopulation((int)freeCitizensAdjustment, (int)buildersAdjustment, (int)producersAdjustment, (int)FarmersNegativeAdjustment);
                        calInitialFoodDifference = myFoodAmountProducedPerDay - estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment,(int)-producersAdjustment, (int)FarmersNegativeAdjustment);
                        return 1; //success && no chance of recalculation errors
                    }
                } 
                else if(this.farmersRequiredAmountTotal > this.FarmersAmount)
                {
                    if(freeCitizensAdjustment > 0)
                    {
                        if(freeCitizensAdjustment > (this.farmersRequiredAmountTotal - this.FarmersAmount))
                        {
                            freeCitizensAdjustment -= (this.farmersRequiredAmountTotal - this.FarmersAmount);
                            this.FarmersAdjustment += (this.farmersRequiredAmountTotal - this.FarmersAmount);
                        } else {
                            this.FarmersAdjustment += freeCitizensAdjustment;
                            freeCitizensAdjustment = 0;
                        }
                        if(estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment, (int)-producersAdjustment, (int)FarmersAdjustment) < estimateProduction((int)FarmersAdjustment))
                        {
                            writeSuccessfulRebalanceToLog();
                            adjustPopulation((int)freeCitizensAdjustment, (int)buildersAdjustment, (int)producersAdjustment, (int)FarmersAdjustment);
                            calInitialFoodDifference = myFoodAmountProducedPerDay - estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment,(int)-producersAdjustment,(int)FarmersAdjustment);
                            return 1; //success && no chance of recalculation errors
                        } else {
                            
                        }
                    }
                }
                ////////////////////////////////
                //End Free Non-essential People
                ////////////////////////////////
          
                
          
                //Force pull from producers
                //At most we need one producer (at this moment)
                if(this.ProducersAmount > 2)
                {
                    float difference = (this.ProducersAmount - 2);
                    freeCitizensAdjustment += difference;
                    producersAdjustment += difference;
                    if(estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment, (int)-producersAdjustment, (int)-FarmersAdjustment) < this.myFoodAmountProducedPerDay)
                    {
                        //writeSuccessfulRebalanceToLog();
                        adjustPopulation((int)freeCitizensAdjustment, (int)buildersAdjustment, (int)producersAdjustment, (int)FarmersAdjustment);
                        calInitialFoodDifference = myFoodAmountProducedPerDay - estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment,(int)-producersAdjustment,(int)FarmersAdjustment);
                        return 1; //success && no chance of recalculation errors
                    }
                }
                
                if(this.buildersAmount > 2)
                {
                    float difference = (this.buildersAmount - 2);
                    freeCitizensAdjustment += difference;
                    buildersAdjustment += difference;
                    if(estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment, (int)-producersAdjustment, (int)-FarmersAdjustment) < this.myFoodAmountProducedPerDay)
                    {
                        //writeSuccessfulRebalanceToLog();
                        adjustPopulation((int)freeCitizensAdjustment, (int)buildersAdjustment, (int)producersAdjustment, (int)FarmersAdjustment);
                        calInitialFoodDifference = myFoodAmountProducedPerDay - estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment,(int)-producersAdjustment,(int)FarmersAdjustment);
                        return 1; //success && no chance of recalculation errors
                    }
                }
                
                //////////////
                adjustPopulation((int)freeCitizensAdjustment, (int)buildersAdjustment, (int)producersAdjustment, (int)FarmersAdjustment);
                //writeFailureRebalanceToLog();
                calInitialFoodDifference = myFoodAmountProducedPerDay - estimateConsumption((int)freeCitizensAdjustment,(int)-buildersAdjustment,(int)-producersAdjustment,(int)FarmersAdjustment);
                return -1; //Failure
            }
            
            private void writeSuccessfulRebalanceToLog()
            {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, "CAMP AI", "After rebalancing population: " 
                          + "\nFarmers: "           + this.FarmersAmount
                          + "\nBuilders: "          + this.buildersAmount
                          + "\nWorkers: "           + this.ProducersAmount
                          + "\nFree Citizens: "     + this.FreeCitizensAmount
                          + "\nWarriors: "          + this.WarriorsAmount
                          + "\nFood Difference "    + this.calInitialFoodDifference);
            }
            
            
            
            private void writeFailureRebalanceToLog()
            {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, "CAMP AI", "After rebalancing population: " 
                      + "\nFarmers: "       + this.FarmersAmount
                      + "\nBuilders: "      + this.buildersAmount
                      + "\nWorkers: "       + this.ProducersAmount
                      + "\nFree Citizens: " + this.FreeCitizensAmount
                      + "\nWarriors: "      + this.WarriorsAmount
                      + "\nFood Difference " + this.calInitialFoodDifference);
            }
            
            public float estimateConsumption(int freeCitizensDiff, int buildersDiff, int ProducersDiff, int farmersDiff)
            {
               int Consumption = 0;
               Consumption += (WarriorsAmount*PopulationHandler.getWarriorsFoodConsumption());
               Consumption += ((this.buildersAmount + buildersDiff) * PopulationHandler.getBuildersFoodConsumption());
               Consumption += ((this.FreeCitizensAmount + freeCitizensDiff) * PopulationHandler.getFreeCitizensConsumption());
               Consumption += ((this.ProducersAmount + ProducersDiff) * PopulationHandler.getProducersFoodConsumption());
               Consumption += ((this.FarmersAmount + farmersDiff) * PopulationHandler.getFarmersFoodConsumption());
               return Consumption;
            }
            
            public float estimateProduction(int diffFarmer)
            {
                return maxFieldDailyYield*Math.max(1, (diffFarmer + this.FarmersAmount)/this.farmersRequiredAmountTotal);
            }
            
            private boolean isDoingPseudoCalculations()
            {
                return false;
            }
            
            

            private MapTile drunkenWalker(MapTile myTile)
            {
                int Amount = DrunkardWalkran.nextInt(3);
                MapTile myNextTile = myTile;
                for(int i = 0; i < Amount; i++)
                {
                  int Choice = DrunkardWalkran.nextInt(8) + 1; //So we ignore 0
                  MapTile nextTile = getMapTileByRandomWalking(Choice, myNextTile);
                  if(nextTile == null)
                  {
                      i--;
                  } else {
                    if(nextTile.isTerritoryOf(myHandle) && !nextTile.hasBuilding() && nextTile.isLand())
                    {
                        myNextTile = nextTile;
                    } else {
                        i--;
                    }
                  }
                }
                return myNextTile;
            }
            
            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Food (Hungry)");

            }

            @Override
            public void onFinish() {
                
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Food (Hungry)");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                
                //NER_Builders substate check will go here

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
                throw new RuntimeException("Walking Algorithm Failed!");
            }

            private void adjustPopulation(int freeCitizensAdjustment, int buildersAdjustment, int producersAdjustment, int FarmersAdjustment) {
                if(freeCitizensAdjustment > 0)
                {
                    myHandle.addFreeCitizens(freeCitizensAdjustment);
                } else {
                    myHandle.removeFreeCitizens(Math.abs(freeCitizensAdjustment));
                }
                if(producersAdjustment > 0)
                {
                    myHandle.getProductionHandler().addProducers(Math.abs(producersAdjustment));
                } else {
                    myHandle.getProductionHandler().removeProducers(Math.abs(producersAdjustment));
                }
                
                if(FarmersAdjustment > 0)
                {
                    myHandle.getFoodHandler().addFarmers(FarmersAdjustment);
                } else {
                    myHandle.getFoodHandler().removeFarmers(Math.abs(FarmersAdjustment));
                }
                
                if(buildersAdjustment > 0)
                {
                    myHandle.getBuildingHandler().addBuilders(buildersAdjustment); 
                } else {
                    myHandle.getBuildingHandler().removeBuilders(Math.abs(buildersAdjustment)); 
                }
            }

          
        };
        
        /////////////////////////////////////
        //START Building Homes State Definition
        ////////////////////////////////////
        buildHomesState = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand
            int totalPopulation;
            float numberofHomesToBuildCurrently;
            ResourceArray resourcesRequired;
            @Override
            public void onEnter() {

                totalPopulation = myHandle.getPopulationHandler().getTotalPopulation();
                numberofHomesToBuildCurrently = (Math.floorDiv(myHandle.getPopulationHandler().getTotalPopulation(), 2) - myHandle.getBuildingHandler().countBuildingsInList(Homes.class));
                resourcesRequired = myHandle.getBuildingHandler().getBuildingResourceArray(Homes.class);
                resourcesRequired = resourcesRequired.multiply(numberofHomesToBuildCurrently);
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Homes");
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            @Override
            public void Execute() {
                if (super.shouldExecute && !isFinished()) {
                    if(myHandle.getProductionHandler().checkResourceArray(resourcesRequired) == -1)
                    {
                        for(int i = 0; i < numberofHomesToBuildCurrently; i++)
                        {
                            MapTile myBuildTile = drunkenWalker(myHandle.getMapTileLocation()); //TODO: Watch out this could maybe explode
                            this.myHandle.getBuildingHandler().startBuilding("Home", myBuildTile);
                        }
                    }
                    //THINGS TO DO/CHECK FOR:s
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
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Homes");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Homes");
            }

            
            private MapTile drunkenWalker(MapTile myTile)
            {
                int Amount = DrunkardWalkran.nextInt(3);
                MapTile myNextTile = myTile;
                for(int i = 0; i < Amount; i++)
                {
                  int Choice = DrunkardWalkran.nextInt(8) + 1; //So we ignore 0
                  MapTile nextTile = getMapTileByRandomWalking(Choice, myNextTile);
                  if(nextTile == null)
                  {
                      i++;
                  } else {
                    if(nextTile.isTerritoryOf(myHandle))
                    {
                        myNextTile = nextTile;
                    } else {
                        i++;
                    }
                  }
                }
                return myNextTile;
            }
            
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
                throw new RuntimeException("Walking Algorithm Failed!");
            }
            
            
            @Override
            public StateExecution substateCheck() {

                
                

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

            @Override
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Warriors");
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            @Override
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

            @Override
            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Warriors");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Warriors");
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
        
         ////////////////////////////////////
        //START NER_Builders State Definition
        /////////////////////////////////////
        NER_Builders = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            @Override
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State:  Not Enough Builders");
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
                    

                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            @Override
            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Builders");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Builders");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                

                return null;
            }
        //////////////////////////////////
        //End NER_Builders State
        //////////////////////////////////
        };
        
         ////////////////////////////////////
        //START NER_Workers State Definition
        /////////////////////////////////////
        NER_Workers = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            @Override
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Workers");
                float myFoodAmountProducedPerDay = myCamp.getFoodHandler().getFoodProducedPerDay();
                //float myFoodConsumptionPerDay = myCamp.
                
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            @Override
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

            @Override
            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Workers");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Workers");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                
                return null;
            }
        //////////////////////////////////
        //End NER_Workers State
        //////////////////////////////////
        };
        
         //////////////////////////////////
        //START NER_Clay State Definition
        //////////////////////////////////
        NER_Clay = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            @Override
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Clay");
                float myFoodAmountProducedPerDay = myCamp.getFoodHandler().getFoodProducedPerDay();
                //float myFoodConsumptionPerDay = myCamp.
                
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            @Override
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

            @Override
            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Clay");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Clay");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                

                return null;
            }
        //////////////////////////////////
        //End NER_Clay State
        //////////////////////////////////
        };
        
         //////////////////////////////////
        //START NER_Wood State Definition
        //////////////////////////////////
        NER_Wood = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            @Override
            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Wood");
                float myFoodAmountProducedPerDay = myCamp.getFoodHandler().getFoodProducedPerDay();
                //float myFoodConsumptionPerDay = myCamp.
                
                /////////////////////////////////////////////////////////////////////////
                //We could possibly pre-generate data here to make Execute execute faster
                /////////////////////////////////////////////////////////////////////////
                super.shouldExecute = true; //Ready to fire the AI Event
            }

            @Override
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
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Wood");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Wood");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                

                return null;
            }
        //////////////////////////////////
        //End NER_Wood State
        //////////////////////////////////
        };
        
         //////////////////////////////////
        //START NER_Stone State Definition
        //////////////////////////////////
        NER_Stone = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Stone");
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
                    

                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Stone");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Stone");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                

                return null;
            }
        //////////////////////////////////
        //End NER_Stone State
        //////////////////////////////////
        };
        
         //////////////////////////////////////
        //START NER_UnsmCopper State Definition
        ///////////////////////////////////////
        NER_UnsmCopper = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Unsmelted Copper");
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
                    

                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Unsmelted Copper");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Unsmelted Copper");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                

                return null;
            }
        //////////////////////////////////
        //End NER_UnsmCopper State
        //////////////////////////////////
        };
        
         //////////////////////////////////////
        //START NER_SmCopper State Definition
        ///////////////////////////////////////
        NER_SmCopper = new StateExecution() {
            TribalCampObject myHandle = myCamp;//can inherit data inside this class meaning that all the food hand

            public void onEnter() {

                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Entering State: Not Enough Smelted Copper");
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
                    

                    if (!isFinished()) {
                        onExit();
                    } else {
                        myCurrentFunctionToExecute.onFinish();
                    }
                }
            }

            public void onExit() { //This is to tell us the event is still live but not active.
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Exiting State: Not Enough Smelted Copper");

            }

            @Override
            public void onFinish() {
                FileLogger.writeToLog(FileLogger.LOGTO.CAMP_AI, AIHandler.class.toString(), "Finishing State: Not Enough Smelted Copper");
            }

            @Override
            public StateExecution substateCheck() { 
                //These are the substate conditions that are being checked.
                
                return null;
            }
        //////////////////////////////////
        //End NER_SmCopper State
        //////////////////////////////////
        };
        
    }
}
