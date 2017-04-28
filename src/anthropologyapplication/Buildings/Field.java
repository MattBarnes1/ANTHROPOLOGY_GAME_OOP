/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.Buildings.Building;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.PopulationHandler;
import anthropologyapplication.Time;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author Duke
 */
public class Field extends Building {
    int RequiredAmountOfFarmers = 2;
    float yield = PopulationHandler.getWarriorsFoodConsumption()*2;
    float yieldMS = (PopulationHandler.getWarriorsFoodConsumption()*2)/86400000F;
    public Field(String Name, String Description, Timer BuildTime, int Index, int amountOfBuildersRequired,  int TerritorySize,String FileNameForegroundImage, String FileNameForegroundDestroyedImage) {
        super(Name, Description, BuildTime, Index, amountOfBuildersRequired, TerritorySize,FileNameForegroundImage, FileNameForegroundDestroyedImage);
    }

    private Field(Field aThis) {
        super(aThis.getBuildingName(), aThis.getDescription(), aThis.getBuildTime(), aThis.getIndex(), aThis.getRequiredBuildersAmount(), aThis.getTerritorySize(),  aThis.getForeGroundImageName(),  aThis.getForeGroundDestroyedImageName());
    
    }

    @Override
    public boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile) {
        return (aTile.isLand() && aTile.isTerritoryOf(myObject) && !aTile.hasBuilding());
    }

    @Override
    public Building Copy() {
        return new Field(this);
    }

    @Override
    public void removeFromMapTile() {
        super.getBuildingTile().clearBuilding();
    }

    public int getRequiredNumberOfFarmers() {
        return RequiredAmountOfFarmers;
    }

    public float getYieldPerMS()
    {
        return yieldMS;
    }
    
    public float getDailyYield() {
       return yield;
    }
    
}
