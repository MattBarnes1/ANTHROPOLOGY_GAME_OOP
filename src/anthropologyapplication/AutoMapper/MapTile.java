/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.AutoMapper;

import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.AutoMapper.*;
import java.awt.AlphaComposite;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Duke
 */
public abstract class MapTile {
    private Vector3 MapTileLocation;
    
    private static double MaxImageSizeX;
    private static boolean MaxImageSize = false;
    private static double MaxImageSizeY;
    private static transient Image DefaultImage;
    private static transient Image DefaultImageUnexplored;
    private static transient Image derivedLoadedImage;
    private String ImageName;
    private transient Image tileBuilding;
    private char Printout;
    
    private Vector3 MapCoordinates;
    public MapTile(String filenameInAutomapperData)
    {
        ImageName = filenameInAutomapperData;
        myImage = new Image("anthropologyapplication/AutoMapper/" + filenameInAutomapperData);
        while(myImage.isBackgroundLoading());
        setupUnexploredImage();
    }
    
    public void setPrintoutString(char aChar)
    {
        Printout = aChar;
    }
    
    public void setCoordinates(Vector3 myCoordinates)
    {
        MapCoordinates = myCoordinates;
    }
    
    public Vector3 getCoordinates()
    {
        return MapCoordinates;
    }
    
    static void DrawDefaultTile(int x, int y, WritableImage myWorldImage) {
                myWorldImage.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), DefaultImage.getPixelReader(), 0, 0);
    }
    private String myImageName;
    
    public static void DrawUndefinedTile(int x, int y, WritableImage aGameCanvas) {
          
        aGameCanvas.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), DefaultImageUnexplored.getPixelReader(), 0, 0);
    }
    
    private  transient Image myImage;
    private boolean Explored = true;
    private boolean CanSeeInto = false;
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
         s.defaultReadObject();
         setupUnexploredImage();
    }

    private void writeObject(ObjectOutputStream s) throws IOException, ClassNotFoundException {
        s.defaultWriteObject();
        
    }
    @Override
    public abstract String toString();
    
    
    
    public abstract void generateSubType(MapTile[][] surroundingTilesAndThis, Random myRandom, double tilesAvailable);
    
    public static double getTileWidth() {
        return MaxImageSizeX;
    }

    public static double getTileHeight() {
        return MaxImageSizeY;
    }
    
    
    private void setupUnexploredImage()
    {
        if(DefaultImageUnexplored == null)
        {
            DefaultImageUnexplored = new Image("anthropologyapplication/AutoMapper/UnexploredTile.jpeg");
            MaxImageSizeX = DefaultImageUnexplored.getWidth();
            MaxImageSizeY = DefaultImageUnexplored.getHeight();
        }
    }

    public void setMapImage(Image myImage)
    {
        this.myImage = myImage;
    }
    
    public void tileWasExplored(boolean aBool)
    {
        Explored = aBool;
    }  
    AlphaComposite Building = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75F);
    public void Draw(int x, int y, WritableImage aGameCanvas) 
    {//TODO: int or double here?
        if(!Explored)
        {
            aGameCanvas.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), DefaultImageUnexplored.getPixelReader(), 0, 0);
        } 
        else {
            aGameCanvas.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), myImage.getPixelReader(), 0, 0);            
            if(tileBuilding != null)
            {
                Color.rgb(255, 255, 255, 0);
                aGameCanvas.getPixelWriter().setPixels((int)(x*getTileWidth()), (int)(y*getTileHeight()), (int)getTileWidth(), (int)getTileHeight(), tileBuilding.getPixelReader(), 0, 0);
                Color.rgb(255, 255, 255, 1);
            }
        }
    }

    public boolean isLand() {
        return b_isLand;
    }

    boolean b_isLand = false;
    
    public void setLand(boolean b) {
        b_isLand = b;
    }

    private String TileSubType;
    public void setSubtype(String subType) {
        TileSubType = subType;
    }
    private MapTile North = null;
    private MapTile Northeast = null;
    private MapTile Northwest = null;
    private MapTile East = null;
    private MapTile Southeast = null;
    private MapTile Southwest = null;
    private MapTile South = null;
    private MapTile West = null;
    
    
    public MapTile getNorthwest() {
        return Northwest;
    }

    public void setNorthwest(MapTile mapTile) {
        if(Northwest == null)
        {
            Northwest = mapTile;
            mapTile.setSoutheast(this);
        }
    }

    public MapTile getNorth() {
        return North;
    }

    public void setNorth(MapTile mapTile) {
        if(North == null)
        {
            North = mapTile;
            mapTile.setSouth(this);
        }
    }

    public MapTile getNortheast() {
        return Northeast;
    }

    public void setNortheast(MapTile mapTile) {
        if(Northeast == null)
        {
            Northeast = mapTile;
            mapTile.setSouthwest(this);
        }
    }

    public MapTile getEast() {
        return East;
    }

    public void setEast(MapTile mapTile) {
        if(East == null)
        {
            East = mapTile;
            mapTile.setWest(this);
        }
    }

    public MapTile getWest() {
       return West;
    }

    public MapTile getSouthwest() {
        return Southwest;
    }

    public MapTile getSouth() {
        return South;
    }

    public MapTile getSoutheast() {
        return Southeast;
    }

    public void setSoutheast(MapTile mapTile) {
        if(Southeast == null)
        {
        Southeast = mapTile;
        mapTile.setNorthwest(this);
        }
    }

    public void setSouth(MapTile mapTile) {
        if(South == null)
        {
        South = mapTile;
        mapTile.setNorth(this);
        }
    }

    public void setSouthwest(MapTile mapTile) {
        if(Southwest == null)
        {
        Southwest = mapTile;
        mapTile.setNortheast(this);
        }
    }

    public void setWest(MapTile mapTile) {
        if(West == null)
        {
            West = mapTile;
            mapTile.setEast(this);
        }
    }

    public void clearForeground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   public  void setForegroundImage(String ForegroundImageFileName) {
       int i = 0;
       if(ForegroundImageFileName.compareTo("NoFile") != 0) tileBuilding = new Image("anthropologyapplication/AutoMapper/" + ForegroundImageFileName);
    }

  

    

    

}
