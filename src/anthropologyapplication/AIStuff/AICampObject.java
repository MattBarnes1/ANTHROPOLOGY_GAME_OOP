/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.AIStuff;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.Map;
import anthropologyapplication.SocietyChoices;
import anthropologyapplication.TribalCampObject;
import java.util.ArrayList;
import java.util.Random;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class AICampObject extends TribalCampObject {
    private Map myWorldMap;
    private final static String[] PossibleNames = { "A", "B", "C", "D", "E" };
    private final static boolean[] isNameInUse = new boolean[PossibleNames.length];
    private final AIHandler myHandler;
    public AICampObject(SocietyChoices mySocietyChoices, Map myMap, MapTile HomeTile) throws Exception {
        super(mySocietyChoices);
        super.setHomeTile(HomeTile);
        myWorldMap = myMap;
        myHandler = new AIHandler(this);
    }
    
    public void startAI()
    {
        myHandler.startAI();
    }
    
    
    @Override 
    public void update(GameTime MS)
    {
        StateExecution aFunc = myHandler.getStateExecution();
        if(aFunc != null)
        {
            aFunc.Execute(); //Executes the state
        }
    }
    
    private static ArrayList<Integer> NamesUsed = new ArrayList<Integer>();
    
    private static String[] TribeNames = {};
    
    private static Random myRandom = new Random();
    
    
    private static String generateRandomName()
    {
        int RandomChance = myRandom.nextInt(TribeNames.length);
        while(NamesUsed.contains(RandomChance))
        {
            RandomChance = myRandom.nextInt(TribeNames.length);
        }
        return TribeNames[RandomChance];
    }
  

 

    
}
