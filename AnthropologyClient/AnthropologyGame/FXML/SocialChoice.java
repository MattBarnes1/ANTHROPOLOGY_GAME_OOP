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
    private String ChoiceString1;
    private String ChoiceString2;
    private SocialValues withChoice1;
    private SocialValues withChoice2;
    public SocialChoice(String Scenario, String Choice1, String Choice2, SocialValues withChoice1,SocialValues withChoice2)
    {
        ScenarioString = Scenario;
        ChoiceString1 = Choice1;
        ChoiceString2 = Choice2;
        this.withChoice1 = withChoice1;
        this.withChoice2 = withChoice2;
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
        if(i == 1)
        {
            return ChoiceString1;
        } 
        else if (i == 2)
        {
            return ChoiceString1;
        }
        else {
            return "";
        }
    }

    SocialValues getChoiceSocialValue(int i) {
        if(i == 1)
        {
            return withChoice1;
        } 
        else if (i == 2)
        {
            return withChoice2;
        }
        else {
            return null;
        }
    }

    String getScenarioString() {
        return ScenarioString;
    }   
    
}
