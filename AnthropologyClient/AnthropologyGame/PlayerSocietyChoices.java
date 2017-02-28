using System;
using System.Collections.Generic;
using System.Text;

namespace AnthropologyClient.AnthropologyGame
{
	public class PlayerSocietyChoices
	{
		short CohesionPoints;
		SocialValues[] mySocialValues;

		public PlayerSocietyChoices(SocialValues[] myValues)
		{
			throw new NotImplementedException();
		}

		public void adjustSocialCohesion(int amount)
		{
			throw new NotImplementedException();
		}

		public void adjustCohesionByTerms(SocialValues[] mySelectedVals)
		{
			throw new NotImplementedException();
		}

		public bool shouldDisintegrate()
		{
			throw new NotImplementedException();
		}

		public short getCohesion()
		{
			throw new NotImplementedException();
		}
	}
}
