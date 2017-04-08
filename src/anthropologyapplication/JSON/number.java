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

/**
 *
 * @author noone
 */
public class Number extends Value
{
        ByteArrayOutputStream myInputData = new ByteArrayOutputStream();
        public Number(InputStream input) throws IOException
        {
            
            byte InputByte = (byte)(input.read());
            myInputData.write(InputByte); //  
            while (InputByte != Byte.parseByte(" "))
            {        //while((!(aString[aPosition].Equals('"') && !aString[aPosition-1].Equals('\\'))) && !((aString[aPosition - 1].Equals('\\') && aString[aPosition - 2].Equals('\\') && aString[aPosition].Equals('\"'))))//as long as we see " and not \" just copy EDIT: No, \\" would be allowable
                InputByte = (byte)(input.read());
            }
            //Moves us past the final " so we don't activate the string again;
        }
        
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

