/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Buildings;

import Buildings.Building;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Time;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author Duke
 */
public class Field extends Building {
    int RequiredAmountOfFarmers = 2;
    float yield = 2;
    public Field(String Name, String Description, Timer BuildTime, int Index, int amountOfBuildersRequired,  int TerritorySize,String FileNameForegroundImage, String FileNameForegroundDestroyedImage) {
        super(Name, Description, BuildTime, Index, amountOfBuildersRequired, TerritorySize,FileNameForegroundImage, FileNameForegroundDestroyedImage);
    }

    private Field(Field aThis) {
        super(aThis.getBuildingName(), aThis.getDescription(), aThis.getBuildTime(), aThis.getIndex(), aThis.getTerritorySize(), aThis.getRequiredBuildersAmount(), aThis.getForeGroundImageName(),  aThis.getForeGroundDestroyedImageName());
    
    }

    @Override
    public boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile) {
        return (aTile.isLand() && aTile.isTerritoryOf(myObject));
    }

    @Override
    public Building Copy() {
        return new Field(this);
    }

    @Override
    public void removeFromMapTile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getRequiredNumberOfFarmers() {
        return RequiredAmountOfFarmers;
    }

    public float getYield() {
       return yield;
    }
    
}
