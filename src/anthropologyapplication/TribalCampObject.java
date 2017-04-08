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
public class TribalCampObject {

    private ProductionHandler myProductionHandler;
    private BuildingHandler myBuildingHandler;
    private SocietyChoices mySocietyChoices;
    private int TotalAvailableCitizens = 10;
    private FoodHandler myFoodHandler;
    private WarriorHandler myWarriorHandler;
    private int availableFood = 100;
    private Map myMap;
    private AICampObject[] myEnemyArray;
    private MapTile HomeTile;
    
    public TribalCampObject(SocietyChoices mySocietyChoices) {
        myProductionHandler = new ProductionHandler();
        myWarriorHandler = new WarriorHandler(myProductionHandler);
        myBuildingHandler = new BuildingHandler();
        myFoodHandler = new FoodHandler(myBuildingHandler);
        this.mySocietyChoices = mySocietyChoices;
    }

    public SocietyChoices getPlayerSocietyChoices() {
        return mySocietyChoices;
    }

    public void setBirthRate(float PercentageOfBase) {
        throw new NotImplementedException();
    }

    public ProductionHandler getProductionHandler() {
        return myProductionHandler;
    }

   
    public BuildingHandler getBuildingHandler() {
        return myBuildingHandler;
    }
    
    public FoodHandler getFoodHandler()
    {
        return myFoodHandler;
    }
    
    public void update(GameTime MS)
    {
        myFoodHandler.update(MS);
        myWarriorHandler.update(MS);
        myProductionHandler.update(MS);
        myBuildingHandler.update(MS);
    }

    void saveAntagonists(AICampObject[] myEnemyArray) {
        this.myEnemyArray = myEnemyArray;
    }

    void saveMap(Map myMap) {
        this.myMap = myMap;
    }

    AICampObject[] loadAntagonists() {
        return myEnemyArray;
    }

    Map loadMap() {
        return myMap;
    }

    void setHomeTile(MapTile HomeTile) {
       this.HomeTile = HomeTile;
       System.out.println(HomeTile.getCoordinates());
       myBuildingHandler.forceBuild("Tribal Hut", HomeTile);
       
    }

    public int getFreeCitizens() {
        return this.TotalAvailableCitizens;
    }

    boolean hasFreeCitizens() {
      return (this.TotalAvailableCitizens > 0);
    }

    void removeFreeCitizen() {
        if(hasFreeCitizens())
        {
            this.TotalAvailableCitizens--;
        }
    }

    void addFreeCitizen() {
        this.TotalAvailableCitizens++;
    }

    WarriorHandler getWarriorHandler() {
        return myWarriorHandler;
    }

    Object getMapTileLocation() {
        return this.HomeTile;
    }
}
