/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

/**
 *
 * @author Duke
 */
public class WarriorHandler {
    ProductionHandler myProductionHandler;
    internalWarriorLocker[] warriorList = {};

    ArrayList<Warrior> myTrainedWarriors = new ArrayList<Warrior>();
    ArrayList<Warrior> myWarriorsInTraining = new ArrayList<Warrior>();


    public WarriorHandler(ProductionHandler aProductionHandler) {
        myProductionHandler = aProductionHandler;
    }

    
    
    public void update(GameTime MS) {
        Iterator<Warrior> myIterator = myWarriorsInTraining.Iterator();
	while(myIterator.hasNext())
	{
		myIterator.Next();
		myIterator.update(MS);
		if(myIterator.isDoneBuilding())
		{
			myTrainedWarrior.add(myIterator);
			myIterator.remove();
		}
	}
    }

    public void addWarriors(Warrior myType) {
	if(myType.checkIfCanBuild(aProduction))
	{
		myWarriorsInTraining.add(myType);
	}

        TrainedWarriors++;//To change body of generated methods, choose Tools | Templates.
    }

    public int getWarriorsAmountByType(String Name) {
	int retVal = 0;
	Iterator<Warrior> myIterator = myWarriorsInTraining.Iterator();
	while(myIterator.hasNext())
	{
		myIterator.Next();
		if(myIterator.getWarriorName().compareTo(myName) == 0)
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
	Iterator<Warrior> myIterator = myWarriorsInTraining.Iterator();
	boolean wasDeleted = false;
	while(myIterator.hasNext())
	{
		myIterator.Next();
		if(myIterator.getWarriorName().compareTo(myName) == 0)
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
    
    public void removeWarriors(String myName) {
	Iterator<Warrior> myIterator = myTrainedWarriors.Iterator();
	boolean wasDeleted = false;
	while(myIterator.hasNext())
	{
		myIterator.Next();
		if(myIterator.getWarriorName().compareTo(myName) == 0)
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
}
