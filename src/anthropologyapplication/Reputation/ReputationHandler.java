/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Reputation;

import anthropologyapplication.TribalCampObject;

/**
 *
 * @author noone
 */
public class ReputationHandler {
    private final int Adjustment_OnAttack_Goal_Kill = -10;
    private final int Adjustment_OnAttack_Goal_Steal = -5;
    private final int Adjustment_OnAttack_Goal_Trade = 10;
    Reputation[] myReputationList = {};
    public ReputationHandler(TribalCampObject myObject) throws Exception
    {
        ReputationFactory.addNewTribe(myObject);
    }
    
    public Reputation getReputationByTribe(TribalCampObject myObject)
    {
        for(int i = 0 ; i < myReputationList.length; i++)
        {
            if(myReputationList[i].getWhoTheyAre() == myObject)
            {
                return myReputationList[i];
            }
        }
        return null;
    }
    
}
