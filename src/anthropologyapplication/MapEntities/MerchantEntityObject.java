/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.MapEntities;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.TradeGoods.TradeGood;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.MapEntityHandler.MapEntityObject;

/**
 *
 * @author noone
 */
public class MerchantEntityObject extends MapEntityObject {

    private final TradeGood[] myGoods;
    private final MapTile endOfJourney;
    public MerchantEntityObject(TribalCampObject Owner, MapTile Destination, MapTile StartingPoint, String ImageName, TradeGood[] myGoods) {
        super(Owner, Destination, StartingPoint, ImageName);
        endOfJourney = StartingPoint;
        this.myGoods = myGoods;
    }

    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public void onArrival(MapTile myDestination) {
        if(endOfJourney != myDestination)
        {
            TribalCampObject Tradee = myDestination.getTerritory();
            Tradee.tradeInitiated(super.getOwner(), Tradee);
            this.returnToOrigin();
        } else {
            deleteMe = true; //We've finished Trading
            //TODO: outcome from trade
        }
    }

    private boolean deleteMe = false;
    
    @Override
    public boolean shouldDelete() {
        return deleteMe;
    }
    
}
