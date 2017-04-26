/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.JSON;

import java.io.IOException;
import java.io.InputStream;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author noone
 */
public class JSON {
    
    
    Value Base;
    public JSON(InputStream newStream) throws IOException, OperationNotSupportedException {
       char aChar = (char)newStream.read();
       if(aChar == '[')
       {
           Base = new Array(newStream);
       }
       else {
           Base = new aObject(newStream, true);
       }
    }

   // Stack<Value> myObjectsConsidered = new Stack<>();
    
    public aObject getObject() {
        return (aObject)Base;
    }
    
}
