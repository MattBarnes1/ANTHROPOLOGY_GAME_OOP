/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.AIStuff;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.TribalCampObject;
import java.util.ArrayList;

/**
 *
 * @author Duke
 */
public class NoisyAIData {
    private static ArrayList<TribalCampObject> myCampObjects = new ArrayList<>();
    
    public static void addCamp(TribalCampObject aCampObject)
    {
        myCampObjects.add(aCampObject);
    }
    
    public static void getCampDataByIndex(int i)
    {
        
    }
    
    public static void getCampDataByTile(MapTile aTile)
    {
        
    }
    
    
    
}
