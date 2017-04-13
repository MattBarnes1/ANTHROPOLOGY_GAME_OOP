/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

/**
 *
 * @author noone
 */
public class PopulationHandler {
    private TribalCampObject myTribe;
    private int Population = 10; //Starting Population
    public PopulationHandler(TribalCampObject myCamp)
    {
        initialTimer = new Timer(730, 0, 0, 0); //Ever 2 ingame years birth
        currentTimer = initialTimer;
        myTribe = myCamp;
    }

    private final Timer initialTimer;
    private Timer currentTimer;
    
    void update(GameTime MS) {
        currentTimer.subtract(MS.getElapsedTime());
        if(currentTimer.EqualTo(new Timer(0,0,0,0)))
        {
            currentTimer = initialTimer;
            doPopulationGrowth();
        }
    }

    private void doPopulationGrowth() {
        myTribe.getBuildingHandler().getAllBuiltBuildingsByType(Homes.class);
    }
    
    
    
}
