/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.AutoMapper.MapTile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class AICampObject extends TribalCampObject {
    
    public AICampObject(SocietyChoices mySocietyChoices, MapTile HomeTile) {
        super(mySocietyChoices);
        super.setHomeTile(HomeTile);
    }
    
    @Override 
    public void update(GameTime MS)
    {
        
    }
    
    
  

    public void doFoodUpdate() {
        throw new NotImplementedException();
    }

    
}
