/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.SocialValues;

/**
 *
 * @author Duke
 */
public class SocialChoice {
    private SocialChoice Next = null;
    private String ScenarioString;
    private String[] ChoiceStrings;
    private SocialValues[] SocialValuesForChoice;
    private String[] ChoiceExplanation;
    public SocialChoice(String Scenario, String Choice1, String Choice2, String Choice1Explanation, String Choice2Explanation, SocialValues withChoice1, SocialValues withChoice2)
    {
        ChoiceStrings = new String[] { Choice1, Choice2 };
        ScenarioString = Scenario;
        SocialValuesForChoice = new SocialValues[]{withChoice1, withChoice2};
        ChoiceExplanation = new String[] { Choice1Explanation, Choice2Explanation};
    }
    
    public void setNext(SocialChoice aChoice)
    {
        Next = aChoice;
    }
    
    public SocialChoice getNext()
    {
        return Next;
    }

    String getChoiceString(int i) {
      return ChoiceStrings[i];
    }

    SocialValues getChoiceSocialValue(int i) {
        return SocialValuesForChoice[i];
    }

    String getScenarioString() {
        return ScenarioString;
    }   

    String getDefinition(int i) {
       return ChoiceExplanation[i];
    }
    
}
