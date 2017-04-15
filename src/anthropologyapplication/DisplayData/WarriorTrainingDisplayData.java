/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.DisplayData;

import anthropologyapplication.Warriors.Warrior;

/**
 *
 * @author Duke
 */
public class WarriorTrainingDisplayData {

    private final Warrior myData;
    public WarriorTrainingDisplayData(Warrior myData)
    {
        this.myData = myData;
    }

    public String getDescription() {
       return myData.getDescription();
    }

    public String getTotalBuildTime() {
       return myData.getTotalBuildTime().toString();
    }

    public String getName() {
        return myData.getName();
    }

    public String getStrength() {
        return "" + myData.getStrength();
    }
    
    
    public String getCurrentBuildTime() {
        return myData.getCurrentBuildTime();
    }
    
    public double getCompletionPercentage()
    {
        return myData.getCompletionPercentage();
    }
}
