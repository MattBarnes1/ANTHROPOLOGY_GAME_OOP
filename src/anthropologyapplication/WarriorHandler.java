/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

/**
 *
 * @author Duke
 */
public class WarriorHandler {
    ProductionHandler myProductionHandler;
    int TrainedWarriors = 0;
    int WarriorsToTrain = 0;
    WarriorHandler(ProductionHandler aProductionHandler) {
        myProductionHandler = aProductionHandler;
    }

    public void incWarriorsToTrain()
    {
            TrainedWarriors++;
    }
    public void decWarriorsToTrain()
    {

    }
    
    
    public void update(GameTime MS) {
        
    }

    public void addWarriors(int i) {
        TrainedWarriors++;//To change body of generated methods, choose Tools | Templates.
    }

    public int getWarriorsAmount() {
        return TrainedWarriors;
    }

    public void removeWarriors(int i) {
        if(TrainedWarriors - i < 0)
        {
            TrainedWarriors = 0;
        } else {
            TrainedWarriors -= i;
        }
    }
    
}
