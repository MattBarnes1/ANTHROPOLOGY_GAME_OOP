/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import Buildings.Building;
import Buildings.BuildingHandler;
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

                public void updateFoodForDay()
                {
                    
                }
                
		public int calculateMaxCropYield()
		{
		}
                
                public boolean canRemoveMore()
                {
                    return ((FarmerAmount - 1) > 0);
                }

   

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
                maxFoodProduced += ((Field)aBuilding).getYield();
       }
       totalFood += (((RequiredFarmers/FarmerAmount)*maxFoodProduced));
    }

    public int getFarmersAmount() {
        return FarmerAmount;
    }
}
