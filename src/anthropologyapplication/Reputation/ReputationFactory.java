/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Reputation;

import anthropologyapplication.TribalCampObject;
import java.util.ArrayList;
import java.util.ListIterator;
/**
 *
 * @author noone
 */
public class ReputationFactory {
    private static ArrayList<TribalCampObject> myReputation = new ArrayList<TribalCampObject>();
    private static ArrayList<ReputationHandler> myHandlersToUpdateAsAdded = new ArrayList<>();
    public static void addNewTribe(TribalCampObject myNewObject) throws Exception
    {
        if(!myReputation.contains(myNewObject))
        {
            System.out.println("Adding a new Object: " + myNewObject.hashCode());
            myReputation.add(myNewObject);
            updateReputationHandlersOnAdd(myNewObject);
            System.out.println("Finished adding a new Object: " + myNewObject.hashCode());
            
        } else {
            throw new Exception("Attempted to add a Tribal Object to Reputation twice!");
        }
    }
    
    public static void removeTribe(TribalCampObject myRemovableObject)
    {
        if(myReputation.remove(myRemovableObject))
            updateReputationHandlersOnRemove(myRemovableObject);
    }
    
    

    private static void updateReputationHandlersOnAdd(TribalCampObject myNewObject) {
        ListIterator<TribalCampObject> myTribeWhosBeingModified = myReputation.listIterator();
        while(myTribeWhosBeingModified.hasNext())
        {
           TribalCampObject myModifyingTribe = myTribeWhosBeingModified.next();
           myTribeWhosBeingModified.remove(); //Temporarily Removes it;
           for(int i = 0; i < myReputation.size(); i++)
           {
               
                Reputation[] newReputations = new Reputation[myReputation.size()];
                ReputationHandler myHandler = myModifyingTribe.getReputationHandler();
                System.out.println("Handler" + myHandler );
                Reputation[] oldReputations = myHandler.myReputationList;
                if(oldReputations != null)
                {
                    //System.out.println("Old Reputation: " + toArrayString(oldReputations));
                    System.arraycopy(oldReputations, 0, newReputations, 0, oldReputations.length);
                    // System.out.println("New Reputation before add: " + toArrayString(newReputations));
                    newReputations[myReputation.size()-1] = new Reputation(myModifyingTribe, myNewObject);
                    //System.out.println("New Reputation after add: " + toArrayString(newReputations));
                    myHandler.myReputationList = newReputations;
                    //System.out.println("Reputation set to: " + toArrayString(myHandler.myReputationList));
                } else {
                    newReputations[myReputation.size()-1] = new Reputation(myModifyingTribe, myNewObject);
                    //System.out.println("Reputation set to: " + toArrayString(newReputations));
                    myHandler.myReputationList = newReputations;
                }
               
           }
           myTribeWhosBeingModified.add(myModifyingTribe);//Position is preserved!
           int debug = 0;
        }
        
    }
    
    private static void updateReputationHandlersOnRemove(TribalCampObject myRemovedObject) {
        ListIterator<TribalCampObject> myTribeWhosBeingModified = myReputation.listIterator();
        while(myTribeWhosBeingModified.hasNext())
        {
            TribalCampObject myModifyingTribe = myTribeWhosBeingModified.next();
            Reputation[] newReputations = new Reputation[myReputation.size()];
            ReputationHandler myHandler = myModifyingTribe.getReputationHandler();
            Reputation[] oldReputations = myHandler.myReputationList;
            boolean found = false;
            for(int i = 0; i < oldReputations.length; i++)
            {
                if(oldReputations[i].getWhoTheyAre() == myRemovedObject)
                {
                    found = true;
                    oldReputations[i] = null;
                }
                else if (found)
                { //swap until null is at end
                    if(i - 1 >= 0)
                    {
                        oldReputations[i-1] = oldReputations[i];
                        oldReputations[i] = null;
                    }
                }
            }
            System.arraycopy(oldReputations, 0, newReputations, 0, oldReputations.length-1); //discard null
            myHandler.myReputationList = newReputations;
        }
    }

    private static String toArrayString(Reputation[] oldReputations) {
        if(oldReputations == null)
        {
            return "NULL";
        }
        String myString = "";
        for(int i = 0; i < oldReputations.length; i++)
        {
            myString += (oldReputations[i].toString() + "\n");
        }
        return myString;
    }

   

}
