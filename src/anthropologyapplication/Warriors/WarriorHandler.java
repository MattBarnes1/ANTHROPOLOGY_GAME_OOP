/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Warriors;
import anthropologyapplication.Warriors.Clubmen;
import anthropologyapplication.TradeGoods.ProductionHandler;
import anthropologyapplication.DisplayData.WarriorTrainingDisplayData;
import anthropologyapplication.GameTime;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.Warriors.Warrior;
import anthropologyapplication.internalLockers.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;



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

    public WarriorTrainingDisplayData addWarriorToTraining(String myWarrior) { 
        for(int i = 0; i < warriorList.length; i++)
        {
           if(warriorList[i].getName().compareTo(myWarrior)==0)
           {
                if(warriorList[i].getWarrior().checkIfCanBuild(myProductionHandler))
                {
                    Warrior aCopy = warriorList[i].Copy();
                    myWarriorsInTraining.add(aCopy);
                    return new WarriorTrainingDisplayData(aCopy);
                } else {
                    Error = "Not enough trade goods for this to be built!";
                }
           }
        }
        return null;
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

    public void unlockWarrior(Class<? extends Warrior> aClass) {
        for(int i = 0; i < warriorList.length; i++)
        {
            if(warriorList[i].getWarrior().getClass() == aClass)
            {
                warriorList[i].unlock();
                hasChanged = true;
            }
        }
    }

    public void lockWarrior(Class<? extends Warrior> aClass) {
    for(int i = 0; i < warriorList.length; i++)
        {
            if(warriorList[i].getWarrior().getClass() == aClass)
            {
                warriorList[i].lock();
                hasChanged = true;
            }
        }
    }

    public void removeWarriorFromTraining(Warrior ProgressData) {
        myWarriorsInTraining.remove(ProgressData);
    }

    Random myRandom = new Random();
    
    
    
    public void killRandomWarrior() {
        int choice = (myRandom.nextInt(2));
        if(!myWarriorsInTraining.isEmpty() && myTrainedWarriors.isEmpty())
        {
                Warrior CurrentlyInTraining = myWarriorsInTraining.get(myWarriorsInTraining.size()-1);
                CurrentlyInTraining.cancelBuild();
                myWarriorsInTraining.remove(myWarriorsInTraining.size()-1);
        }
        else if (myWarriorsInTraining.isEmpty() && !myTrainedWarriors.isEmpty())
        {
                Warrior CurrentlyInTraining = myWarriorsInTraining.get(myWarriorsInTraining.size()-1);
                myTrainedWarriors.remove(myTrainedWarriors.size()-1);                 
        }
        else
        {
            if(choice == 0)
            {
                myWarriorsInTraining.remove(myWarriorsInTraining.size()-1);//
            } else {
                myTrainedWarriors.remove(myTrainedWarriors.size()-1);                
            }
        }
    }

    boolean hasChanged = false;
    public boolean hasChanged() {
        if(hasChanged == true)
        {
            hasChanged = false;
            return true;
        } else {
            return false;
        }
    }
}
