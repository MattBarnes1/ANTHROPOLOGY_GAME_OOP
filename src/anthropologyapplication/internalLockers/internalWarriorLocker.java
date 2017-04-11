/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.internalLockers;

import anthropologyapplication.ProductionHandler;
import anthropologyapplication.Warriors.Warrior;

/**
 *
 * @author Duke
 */
public class internalWarriorLocker {

    private final Warrior aWarrior;

    
    public internalWarriorLocker(Warrior aWarrior)
    {
        this.aWarrior = aWarrior;
    }
    
    public String getName() {
        return aWarrior.getName();
    }

    public boolean checkIfCanBuild(ProductionHandler myProductionHandler) {
        return aWarrior.checkIfCanBuild(myProductionHandler);
    }

    public Warrior Copy() {
        return this.Copy(aWarrior);
    }

    private Warrior Copy(Warrior aWarrior) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
