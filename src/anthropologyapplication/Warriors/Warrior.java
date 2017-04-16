/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Warriors;

import anthropologyapplication.GameTime;
import TradeGoods.ProductionHandler;
import anthropologyapplication.Time;
import TradeGoods.TradeGood;
import anthropologyapplication.Timer;
import anthropologyapplication.internalLockers.internalWarriorLocker;

/**
 *
 * @author Duke
 */
public class Warrior {
    protected final Timer BuildTime;
    protected Timer BuildTimeCountDown;
    protected String[] itemsNeededForProduction;
    protected int[] quantityofItemsNeededForProduction;
    protected int Strength = 0;
    protected String Name;
    protected String Description;
    protected boolean Available = false;
    public Warrior(String Name, String Description,  Timer TrainingTimeMS, int Strength)
    {
        this.Name = Name;
        this.Description = Description;
        this.Strength = Strength;
        
        BuildTime = TrainingTimeMS;
        BuildTimeCountDown = BuildTime;
    }


    public double getStrength() {
        return this.Strength; //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isDoneBuilding() {
        return (BuildTimeCountDown.EqualTo(new Timer(0,0,0,0)));
    }

    boolean hasBeenBuilt = false;
    public void update(GameTime MS) {
        if(!hasBeenBuilt)
        {
           BuildTimeCountDown = BuildTimeCountDown.subtract(MS.getElapsedTime());
           hasBeenBuilt = isDoneBuilding();
        }
    }

    public String getName() {
        return Name; //To change body of generated methods, choose Tools | Templates.
    }

    public boolean checkIfCanBuild(ProductionHandler myProductionHandler)
    {
        for(int i = 0; i < itemsNeededForProduction.length; i++)
        {
           String ItemName = itemsNeededForProduction[i];
           int requiredAmount = quantityofItemsNeededForProduction[i];
           int ItemAmount = myProductionHandler.getAmountByString(itemsNeededForProduction[i]);
           if(requiredAmount > ItemAmount)
           {
               return false;
           }
        }
        return true;
    }

    public String getDescription() {
        return Description;
    }

    public Timer getTotalBuildTime() {
        return BuildTime;
    }

    public String getCurrentBuildTime() {
        return BuildTime.toString();
    }

    public double getCompletionPercentage() {
        return BuildTimeCountDown.dividedBy(BuildTime);
    }

    public boolean isUnlocked() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
