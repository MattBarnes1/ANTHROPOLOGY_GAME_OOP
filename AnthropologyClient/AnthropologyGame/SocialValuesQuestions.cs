using System;
using System.Collections.Generic;
using System.Text;

namespace AnthropologyClient.AnthropologyGame
{
	public abstract class SocialValuesQuestions
	{
		static ArrayList<SocialValues> myValues = new ArrayList<SocialValues>();
		SocialValuesQuestion NextQuestion;

		public SocialValuesQuestions()
		{
			throw new NotImplementedException();
		}

		public void addSocialValue(SocialValues aValue)
		{
			throw new NotImplementedException();
		}

		public abstract String getQuestionBody();

		public abstract String[] getChoices();

		public SocialValues[] getSocialValues()
		{
			throw new NotImplementedException();
		}
	}
}
