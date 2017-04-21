/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.Buildings.Building;
import anthropologyapplication.Buildings.BuildingHandler;
import anthropologyapplication.Buildings.Field;
import java.util.ArrayList;
import java.util.Iterator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class FoodHandler {
    
		BuildingHandler myBuildingHandler;
                float totalFood = 100;
                int FarmerAmount = 0;
                float foodPerDay = 0;
                private final TribalCampObject myTribe;
		public FoodHandler(TribalCampObject myObject)
		{
                        this.myTribe = myObject;
                    myBuildingHandler = myTribe.getBuildingHandler();
		}

		public void addFarmers(int amount)
		{
			FarmerAmount++;
		}

		public void removeFarmers(int Amount)
		{
                    if(getFarmersAmount()-1 < 0)
                    {
                        FarmerAmount = 0;
                    } else {
			FarmerAmount--;
                    }
		}

		public int getCrops()
		{
			throw new NotImplementedException();
		}

                public float getFoodProducedPerDay()
                {
                    return currentFoodProductionRate;
                }

                public void updateFoodForDayPerMS()
                {
                    //return currentFoodProductionRate;
                }
                
                public int getTotalFood()
                {
                    return (int) Math.floor(this.totalFood);
                }
		
                
                
                public boolean canRemoveMore()
                {
                    return ((FarmerAmount - 1) > 0);
                }

   
    float currentFoodProductionRate = 0;
    void update(GameTime MS) {
       ArrayList<Building> aBuildingHandler = myBuildingHandler.getAllBuiltBuildingsByType(Field.class);
       int Check = FarmerAmount;
       Iterator<Building> BuildingIterator = aBuildingHandler.iterator();
       int RequiredFarmers = 0;
       float maxFoodProduced = 0;
       while(BuildingIterator.hasNext())
       {
                
                Building aBuilding = BuildingIterator.next();
                RequiredFarmers += ((Field)aBuilding).getRequiredNumberOfFarmers();
                maxFoodProduced += ((Field)aBuilding).getYield() * MS.getElapsedTime().toMS();
       }
       if(FarmerAmount != 0)
       {
            if(FarmerAmount > RequiredFarmers)
            {
                currentFoodProductionRate = (maxFoodProduced);
            } 
            else
            {
                currentFoodProductionRate = (((FarmerAmount/RequiredFarmers)*maxFoodProduced));
            }
            totalFood += currentFoodProductionRate;
       } else {
           currentFoodProductionRate = 0;
       }
       totalFood -= (this.myTribe.getPopulationHandler().getFoodConsumptionPerMS() * MS.getElapsedTime().toMS());
       if(totalFood < 0)
       {
            totalFood = 0;
       }
       
    }

    public int getFarmersAmount() {
        return FarmerAmount;
    }
}
