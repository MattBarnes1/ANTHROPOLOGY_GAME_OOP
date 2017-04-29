/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.internalLockers;

import anthropologyapplication.TradeGoods.ProductionHandler;
import anthropologyapplication.Warriors.Clubmen;
import anthropologyapplication.Warriors.Warrior;

/**
 *
 * @author Duke
 */
public class internalWarriorLocker {

    private final Warrior aWarrior;
    private boolean Available = false;
    


    public internalWarriorLocker(Warrior aWarrior, boolean b) {
        Available = b;
        this.aWarrior = aWarrior;
    }
    
    public String getName() {
        return aWarrior.getName();
    }

    public Warrior Copy() {
        return aWarrior.Copy();
    }

    

    public boolean isAvailable() {
        return Available;
    }

    public Warrior getWarrior() {
        return aWarrior;
    }

    public void unlock() {
        Available = true;
    }

    public void lock() {
        Available = false;
    }
    
}
