/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.Reputation.ReputationHandler;
import anthropologyapplication.AIStuff.AICampObject;
import anthropologyapplication.Buildings.BuildingHandler;
import anthropologyapplication.Warriors.WarriorHandler;
import anthropologyapplication.TradeGoods.ProductionHandler;
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
    private PopulationHandler myPopulationHandler;
    private int TotalFreeCitizens = 10;
    private FoodHandler myFoodHandler;
    private WarriorHandler myWarriorHandler;
    private int availableFood = 100;
    private Map myMap;
    private AICampObject[] myEnemyArray;
    private MapTile HomeTile;
    private String myName;
    private ReputationHandler myReputationHandler;
    public TribalCampObject(SocietyChoices mySocietyChoices) {
        myReputationHandler = new ReputationHandler();
        myProductionHandler = new ProductionHandler(this);
        myWarriorHandler = new WarriorHandler(this);
        myBuildingHandler = new BuildingHandler(this);
        myFoodHandler = new FoodHandler(this);
        myPopulationHandler = new PopulationHandler(this);
        this.mySocietyChoices = mySocietyChoices;
    }

    public void setName(String aName)
    {
        myName = aName;
    }
    
    public SocietyChoices getSocietyChoices() {
        return mySocietyChoices;
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
        myProductionHandler.update(MS);
        myBuildingHandler.update(MS);
        myWarriorHandler.update(MS);
        myFoodHandler.update(MS);
        myPopulationHandler.update(MS);
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

    public void setHomeTile(MapTile HomeTile) {
       this.HomeTile = HomeTile;
       myBuildingHandler.forceBuild("Tribal Hut", HomeTile);

    }

    public int getFreeCitizens() {
        return this.TotalFreeCitizens;
    }

    boolean hasFreeCitizens() {
      return (this.TotalFreeCitizens > 0);
    }

    void removeFreeCitizen() {
        if(hasFreeCitizens())
        {
            this.TotalFreeCitizens--;
        }
    }

    void addFreeCitizen() {
        this.TotalFreeCitizens++;
    }

    public WarriorHandler getWarriorHandler() {
        return myWarriorHandler;
    }

    Object getMapTileLocation() {
        return this.HomeTile;
    }

    PopulationHandler getPopulationHandler() {
        return this.myPopulationHandler;
    }

    public ReputationHandler getReputationHandler() {
       return this.myReputationHandler;
    }

    void addFreeCitizens(int newCitizens) {
        TotalFreeCitizens += newCitizens;
    }
}
