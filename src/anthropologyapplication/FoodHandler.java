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
			FarmerAmount--;
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

   

    void update(GameTime MS) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
