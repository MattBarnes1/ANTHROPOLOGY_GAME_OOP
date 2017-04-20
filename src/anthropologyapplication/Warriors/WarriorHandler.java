/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Warriors;
import anthropologyapplication.Warriors.Clubmen;
import TradeGoods.ProductionHandler;
import anthropologyapplication.DisplayData.WarriorTrainingDisplayData;
import anthropologyapplication.GameTime;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.Warriors.Warrior;
import anthropologyapplication.internalLockers.*;
import java.util.ArrayList;
import java.util.Iterator;



/**
 *
 * @author Duke
 */
public class WarriorHandler {
    ProductionHandler myProductionHandler;
    internalWarriorLocker[] warriorList = {
        new internalWarriorLocker(new Clubmen("Clubmen", "Clubbers",new Timer(0,0,0,1),5), true),
        new internalWarriorLocker(new Spearmen("Spearmen", "Spearers", new Timer(0,0,0,1),15), false)
        };
//(String Name, String Description,  Timer TrainingTimeMS, int Strength)
    
    ArrayList<Warrior> myTrainedWarriors = new ArrayList<Warrior>();
    ArrayList<Warrior> myWarriorsInTraining = new ArrayList<Warrior>();

    TribalCampObject myObject;
    

    public WarriorHandler(TribalCampObject myCamp) {
        myObject = myCamp;
    }

    public int getFightingStrength()
    {
        double retVal = 0;
        Iterator<Warrior> myTrainedWarriorsIterator = myTrainedWarriors.iterator();
        Iterator<Warrior> myTrainingWarriorsIterator = myWarriorsInTraining.iterator();
        
        while(myTrainedWarriorsIterator.hasNext())
        {
            Warrior myWarrior = myTrainedWarriorsIterator.next();
            retVal += myWarrior.getStrength();
        }
        
        while(myTrainingWarriorsIterator.hasNext())
        {
            Warrior myWarrior = myTrainingWarriorsIterator.next();
            retVal += (myWarrior.getStrength()*0.5);
        }
        
        return (int)Math.floor(retVal);
    }
    
    
    
    
    public void update(GameTime MS) {
        Iterator<Warrior> myIterator = myWarriorsInTraining.iterator();
	while(myIterator.hasNext())
	{
		Warrior aWarrior = myIterator.next();
		aWarrior.update(MS);
		if(aWarrior.isDoneBuilding())
		{
			myTrainedWarriors.add(aWarrior);
			myIterator.remove();
		}
	}
        myIterator = myTrainedWarriors.iterator();
    }

    public void addWarrior(String myWarrior) {
       
    }

    public int getWarriorsAmountByName(String Name) {
	int retVal = 0;
	Iterator<Warrior> myIterator = myWarriorsInTraining.iterator();
	while(myIterator.hasNext())
	{
		Warrior myWarrior = myIterator.next();
		if(myWarrior.getName().compareTo(Name) == 0)
		{
			retVal++;
		}
	}
        return retVal;
    }

    public int getWarriorsAmount() {
        return myTrainedWarriors.size();
    }

    public void removeWarriorsFromTraining(String myName) {
	Iterator<Warrior> myIterator = myWarriorsInTraining.iterator();
	boolean wasDeleted = false;
	while(myIterator.hasNext())
	{
		Warrior myWarrior = myIterator.next();
		if(myWarrior.getName().compareTo(myName) == 0)
		{
		
			wasDeleted = true;
			myIterator.remove();
		}
	}
	if(wasDeleted)
	{
		return;
	} else {
		//Report error
	}
    }
    
    public void forceRemoveWarriors(String myName) {
	Iterator<Warrior> myIterator = myTrainedWarriors.iterator();
	boolean wasDeleted = false;
	while(myIterator.hasNext())
	{
		Warrior myWarrior = myIterator.next();
		if(myWarrior.getName().compareTo(myName) == 0)
		{
			wasDeleted = true;
			myIterator.remove();
		}
	}
	if(wasDeleted)
	{
		return;
	} else {
		//Report error
	}
    }

    public Iterator<WarriorTrainingDisplayData> getWarriorsAvailable() {
        ArrayList<WarriorTrainingDisplayData> myList = new ArrayList<>();
        for(internalWarriorLocker A : warriorList)
        {
            //if(A.isAvailable())
            //{
                myList.add(new WarriorTrainingDisplayData(A.getWarrior()));
            //}
        }
        return myList.iterator();
    }

    public void forceAddWarrior(String myWarrior) {
        for(int i = 0; i < warriorList.length; i++)
        {
           if(warriorList[i].getName().compareTo(myWarrior)==0)
           {
                    myTrainedWarriors.add(warriorList[i].Copy());
           }
        }
    }

    public void addWarriorToTraining(String myWarrior) { 
        for(int i = 0; i < warriorList.length; i++)
        {
           if(warriorList[i].getName().compareTo(myWarrior)==0)
           {
                if(warriorList[i].checkIfCanBuild(myProductionHandler))
                {
                    myWarriorsInTraining.add(warriorList[i].Copy());
                } else {
                    Error = "Not enough trade goods for this to be built!";
                }
           }
        }
    }

    public void removeWarriorFromTraining(String myWarrior) { 
        for(int i = 0; i < warriorList.length; i++)
        {
           if(warriorList[i].getName().compareTo(myWarrior)==0)
           {
                myWarriorsInTraining.remove(warriorList[i].Copy());
           }
        }
    }

    String Error = null;
    
    public boolean hasError() {
        return Error != null;
    }

    public String getError() {
        String myRet = Error;
        Error = null;
        return myRet;
    }

    public void unlockWarrior(Class<Spearmen> aClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void lockWarrior(Class<Spearmen> aClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
