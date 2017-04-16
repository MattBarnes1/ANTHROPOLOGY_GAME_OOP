/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import Buildings.Building;
import Buildings.Homes;
import java.util.ArrayList;
/**
 *
 * @author noone
 */
public class PopulationHandler {
    private TribalCampObject myTribe;
    private int TotalPopulation = 10; //Starting Population
    private int FreeCitizens = TotalPopulation;
    public PopulationHandler(TribalCampObject myCamp)
    {
        initialTimer = new Timer(730, 0, 0, 0); //Ever 2 ingame years birth
        currentTimer = initialTimer;
        myTribe = myCamp;
    }

    private final Timer initialTimer;
    private Timer currentTimer;
    
    void update(GameTime MS) {
        currentTimer.subtract(MS.getElapsedTime());
        if(currentTimer.EqualTo(new Timer(0,0,0,0)))
        {
            currentTimer = initialTimer;
            doPopulationGrowth();
        }
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