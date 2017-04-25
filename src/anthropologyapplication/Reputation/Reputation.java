/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Reputation;

import anthropologyapplication.SocialValues;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author noone
 */
public class Reputation {
    private TribalCampObject whoTheyAre;
   // private int whatTheyThinkOfMe;
    private int whatTheirReputationIs;

     public Reputation(TribalCampObject whoTheyAre, TribalCampObject whoIAm) {
        this.whoTheyAre = whoTheyAre;
        this.whatTheirReputationIs = determineIntitalOpinon(whoTheyAre, whoIAm);
    }

    public int getReputation() {
        return whatTheirReputationIs;
    }
    
    private int determineIntitalOpinon(TribalCampObject whoTheyAre, TribalCampObject whoIAm)
    {
        int Positive = 0;
        int Negative = 0;
        short[] theirValues = whoTheyAre.getSocietyChoices().getSocialValuesArrayCopy();
        short[] myValues = whoIAm.getSocietyChoices().getSocialValuesArrayCopy();
        for(int i = 0; i < theirValues.length; i++)
        {
            if(theirValues[i] > 0 && myValues[i] > 0)
            {
                Positive++;
            }
            else if(theirValues[i] <= 0 && myValues[i] <= 0)
            {
                Positive++;
            } else {
                Negative--;
            }
        }
        return Positive + Negative;
    }
    
     
    public TribalCampObject getWhoTheyAre() {
        return whoTheyAre;
    }

    
        
        
  
}
