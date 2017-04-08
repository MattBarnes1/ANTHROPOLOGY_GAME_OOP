/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author noone
 */
public class aObject extends Value {

    HashMap<String, Value> myPair = new HashMap<String, Value>();

    public int GetWeight() {
        int Amount = 1;
        if (myPair.size() > 0) {
            Iterator<Value> anEnum = myPair.values().iterator();
            while (anEnum.hasNext()) {
                Amount += anEnum.next().GetWeight();
            }
        }
        return Amount;
    }

    byte[] valueColonOffset = {Byte.parseByte(":"), Byte.parseByte(" ")};
    byte myInternalPar = Byte.parseByte("{");
    byte myInternalParEnd = Byte.parseByte("}");
    byte[] newValueCommaOffset = {Byte.parseByte(","), Byte.parseByte("\n")};
    byte[] emptyObject = {Byte.parseByte("{"), Byte.parseByte(" "), Byte.parseByte("}")};
    byte myInternalByte = Byte.parseByte("\n");

    public void PrettyPrint(OutputStream aStream, int tabdepth) throws IOException {
        super.doTabDepth(tabdepth);
        aStream.write(myByteDepth);
        if (myPair.size() > 0) {
            Iterator<Map.Entry<String, Value>> anEnum = myPair.entrySet().iterator();
            boolean hasNext = anEnum.hasNext();
            aStream.write(myInternalPar);
            aStream.write(myInternalByte);
            while (hasNext) {
                Map.Entry<String, Value> myValue = anEnum.next();

                myValue.getKey().PrettyPrint(aStream, tabdepth + 1);
                aStream.write(valueColonOffset);

                if (myValue.getValue().getClass().getDeclaringClass() == Array.class) {
                    aStream.write(myInternalByte);
                    myValue.getValue().PrettyPrint(aStream, tabdepth + 1);
                } else if (myValue.getValue().getClass().getDeclaringClass() == aObject.class) {
                    aStream.write(myInternalByte);
                    myValue.getValue().PrettyPrint(aStream, tabdepth + 1);
                } else {
                    myValue.getValue().PrettyPrint(aStream, 0);
                }
                hasNext = anEnum.hasNext();
                if (hasNext) {
                    aStream.write(newValueCommaOffset);
                }
            }
            aStream.write(myInternalByte);
            aStream.write(myByteDepth);
            aStream.write(myInternalParEnd);
        } else if (myPair.size() == 0) {
            aStream.write(emptyObject);
        }

    }

    public aObject(InputStream input, boolean namelessObject) throws IOException, OperationNotSupportedException {
        doValuePair(input);
    }

    private void doKeyValuePair(InputStream anInput) throws IOException, OperationNotSupportedException {
        String myKeyString;
        anInput.mark(2);
        byte character = (byte) anInput.read();
        if (character == Byte.parseByte(" ") || character == Byte.parseByte(":")) {
            doKeyValuePair(anInput);
        } else if (character == Byte.parseByte("\\")) {
            anInput.reset();
            myKeyString = new String(anInput);
            Value aValue = doValuePair(anInput);
            myPair.put(myKeyString, aValue);
            doKeyValuePair(anInput);
        }
    }

    boolean hasValue = false;

    private Value doValuePair(InputStream anInput) throws IOException, OperationNotSupportedException {
        anInput.mark(1);
        byte character = (byte) anInput.read();

        if (character == Byte.parseByte("\\")) {
            if (hasValue) {
                return new String(anInput);
            } else {
                hasValue = true;
                String aName = new String(anInput);
                myPair.put(aName, doValuePair(anInput));
                hasValue = false;
                return doValuePair(anInput);
            }
        } else if (character == Byte.parseByte("[")) {
            return new Array(anInput); //Will break this function if all chars don't handle their closing items
        } else if (character == Byte.parseByte("{")) {
            return new aObject(anInput, false);
        } else if (character == Byte.parseByte("n")) {
            return new Null(anInput);
        } else if (character == Byte.parseByte("f")) {
            return new False(anInput);
        } else if (character == Byte.parseByte("t")) {
            return new True(anInput);
        } else if (Character.isDigit((char) character)) {
            return new Number(anInput);
        } else if (character == Byte.parseByte(" ") || character == Byte.parseByte("\r") || character == Byte.parseByte("\n")) {

            return doValuePair(anInput);
        } else if (character == Byte.parseByte(",")) {
            return doValuePair(anInput);
        } else if (Character.isDigit((char) character) || character == Byte.parseByte("-") || character == Byte.parseByte("+"))//TODO: char.isnumber
        {
            anInput.reset();
            return new Number(anInput);
        } else if (character == Byte.parseByte(" ") || character == Byte.parseByte("\r") || character == Byte.parseByte("\n")) {
            return doValuePair(anInput);
        } else if (character == Byte.parseByte(":")) {
            return doValuePair(anInput);
        } else if (character == Byte.parseByte(",")) {

            return doValuePair(anInput);
        } else if (character == Byte.parseByte("}")) {
            throw new UnsupportedOperationException("Unhandled Character: " + character + " at character position: ");

        }
        throw new UnsupportedOperationException("Unhandled Character: " + character + " at character position: ");
    }

}
