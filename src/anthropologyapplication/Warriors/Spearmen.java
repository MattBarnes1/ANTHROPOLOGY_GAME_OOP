/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Warriors;

import anthropologyapplication.Timer;

/**
 *
 * @author noone
 */
public class Spearmen extends Warrior {

    public Spearmen(String Name, String Description,  Timer TrainingTimeMS, int Strength)
    { 
        super(Name, Description,  TrainingTimeMS, Strength);
    }
    
    @Override
    public Warrior Copy()
    {
       return new Spearmen(this);
    }
    
    
    private Spearmen(Spearmen mySpearmen)
    {
        super(mySpearmen.getName(), mySpearmen.getDescription(), mySpearmen.BuildTime, mySpearmen.Strength);
    }
}
