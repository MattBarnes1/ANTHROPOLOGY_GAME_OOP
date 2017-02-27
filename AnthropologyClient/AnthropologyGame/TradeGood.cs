using System;
using System.Collections.Generic;
using System.Text;

namespace AnthropologyClient.AnthropologyGame
{
	public class TradeGood
	{
		int TotalAmount;

		public virtual void incrementAmount()
		{
			throw new NotImplementedException();
		}

		public virtual void decrementAmount()
		{
			throw new NotImplementedException();
		}

		public TradeGood(String Name, int Tier, int startingTradeGood, float productionTime, int baseSellVal)
		{
			throw new NotImplementedException();
		}

		public virtual int getTierLevel()
		{
			throw new NotImplementedException();
		}

		public TradeGood(String Name, int Tier, int startingTradeGood, int[] TradeGoodsForProduction, float ProductionTime, TradeGoods[] GoodsNeedForProduction, int baseSellVal)
		{
			throw new NotImplementedException();
		}
	}
}
