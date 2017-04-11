/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.DisplayData;

import anthropologyapplication.Building;

/**
 *
 * @author Duke
 */
public class BuildingConstructionDisplayData {
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
        return aBuilding.getBuildTime().getTimeString();
    }

    
}
