/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Warriors;

import anthropologyapplication.GameTime;
import anthropologyapplication.Time;
import anthropologyapplication.TradeGood;

/**
 *
 * @author Duke
 */
public class Warrior {
    final Time TimeCountDown;
    public Warrior(TradeGood[] forProduction, Time TrainingTimeMS)
    {
        TimeCountDown = TrainingTimeMS;
    }

    public double getStrength() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isDoneBuilding() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(GameTime MS) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
