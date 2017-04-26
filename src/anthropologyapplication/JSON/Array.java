/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.JSON;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author noone
 */
public class Array extends Value {

    ArrayList<Value> myPair = new ArrayList<Value>();
    byte StartValue = Byte.parseByte("[");
    byte EndValue = Byte.parseByte("]");
    byte[] newValueCommaOffset = {Byte.parseByte(","), Byte.parseByte("\n")};
    byte[] newValueCommaNoOffset = {Byte.parseByte(","), Byte.parseByte(" ")};
    byte newValueNoCommaOffset = Byte.parseByte("\n");
    byte[] emptyArray = {Byte.parseByte("["), Byte.parseByte(" "), Byte.parseByte("]")};

    public int GetWeight() {
        int ReturnValue = 1;
        for(Value aVal : myPair)
        {
            ReturnValue += aVal.GetWeight();
        }
        return ReturnValue;
    }

    public void PrettyPrint(OutputStream aStream, int tabdepth) throws IOException {
        super.doTabDepth(tabdepth);
        aStream.write(myByteDepth);
        if (myPair.size() == 0) {
            aStream.write(emptyArray);
            return;
        }
        aStream.write(StartValue);
        aStream.write(newValueNoCommaOffset);

        for (int i = 0; i < myPair.size(); i++) {
            myPair.get(i).PrettyPrint(aStream, tabdepth + 1);
            if ((i + 1) == myPair.size()) {
                aStream.write(newValueNoCommaOffset);
            } else {
                aStream.write(newValueCommaOffset);
            }
        }
        aStream.write(myByteDepth);
        aStream.write(EndValue);
    }

    public int getLength() {
        return myPair.size();
    }

    private Value doValuePair(InputStream input) throws IOException, OperationNotSupportedException
    {
        byte aCharacter = (byte)input.read();
           if (aCharacter == Byte.parseByte("\"")) {
                return new String(input);
            }
            else if (aCharacter == Byte.parseByte("[")) {
                return new Array(input); //Will break this function if all chars don't handle their closing items
            }
            else if (aCharacter == Byte.parseByte("{")) {
                return new aObject(input, false);
            }
            else if (aCharacter == Byte.parseByte("n")) {
                return new Null(input);
            }
            else if (aCharacter == Byte.parseByte("f")) {
                return new False(input);
            }
            else if (aCharacter == Byte.parseByte("t")) {
                return new True(input);
            }
            else if (Character.isDigit((char)aCharacter))
            {
                return new Number(input);
            }
            else if (aCharacter == Byte.parseByte(" ") || aCharacter == Byte.parseByte("\r") || aCharacter == Byte.parseByte("\n")) {
                    
                return doValuePair(input);
            }
            else if (aCharacter == Byte.parseByte(",")) {
                return doValuePair(input);
            }
            else if (aCharacter == Byte.parseByte("]")) {
                return null;
            }
       
            throw new OperationNotSupportedException("Unhandled Character: " + aCharacter + " at character position: ");
    }

    public Array(InputStream input) throws IOException, OperationNotSupportedException {
        byte readData = (byte)input.read();
        while (readData != Byte.parseByte("]")) {
            Value aVal = doValuePair(input);
            if (aVal == null) {
                break; //Used to prevent empty arrays from adding a bad char
            }
            myPair.add(aVal);
        }
        readData = (byte)input.read(); //Moves us out of the array so we don't have to bother with it.
    }

    public Iterator<Value> getIterator() {
        return myPair.iterator();
    }

}
