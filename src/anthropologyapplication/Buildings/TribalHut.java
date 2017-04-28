/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Buildings.Building;
import anthropologyapplication.Time;
import anthropologyapplication.Timer;
import anthropologyapplication.TradeGoods.ResourceArray;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author Duke
 */
public class TribalHut extends Building {

    public TribalHut(String Name, String Description, Timer BuildTime, int Index, int amountOfBuildersRequired, int TerritorySize, String FileNameForegroundImage, String FileNameForegroundDestroyedImage, ResourceArray myResourcesNeed) {
        super(Name, Description, BuildTime, Index, amountOfBuildersRequired,  TerritorySize, FileNameForegroundImage, FileNameForegroundDestroyedImage,myResourcesNeed);
        
    }

    
    
    private TribalHut(TribalHut aThis) {
        super(aThis.getBuildingName(), aThis.getDescription(), aThis.getBuildTime(), aThis.getIndex(), aThis.getRequiredBuildersAmount(), aThis.getTerritorySize(), aThis.getForeGroundImageName(),  aThis.getForeGroundDestroyedImageName(), aThis.getResourceArray());
    }

    @Override
    public boolean canBuildMultiples()
    {
        return false;
    }
    
    
    
    
    @Override
    public boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile) {
        return (aTile.isLand() && aTile.isTerritoryOf(myObject) && aTile.hasBuilding() && hasEnoughResources(myObject));
    }

    @Override
    public Building Copy() {
        return new TribalHut(this);
    }
    
}
