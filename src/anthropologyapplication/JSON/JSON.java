/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.JSON;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Duke
 */
public class JSON implements Iterator {
    aObject StartingObject;
    array anArray;
    public JSON(InputStream newStream) throws IOException {
        int charVal = newStream.read();
        if(charVal == '{') //it's an Object
        {
            StartingObject = new aObject(newStream);
        } else if(charVal == '[')
        {
            anArray = new array(newStream);
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object next() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

   


    @Override
    public void remove() {
        Iterator.super.remove(); //To change body of generated methods, choose Tools | Templates.
    }

    public array getArray() {
        return anArray;
    }

   
    public Value getValueInObject(aObject myObject, String eventString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public aObject getObject() {
        return StartingObject;
    }
    
}
