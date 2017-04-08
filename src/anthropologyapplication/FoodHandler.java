/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class FoodHandler {
    
		BuildingHandler myBuildingHandler;
                int FarmerAmount = 0;
                float foodPerDay = 0;
		public FoodHandler(BuildingHandler CurrentBuildingHandler)
		{
                    myBuildingHandler = CurrentBuildingHandler;
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
			throw new NotImplementedException();
		}
                
                public boolean canRemoveMore()
                {
                    return ((FarmerAmount - 1) > 0);
                }

   

    void update(GameTime MS) {
    }

    public int getFarmersAmount() {
        return FarmerAmount;
    }
}
