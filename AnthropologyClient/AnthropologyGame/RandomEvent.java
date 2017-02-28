using System;
using System.Collections.Generic;
using System.Text;

namespace AnthropologyClient.AnthropologyGame
{
	public abstract class RandomEvent
	{
		RandomEvent[] aNextRandomEvent;
		ArrayList<SocialValues> CurrentSocialVals;
		RandomEvent Previous;

		public RandomEvent(RandomEvent[] MyLowerEvents)
		{
			throw new NotImplementedException();
		}

		public RandomEvent()
		{
			throw new NotImplementedException();
		}

		public abstract String getTextBody();

		public abstract String[] getChoices();

		public abstract void sendChoice(String myChoiceString);

		public abstract bool isFinished();

		public abstract String[] getFinishedChoiceData();
	}
}
