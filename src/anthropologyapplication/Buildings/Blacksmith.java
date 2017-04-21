/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.Buildings.Building;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.Warriors.Spearmen;

/**
 *
 * @author noone
 */
public class Blacksmith extends Building {

    public Blacksmith(String Name, String Description, Timer BuildTime, int Index, int BuildersRequired, int TerritorySize, String ForegroundImageFileName, String ForegroundImageDestroyedFileName)
    {
        super(Name,Description, BuildTime, Index, BuildersRequired, TerritorySize,ForegroundImageFileName, ForegroundImageDestroyedFileName);
        
    }

    private Blacksmith(Blacksmith aThis) {
        super(aThis.getBuildingName(),aThis.getDescription(), aThis.BuildTimeToBuild, aThis.getIndex(), aThis.getRequiredBuildersAmount(), aThis.getTerritorySize(), aThis.getForeGroundImageName(), aThis.getForeGroundDestroyedImageName());
    }

    @Override
    public boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile) {
        return aTile.isLand() && aTile.isTerritoryOf(myObject);
    }

    @Override
    void onLock(TribalCampObject anObject)
    {
        anObject.getWarriorHandler().lockWarrior(Spearmen.class);
        
    }

    @Override
    void onUnlock(TribalCampObject anObject) {    
        anObject.getWarriorHandler().unlockWarrior(Spearmen.class);
    }
    
    @Override
    public Building Copy() {
        return new Blacksmith(this);
    }


    
}
