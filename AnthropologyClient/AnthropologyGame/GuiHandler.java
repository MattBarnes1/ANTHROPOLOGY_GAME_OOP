imports java.javafx;

namespace AnthropologyClient.AnthropologyGame
{
	public class GuiHandler
	{
		private static GuiHandler myGui = null;
		
		public static GuiHandler getGui()
		{
			if(GuiHandler == null)
			{
				throw new Exception("Gui handler undefined!");
			} else {
				return myGui;
			}
		}
		
		String choiceBody;
		Stage mainDisplayStage;
		Scene mainScreen;
		Scene loadGameScreen;
		Scene displayInGameChoice;
		Scene displayInGameChoiceExplanation;
		
		public GuiHandler(Stage primaryStage, Scene mainScreen, Scene loadScreen, Scene saveScreen)
		{
			mainDisplayStage = primaryStage;
			
			displayMainMenuGUI();
		}

		public void displayMainMenuGUI()
		{
			
		}

		public void displaySocietyChoiceSelectionGUI(String Body, String[] Choices)
		{
			throw new NotImplementedException();
		}

		public void displayLoadGameDisplay()
		{
			throw new NotImplementedException();
		}

		public void displayInGameChoiceOption(String Body, String[] Choices)
		{
			throw new NotImplementedException();
		}

		public String getCheckInGamePlayerChoice(String Body, String[] Choices)
		{
			throw new NotImplementedException();
		}

		public void UpdateChoiceField(String Body, String myChoices)
		{
			throw new NotImplementedException();
		}

		public void explainInGameChoiceProblems(String[] inGameChoices)
		{
			throw new NotImplementedException();
		}
	}
}
