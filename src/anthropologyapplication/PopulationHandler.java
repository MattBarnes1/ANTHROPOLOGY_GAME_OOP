/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.Buildings.Building;
import anthropologyapplication.Buildings.BuildingHandler;
import anthropologyapplication.Buildings.Homes;
import anthropologyapplication.TradeGoods.ProductionHandler;
import anthropologyapplication.Warriors.WarriorHandler;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author noone
 */
public class PopulationHandler {
    private TribalCampObject myTribe;
    private int TotalPopulation = 10; //Starting Population
    private int FreeCitizens = TotalPopulation;
    private final float CONSUMPTION_WARRIORS = 1F; //Eat the most food per person
    private final float CONSUMPTION_BUILDERS = .75F; //Eat the 2nd most etc
    private final float CONSUMPTION_WORKERS = .5F;
    private final float CONSUMPTION_FARMERS = .5F;
    private final float CONSUMPTION_FREE_CITIZENS = 2.5F; //eat the least
    private float dailyFoodUse = 0;
    private Random myRandom = new Random();
    public PopulationHandler(TribalCampObject myCamp)
    {
        initialTimer = new Timer(730, 0, 0, 0); //Ever 2 ingame years birth
        currentTimer = initialTimer;
        myTribe = myCamp;
    }

    private final Timer initialTimer;
    private Timer currentTimer;
    
    private void updateTotalFoodUse(GameTime MS) {
        dailyFoodUse = 0;
        int freeCitizens = myTribe.getFreeCitizens();
        int Warriors = myTribe.getWarriorHandler().getWarriorsAmount();
        int Builders = myTribe.getBuildingHandler().getBuildersAmount();
        int Workers = myTribe.getProductionHandler().getProducersAmount();
        int farmers = myTribe.getProductionHandler().getProducersAmount();
        dailyFoodUse += (freeCitizens*CONSUMPTION_FREE_CITIZENS);
        dailyFoodUse += (farmers * CONSUMPTION_FARMERS);
        dailyFoodUse += (Warriors*CONSUMPTION_WARRIORS);
        dailyFoodUse += (Builders*CONSUMPTION_BUILDERS);
        dailyFoodUse += (Workers*CONSUMPTION_WORKERS);
    }
    
    void update(GameTime MS) {
        
        currentTimer.subtract(MS.getElapsedTime());
        if(currentTimer.EqualTo(Timer.Zero))
        {
            currentTimer = initialTimer;
            doPopulationGrowth();
        }
        updateTotalFoodUse(MS);
    }

    public float getFoodConsumptionPerDay()
    {
        return dailyFoodUse;//TODO: Fix
    }
    
    public float getFoodConsumptionPerMS()
    {
        return dailyFoodUse/86400000;
    }
    private void doPopulationGrowth() {
       ArrayList<Building> myBuildings = myTribe.getBuildingHandler().getAllBuiltBuildingsByType(Homes.class);
       int AmountInHomes = 0;
       for(Building X : myBuildings)
       {
           AmountInHomes = ((Homes)X).getOccupancy();
       }
       
       if(AmountInHomes > TotalPopulation)
       {
           TotalPopulation += ((TotalPopulation-(TotalPopulation%2))/2);
       } else {
           int newCitizens = (int)Math.toIntExact((TotalPopulation-(TotalPopulation%2))/2);
           TotalPopulation += newCitizens;
           myTribe.addFreeCitizens(newCitizens);
       }
       
    }

    void doStarvation() {
      int amountToDecimate = (int)Math.floor((float)TotalPopulation * ((float)1/(float)myRandom.nextInt(4)));
      boolean isWarriorsEmpty = false;
      boolean isFreeCitizenEmpty = false;
      boolean isBuildersEmpty = false;
      boolean isFarmersEmpty = false; 
      boolean isProducersEmpty = false;
      
      for(int i = 0; i < amountToDecimate; i++)
      {
          int Choice = myRandom.nextInt(6);
          if(Choice == 1)
          {
              if(myTribe.getFreeCitizens() > 0)
              {
                  myTribe.removeFreeCitizen();
              } else {
                  isFreeCitizenEmpty = true;
                  i++; //this didn't count because no free citizens
              }
          }
          else if(Choice == 2)
          {
                BuildingHandler myHandle = myTribe.getBuildingHandler();
                if(myHandle.canRemoveMore())
                {
                    myHandle.removeBuilders(Choice);
                }
                else {
                    isBuildersEmpty = true;
                    i++; //this didn't count because no free citizens
                }
          }
          else if(Choice == 3)
          {
                FoodHandler myHandle = myTribe.getFoodHandler();
                if(myHandle.canRemoveMore())
                {
                    myHandle.removeFarmers(Choice);
                }
                else {
                    isFarmersEmpty = true;
                    i++; //this didn't count because no free citizens
                }
          }
          else if(Choice == 4)
          {
                ProductionHandler myHandle = myTribe.getProductionHandler();
                if(myHandle.canRemoveMore())
                {
                    myHandle.removeProducers(1);
                }
                else {
                    isProducersEmpty = true;
                    i++; //this didn't count because no free citizens
                }
          }
          else if(Choice == 5)
          {
                WarriorHandler myHandle = myTribe.getWarriorHandler();
                if(myHandle.getWarriorsAmount() > 0)
                {
                    myHandle.killRandomWarrior();
                }
                else {
                    isWarriorsEmpty = true;
                    i++; //this didn't count because no free citizens
                }
          }
          else if (isWarriorsEmpty && isFarmersEmpty && isBuildersEmpty && isProducersEmpty && isFreeCitizenEmpty)
          {
              break; //All dead!
          }
      }
    }

    public float getFreeCitizensConsumption() {
        return this.CONSUMPTION_FREE_CITIZENS;
    }

    public float getWarriorsFoodConsumption() {
        return this.CONSUMPTION_WARRIORS;
    }

    public float getBuildersFoodConsumption() {
        return this.CONSUMPTION_BUILDERS;
    }

    public float getProducersFoodConsumption() {
        return this.CONSUMPTION_WORKERS;
    }

    public float getFarmersFoodConsumption() {
        return this.CONSUMPTION_FARMERS;
    }


    
    
    
}
