/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Buildings;

import Buildings.Building;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author noone
 */
class Blacksmith extends Building {

    public Blacksmith(String Name, String Description, Timer BuildTime, int Index, int BuildersRequired, String ForegroundImageFileName, String ForegroundImageDestroyedFileName)
    {
        super(Name,Description, BuildTime, Index, BuildersRequired, ForegroundImageFileName, ForegroundImageDestroyedFileName);
        
    }

    @Override
    public boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void onLock(TribalCampObject anObject)
    {

    }

    @Override
    void onUnlock(TribalCampObject anObject) {

    }
    
    @Override
    public Building Copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
