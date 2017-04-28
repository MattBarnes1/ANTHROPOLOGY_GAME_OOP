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
    private static final float CONSUMPTION_WARRIORS = 1F; //Eat the most food per person
    private static final float CONSUMPTION_BUILDERS = .75F; //Eat the 2nd most etc
    private static final float CONSUMPTION_WORKERS = .5F;
    private static final float CONSUMPTION_FARMERS = .5F;
    private static final float CONSUMPTION_FREE_CITIZENS = .25F; //eat the least
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
        int freeCitizens = myTribe.getFreeCitizens();
        int Warriors = myTribe.getWarriorHandler().getWarriorsAmount();
        float Builders = myTribe.getBuildingHandler().getBuildersAmount();
        int Workers = myTribe.getProductionHandler().getProducersAmount();
        float farmers = myTribe.getFoodHandler().getFarmersAmount();
        dailyFoodUse = (freeCitizens*CONSUMPTION_FREE_CITIZENS);
        System.out.println("FC: " + freeCitizens + " Result: " + freeCitizens*CONSUMPTION_FREE_CITIZENS);
        dailyFoodUse += (farmers * CONSUMPTION_FARMERS);
        System.out.println("FA: " + farmers + " Result: " + farmers*CONSUMPTION_FARMERS);
        dailyFoodUse += (Warriors*CONSUMPTION_WARRIORS);
        System.out.println("WA: " + Warriors + " Result: " + Warriors*CONSUMPTION_WARRIORS);
        dailyFoodUse += (Builders*CONSUMPTION_BUILDERS);
        System.out.println("B: " + Builders + " Result: " + Builders*CONSUMPTION_BUILDERS);
        dailyFoodUse += (Workers*CONSUMPTION_WORKERS);
        System.out.println("WO: " + Workers + " Result: " + Workers*CONSUMPTION_WORKERS);
    }
    
    void update(GameTime MS) {
        
        currentTimer = currentTimer.subtract(MS.getElapsedTime());
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

    public int getTotalPopulation()
    {
        return TotalPopulation;
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
          if(Choice == 0)
          {
              if(myTribe.getFreeCitizens() > 0)
              {
                hasChanged = true;
                  myTribe.removeFreeCitizen();
                  TotalPopulation--;
              } else {
                  isFreeCitizenEmpty = true;
                  i++; //this didn't count because no free citizens
              }
          }
          else if(Choice == 1)
          {
                BuildingHandler myHandle = myTribe.getBuildingHandler();
                if(myHandle.canRemoveMore())
                {
                    hasChanged = true;
                    myHandle.removeBuilders(Choice);
                  TotalPopulation--;
                }
                else {
                    isBuildersEmpty = true;
                    i++; //this didn't count because no free citizens
                }
          }
          else if(Choice == 2)
          {
                FoodHandler myHandle = myTribe.getFoodHandler();
                if(myHandle.canRemoveMore())
                {
                    hasChanged = true;
                    myHandle.removeFarmers(Choice);
                  TotalPopulation--;
                }
                else {
                    isFarmersEmpty = true;
                    i++; //this didn't count because no free citizens
                }
          }
          else if(Choice == 3)
          {
                ProductionHandler myHandle = myTribe.getProductionHandler();
                if(myHandle.getProducersAmount() > 0)
                {
                    hasChanged = true;
                    myHandle.removeProducers(1);
                  TotalPopulation--;
                }
                else {
                    isProducersEmpty = true;
                    i++; //this didn't count because no free citizens
                }
          }
          else if(Choice == 4)
          {
                WarriorHandler myHandle = myTribe.getWarriorHandler();
                if(myHandle.getWarriorsAmount() > 0)
                {
                    hasChanged = true;
                    myHandle.killRandomWarrior();
                  TotalPopulation--;
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

    public static float getFreeCitizensConsumption() {
        return CONSUMPTION_FREE_CITIZENS;
    }

    public static float getWarriorsFoodConsumption() {
        return CONSUMPTION_WARRIORS;
    }

    public static float getBuildersFoodConsumption() {
        return CONSUMPTION_BUILDERS;
    }

    public static float getProducersFoodConsumption() {
        return CONSUMPTION_WORKERS;
    }

    public static float getFarmersFoodConsumption() {
        return CONSUMPTION_FARMERS;
    }

    boolean hasChanged = false;
    public boolean hasChanged() {
        if(hasChanged == true)
        {
            hasChanged = false;
            return true;
        } else {
            return false;
        }
    }

    boolean hasStarvedToDeath() {
       return (this.TotalPopulation == 0);
    }
    
    
    
}
