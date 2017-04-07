/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Warriors;

import anthropologyapplication.Time;
import anthropologyapplication.TradeGood;

/**
 *
 * @author Duke
 */
public class Warrior {
    final Time TimeCountDown;
    public Warrior(TradeGood[] forProduction, float TrainingTimeMS)
    {
        TimeCountDown = new Time(0,0,0,TrainingTimeMS);
    }
}
