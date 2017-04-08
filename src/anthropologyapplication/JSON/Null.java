/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.JSON;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author noone
 */
public class Null extends Value
    {
        byte[] myInternalBytes = new byte[4];
        public Null(InputStream aStream) throws IOException
        {
            aStream.read(myInternalBytes);
        }
        public int GetWeight()
        {
            return 1;
        }
        public void PrettyPrint(OutputStream aStream, int tabdepth) throws IOException
        {
            super.doTabDepth(tabdepth);
            aStream.write(myByteDepth);
            aStream.write(myInternalBytes);
        }
        public java.lang.String ToString()
            {
                return "null";
            }
	}

