/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.DisplayData;

import anthropologyapplication.Buildings.Building;
import anthropologyapplication.MainGameCode;

/**
 *
 * @author Duke
 */
public class BuildingConstructionDisplayData implements DisplayData {
    private Building aBuilding;
    
    public BuildingConstructionDisplayData(Building aBuilding)
    {
     this.aBuilding = aBuilding;   
    }
    
    public String getName()
    {
        return aBuilding.getBuildingName();
    }
    
    public String getTimeToCompleteString()
    {
        return aBuilding.getBuildTime().toString();
    }

    public String getDescription() {
        return aBuilding.getDescription();
    }

    public String getTotalBuildTime() {
        return "" + aBuilding.getTotalBuildTime();
    }
    
    public double getCompletionPercentage()
    {
        return aBuilding.getCompletionPercentage();
    }
    
    

    @Override
    public void acceptRemoverAsVisitor(MainGameCode myGameCode) {
        myGameCode.remove(aBuilding);
    }

    @Override
    public boolean shouldBeRemoved() {
        return true;
    }

    @Override
    public String getToolTipString() {
        return getDescription() + getTimeToCompleteString();
    }
    
}
