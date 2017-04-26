/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Duke
 */
public class FileLogger {

      
  
    
    public enum LOGTO
    {
        PATHFINDER, //Only need to modify this to make new log file
        CAMP_AI,
        MAP_GENERATION,
        MAP_ENTITIES, 
        RANDOM_EVENTS_CREATOR
    }
    protected static Lock aLock;
    protected FileWriter myWriter = null;
    protected static FileLogger myInternalLogger;
    protected final String LogDirectory = "Log";
    protected static boolean canLog = true;
    
    ArrayList<ArrayList<String>> StringOutputForLogs = new ArrayList<>();

    Path LogDir = FileSystems.getDefault().getPath(LogDirectory);
    private void flushToFiles() throws IOException {
        if(!Files.exists(LogDir))
        {
            Files.createDirectory(LogDir);
        }
        
        for(int i = 0; i < (LOGTO.values()).length; i++)
        {
            String myLogName = LOGTO.values()[i].toString();
            
            Path LogDir = FileSystems.getDefault().getPath(LogDirectory, myLogName + ".txt");
            if(Files.exists(LogDir) && !Files.isWritable(LogDir))
            {
                int salt = findSaltValue(myLogName);
                LogDir = FileSystems.getDefault().getPath(LogDirectory, myLogName + salt + ".txt");
            } else {
                Files.deleteIfExists(LogDir);
                Files.createFile(LogDir);
            }
            
            myWriter = new FileWriter(LogDir.toFile());
            ArrayList<String> myCurrentList = StringOutputForLogs.get(i);
            for(String A : myCurrentList )
            {
                myWriter.write(A);
            }
            myWriter.flush();
            myWriter.close();
        }
    }

    Random Salt = new Random();
    
    private int findSaltValue(String myLogName) {
        int salt = 0;
        while(Files.exists(FileSystems.getDefault().getPath(LogDirectory, myLogName + salt + ".txt")))
        {
            salt = Salt.nextInt();
        }
        return salt;
    }
    
    
    public static void enableWriteToLog()
    {
        canLog = true;
    }

    public static void disableWriteToLog()
    {
        canLog = false;
    }
    
    private FileLogger() throws FileNotFoundException, IOException
    {
        for(LOGTO A : LOGTO.values())
        {
            StringOutputForLogs.add(new ArrayList<String>());
        }
    }
    
    private static boolean isAddingToArrayList = false;
    StringBuilder myString = new StringBuilder();
    private synchronized void internalWriteMessage(LOGTO logToWriteTo, String whoIsWriting, String aMessage)
    {
        while(isAddingToArrayList)
         {
             try {
                 wait();//Wait for the one currently writing to the file to finish;
             } catch (InterruptedException ex) {
                 Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    flushToFiles();
                } catch (IOException ex1) {
                    Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex1);
                }
             }
         }
        isAddingToArrayList = true;
        myString.append(new SimpleDateFormat("MM.dd 'at' HH:mm:ss").format(Calendar.getInstance().getTime()).toString() + System.getProperty("line.separator"));
        myString.append(whoIsWriting + System.getProperty("line.separator") + System.getProperty("line.separator"));
        myString.append(aMessage + System.getProperty("line.separator") + System.getProperty("line.separator"));
        
        for(int i = 0; i < LOGTO.values().length; i++)
        {
            if(LOGTO.values()[i].compareTo(logToWriteTo) == 0)
            {
                StringOutputForLogs.get(i).add(myString.toString());
            }
        }
        myString.delete(0, myString.length()-1);
        isAddingToArrayList = false;
        
        notify(); //Notify next thread;
    }
    
    public static void createLogFile() throws IOException
    {
        if(myInternalLogger == null)
        {
            myInternalLogger = new FileLogger(); //This should be thread safe
        }
    }
    
    
    public static void flushLogsToFiles() throws IOException
    {
        if(myInternalLogger != null && canLog)
        {
            myInternalLogger.flushToFiles();
        }
    }
    
    
    //Do not ever edit;
    public static void writeToLog(LOGTO logToWriteTo, String whoIsWriting, String aMessage)
    {
        try {
            createLogFile();
            myInternalLogger.internalWriteMessage(logToWriteTo, whoIsWriting, aMessage);
        } catch (IOException ex) {
            Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
