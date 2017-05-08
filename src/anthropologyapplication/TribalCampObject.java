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
import javafx.scene.image.Image;
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
    public TribalCampObject(SocietyChoices mySocietyChoices) throws Exception {
        this.mySocietyChoices = mySocietyChoices;
        myReputationHandler = new ReputationHandler();
        myReputationHandler.doReputationCalculation(this);
        myProductionHandler = new ProductionHandler(this);
        myWarriorHandler = new WarriorHandler(this);
        myBuildingHandler = new BuildingHandler(this);
        myFoodHandler = new FoodHandler(this);
        myPopulationHandler = new PopulationHandler(this);
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
    
        private Image Starving = new Image("anthropologyapplication/AutoMapper/Starving.bmp");
        private Image StarvationDeath = new Image("anthropologyapplication/AutoMapper/StarvationDeath.bmp");
        private Image originalImage = new Image("anthropologyapplication/AutoMapper/TribalCamp.jpg");
        private Image CurrentImage = originalImage;
    
    public FoodHandler getFoodHandler()
    {
        return myFoodHandler;
    }
    
    private final Timer baseStarvationAnimationTimer = new Timer(0,0,0,10);
    private Timer currentStarvation = baseStarvationAnimationTimer;
    public void update(GameTime MS)
    {
        currentStarvation = currentStarvation.subtract(MS.getElapsedTime());
        if(currentStarvation.EqualTo(Timer.Zero) && getFoodHandler().isStarving()&& !getPopulationHandler().hasStarvedToDeath())
        {
            showStarvationWarning();
            currentStarvation = baseStarvationAnimationTimer;
        }
        if(getPopulationHandler().hasStarvedToDeath() && this.HomeTile.getMapTileForegroundImage() != StarvationDeath)
        {
            this.HomeTile.setStatusImage(StarvationDeath);
            
        }
        if(!getPopulationHandler().hasStarvedToDeath())
        {
            myProductionHandler.update(MS);
            myBuildingHandler.update(MS);
            myWarriorHandler.update(MS);
            myFoodHandler.update(MS);
            myPopulationHandler.update(MS);
        }
    }

    
    
    void saveAntagonists(AICampObject[] myEnemyArray) {
        this.myEnemyArray = myEnemyArray;
    }

    void saveMap(Map myMap) {
        this.myMap = myMap;
    }

    public AICampObject[] loadAntagonists() {
        return myEnemyArray;
    }

    public Map loadMap() {
        return myMap;
    }

    public void setHomeTile(MapTile HomeTile) {
       this.HomeTile = HomeTile;
       myBuildingHandler.forceBuild("Tribal Hut", HomeTile);

    }

    public int getFreeCitizens() {
        return this.TotalFreeCitizens;
    }

    public boolean hasFreeCitizens() {
      return (this.TotalFreeCitizens > 0);
    }

    public void removeFreeCitizen() {
        if(hasFreeCitizens())
        {
            this.TotalFreeCitizens--;
        }
    }

    public void addFreeCitizen() {
        this.TotalFreeCitizens++;
    }

    public WarriorHandler getWarriorHandler() {
        return myWarriorHandler;
    }

    public MapTile getMapTileLocation() {
        return this.HomeTile;
    }

    public PopulationHandler getPopulationHandler() {
        return this.myPopulationHandler;
    }

    public ReputationHandler getReputationHandler() {
       return this.myReputationHandler;
    }

    public void addFreeCitizens(int newCitizens) {
        
        TotalFreeCitizens += newCitizens;
    }

  
    
    
    public void tradeInitiated(TribalCampObject owner, TribalCampObject Tradee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeFreeCitizens(int abs) {
        TotalFreeCitizens -= abs;
    }

    
    public void showStarvationDeath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void showStarvationWarning() {
        if(CurrentImage != Starving)
        {
            CurrentImage = Starving;
            HomeTile.setStatusImage(Starving);
        } else {
            CurrentImage = originalImage;
            HomeTile.setStatusImage(originalImage);
        }
    }
}
