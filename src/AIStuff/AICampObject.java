/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AIStuff;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.Map;
import anthropologyapplication.SocietyChoices;
import anthropologyapplication.TribalCampObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class AICampObject extends TribalCampObject {
    private Map myWorldMap;
    private final static String[] PossibleNames = { "A", "B", "C", "D", "E" };
    private final static boolean[] isNameInUse = new boolean[PossibleNames.length];
    public AICampObject(SocietyChoices mySocietyChoices, Map myMap, MapTile HomeTile) {
        super(mySocietyChoices);
        super.setHomeTile(HomeTile);
        myWorldMap = myMap;
    }
    
    @Override 
    public void update(GameTime MS)
    {
        
    }
    
    private static String generateRandomName()
    {
        return "";
    }
  

 

    
}
