using System;
using System.Collections.Generic;
using System.Text;

namespace AnthropologyClient.AnthropologyGame
{
	public class PlayerCampObject
	{
		private ProductionHandler myProductionHandler;
		BuildingHandler myBuildingHandler;
		PlayerSocietyChoices mySocietyChoices;
		int TotalAvailableCitizens = 10;
		HorticulturalHandler myAgriculturalHandler;
		int availableFood = 100;

		public PlayerCampObject(PlayerSocietyChoice mySocietyChoices)
		{
			throw new NotImplementedException();
		}

		public PlayerSocietyChoices getPlayerSocietyChoices()
		{
			throw new NotImplementedException();
		}

		public void updateNewYearData()
		{
			throw new NotImplementedException();
		}

		public void setBirthRate(float PercentageOfBase)
		{
			throw new NotImplementedException();
		}

		public void addCitizensToProduction(int amount)
		{
			throw new NotImplementedException();
		}

		public void removeCitizensFromProduction(int amount)
		{
			throw new NotImplementedException();
		}

		public void addCitizensToBuilding(int Amount)
		{
			throw new NotImplementedException();
		}

		public void removeCitizensFromBuilding(int Amount)
		{
			throw new NotImplementedException();
		}

		public void updateDay()
		{
			throw new NotImplementedException();
		}

		public void doFoodUpdate()
		{
			throw new NotImplementedException();
		}
	}
}
