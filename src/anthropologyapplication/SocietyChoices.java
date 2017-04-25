/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class SocietyChoices
    {
            short CohesionPoints = 100;
            HashMap mySocialValueScores = new HashMap();
            short[] socialValueShortArray = new short[SocialValues.values().length];
            SocialValues[] socialValueArray = SocialValues.values();
            
            
            
            public SocietyChoices(ArrayList<SocialValues> myValues)
            {
                assert(socialValueArray.length%2 != 0) : "Improper length of social values table! (Did you make sure to add the opposite to the table?";
                int SocialValuePairs = (int)(0.5F*socialValueArray.length);
                for(int i = 0; i < socialValueArray.length; i++)
                {
                    if(myValues.contains(socialValueArray[i]))
                    {
                        socialValueShortArray[i] = 100;
                    } else {
                        socialValueShortArray[i] = -100;
                    }
                }
                assert(SocialValuePairs != 0) : "Not all social value pairs were assigned!";
            }
            
            private int getValue(SocialValues aSocial)
            {
                for(int i = 0; i < socialValueArray.length; i++)
                {
                    if(socialValueArray[i] == aSocial)
                        return i;
                }
                return -1;
            }
            
            public void adjustSocialCohesion(int amount)
            {
                    if(CohesionPoints + amount >= 100)
                    {
                        CohesionPoints = 100;
                        return;
                    } else if ((CohesionPoints + amount) <= 0)
                    {
                        CohesionPoints = 0;
                    } else {
                        CohesionPoints += amount;
                    }
            }

            public int calculateCohesionAdjustment(int Amount)
            {
                    return (int)Math.floor(2*Amount);
            }
            
            public void adjustCohesionByTerms(SocialValues[] mySelectedVals, short[] ChangeAmount) //Notice can never be negative
            {
                    for(int i = 0; i < mySelectedVals.length; i ++)
                    {
                        int indexValue = getValue(mySelectedVals[i]);
                        int oppositeIndexValue = getOppositeValue(indexValue);
                        int deltaValue = calculateCohesionAdjustment(ChangeAmount[i]);
                        assert(deltaValue > 0) : "Improper Delta value detected!";
                        if(socialValueShortArray[indexValue] > socialValueShortArray[oppositeIndexValue])//Player selected a positive pre-existing value, this promotes cohesion
                        {
                            if(ChangeAmount[i] > 0)
                            {
                                adjustSocialCohesion(deltaValue);
                            } else {
                                adjustSocialCohesion(-deltaValue);
                            }
                        }
                        else if(socialValueShortArray[indexValue] < socialValueShortArray[oppositeIndexValue])//Player selected a neg pre-existing value, this neg cohesion
                        {
                            if(ChangeAmount[i] < 0)
                            {
                                adjustSocialCohesion(deltaValue);
                            } else {
                                adjustSocialCohesion(-deltaValue);
                            }
                        } else {
                            
                        }
                        adjustSocialValue(indexValue, ChangeAmount[i]);
                        adjustSocialValue(oppositeIndexValue, (short)-ChangeAmount[i]);
                    }
            }
            
            public short[] getSocialValuesArrayCopy()
            {
                short[] retSocialValueArray = new short[SocialValues.values().length];
                System.arraycopy(socialValueArray, 0, retSocialValueArray, 0, socialValueArray.length);
                //Since array is an object it's modifiable by the one receiving it. We don't want that.
                return retSocialValueArray;
            }
                
            public boolean shouldDisintegrate()
            {
                    return (CohesionPoints == 0);
            }

            public short getCohesion()
            {
                    return CohesionPoints;
            }

        private int getOppositeValue(int indexValue) {
            if(indexValue%2 == 0)
            {
                return indexValue + 1;
            } else {
                return indexValue - 1;
            }
        }

    private void adjustSocialValue(int indexValue, short s) {//TODO: check for overflow stuff?
        if((socialValueShortArray[indexValue] + s) > 100)
        {
            socialValueShortArray[indexValue] = 100;
        }
        else if ((socialValueShortArray[indexValue] + s) < -100)
        {
            socialValueShortArray[indexValue] = -100;
        }
        else {
            socialValueShortArray[indexValue] += s;
        }
    }

   
   }
