/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Buildings;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author noone
 */
public class Smelterer extends Building {

    public Smelterer(String Name, String Description, Timer BuildTime, int Index, int BuildersRequired, int TerritorySize, String ForegroundImageFileName, String ForegroundImageDestroyedFileName)
    {
        super(Name,Description, BuildTime, Index, BuildersRequired,  TerritorySize, ForegroundImageFileName, ForegroundImageDestroyedFileName);
        
    }

    private Smelterer(Smelterer aThis) {
        super(aThis.getBuildingName(), aThis.getDescription(), aThis.getBuildTime(), aThis.getIndex(), aThis.getRequiredBuildersAmount(), aThis.getTerritorySize(), aThis.getForeGroundImageName(),  aThis.getForeGroundDestroyedImageName());
    }

    @Override
    public boolean canBuildOnTile(TribalCampObject myObject, MapTile aTile) {
        
        return (aTile.isLand() && aTile.isTerritoryOf(myObject) && !aTile.hasBuilding());
    }

    @Override
    void onUnlock(TribalCampObject anObject) {
        anObject.getProductionHandler().unlockTradeGood(anthropologyapplication.TradeGoods.Unsmelted_Copper.class);
        anObject.getProductionHandler().unlockTradeGood(anthropologyapplication.TradeGoods.Smelted_Copper.class);
    }

    @Override
    void onLock(TribalCampObject anObject) {
        anObject.getProductionHandler().lockTradeGood(anthropologyapplication.TradeGoods.Unsmelted_Copper.class);
        anObject.getProductionHandler().lockTradeGood(anthropologyapplication.TradeGoods.Smelted_Copper.class);
    }

    
    
    @Override
    public Building Copy() {
       return new Smelterer(this);
    }
    
}
