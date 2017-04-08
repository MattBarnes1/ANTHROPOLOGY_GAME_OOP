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
public abstract class Value {
     public abstract void PrettyPrint(OutputStream aStream, int tabdepth) throws IOException;
		public abstract int GetWeight();

        private byte Tab = Byte.parseByte("\t");
        protected byte[] myByteDepth = { };
        protected void doTabDepth(int tabDepth)
        {
            myByteDepth = new byte[tabDepth];
            for (int i = 0; i < tabDepth; i++)
            {
                myByteDepth[i] = Tab;
            }
        }
}
