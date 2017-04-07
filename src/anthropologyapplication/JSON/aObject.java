/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.JSON;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Duke
 */
public class aObject extends Value {
    Map<string, Value> myPair = new HashMap<>(); 

    aObject(InputStream newStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int GetWeight()
    {
        int Amount = 1;
        if (!myPair.isEmpty()) {
            Iterator<Value> anEnum = myPair.values().iterator();
            while (anEnum.hasNext()) {
                //Amount += anEnum.next().GetWeight();
            }
        }
        return Amount;
    }

    public Value getValueInObject(string eventString) {
       return myPair.get(eventString);
    }
}
