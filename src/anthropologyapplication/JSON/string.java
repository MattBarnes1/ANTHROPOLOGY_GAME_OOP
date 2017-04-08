/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.JSON;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author noone
 */
public class String extends Value{
        ByteArrayOutputStream myInputData = new ByteArrayOutputStream();
        public String(InputStream input) throws IOException
        {
            myInputData.write(Byte.parseByte("\""));
            byte InputByte  = (byte)(input.read());
            myInputData.write(InputByte);
            while ( InputByte != Byte.parseByte("\""))
            {        //while((!(aString[aPosition].Equals('"') && !aString[aPosition-1].Equals('\\'))) && !((aString[aPosition - 1].Equals('\\') && aString[aPosition - 2].Equals('\\') && aString[aPosition].Equals('\"'))))//as long as we see " and not \" just copy EDIT: No, \\" would be allowable
                InputByte = (byte)(input.read());
                if (InputByte == Byte.parseByte("\\"))
                {
                    myInputData.write(InputByte);
                    InputByte = (byte)(input.read());
                }
                    myInputData.write(InputByte);
            }
            //The Very last quote must be added!
            InputByte = (byte)(input.read());
            myInputData.write(InputByte);
            //Moves us past the final " so we don't activate the string again;
        }

        public String(java.lang.String aString)
        {
            for(int i = 0; i < aString.length(); i++)
            {
                myInputData.write(Byte.parseByte(""+aString.charAt(i)));
            }
        }
        
        @Override
        public void PrettyPrint(OutputStream aStream, int tabdepth) throws IOException
        {
            super.doTabDepth(tabdepth);
            myInputData.writeTo(aStream);
        }

        public int GetWeight()
        {
            return 1;
        }

        @Override
        public java.lang.String toString()
        {
            return myInputData.toString();
        }
    }

