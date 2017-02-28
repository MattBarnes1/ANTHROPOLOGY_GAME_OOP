using System;
using System.Collections.Generic;
using System.Text;

namespace AnthropologyClient.AnthropologyGame
{
	public abstract class Building
	{
		readonly int Tier;
		readonly string BuilidingName;
		String Description;
		bool canBuildMultiples;

		public String getBuildingName()
		{
			throw new NotImplementedException();
		}

		public int getBuildingTier()
		{
			throw new NotImplementedException();
		}

		protected Building(string Name, string Description, int Tier)
		{
			throw new NotImplementedException();
		}

		public bool canBuildMultiples()
		{
			throw new NotImplementedException();
		}

		public void getDescription()
		{
			throw new NotImplementedException();
		}
	}
}
