/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.Buildings.Building;
import anthropologyapplication.Buildings.Homes;
import java.util.ArrayList;
/**
 *
 * @author noone
 */
public class PopulationHandler {
    private TribalCampObject myTribe;
    private int TotalPopulation = 10; //Starting Population
    private int FreeCitizens = TotalPopulation;
    private final float CONSUMPTION_WARRIORS = 10F; //Eat the most food per person
    private final float CONSUMPTION_BUILDERS = 7.5F; //Eat the 2nd most etc
    private final float CONSUMPTION_WORKERS = 5F;
    private final float CONSUMPTION_FARMERS = 5F;
    private final float CONSUMPTION_FREE_CITIZENS = 2.5F; //eat the least
    private float dailyFoodUse = 0;
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
        if(currentTimer.EqualTo(new Timer(0,0,0,0)))
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
           TotalPopulation += (int)Math.toIntExact((TotalPopulation-(TotalPopulation%2))/2);
       }
       
    }


    
    
    
}
