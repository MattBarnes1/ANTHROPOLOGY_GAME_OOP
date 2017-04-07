/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.AutoMapper;

/**
 *
 * @author Duke
 */
public class Vector3 implements java.io.Serializable {

    public final static Vector3 Zero = new Vector3();
    
    static public Vector3 calculateZeroTransform(Vector3 MinValues) {
       return new Vector3(Math.abs(MinValues.X), Math.abs(MinValues.Y),Math.abs(MinValues.Z));
    }
    public int X, Y, Z;
    public Vector3 (int x, int y, int z)
    {
        X = x;
        Y = y;
        Z = z;
    }
    
    public Vector3()
    {
        X = 0;
        Y = 0;
        Z = 0;
    }

    public Vector3(Vector3 incomingVector)
    {
        X = incomingVector.X;
        Y = incomingVector.Y;
        Z = incomingVector.Z;
    }
    
    public Vector3(int[] roomCoordinates) {
        X = roomCoordinates[0];
        Y = roomCoordinates[1];
        Z = roomCoordinates[2];
    }
    
    @Override
    public String toString()
    {
        return "Vector3\tX: " + X + "\tY: " + Y + "\tZ: " + Z;
    }
    
    public float calculateDistance(Vector3 toThisVector)
    {
        return (float)Math.sqrt((double)((X-toThisVector.X)^2 + (Y-toThisVector.Y)^2 + (Z-toThisVector.Z)^2));
    }

    public Vector3 Transform(Vector3 VectorZeroTransformData) {
        return new Vector3(X + VectorZeroTransformData.X, Y + VectorZeroTransformData.Y, Z + VectorZeroTransformData.Z);
    }

    public boolean isLessThan(Vector3 aVector) {
        return (this.X < aVector.X && this.Y < aVector.Y && this.Z < aVector.Z);
    }
    
    public boolean isEqualTo(Vector3 aVector)
    {
        return (this.X == aVector.X && this.Y == aVector.Y && this.Z == aVector.Z);
    }
    
    public boolean isGreaterThan(Vector3 aVector) {
        return (this.X > aVector.X && this.Y > aVector.Y && this.Z > aVector.Z);
    }
    
    public boolean isLessThanEqualTo(Vector3 aVector) {
      return (this.X <= aVector.X && this.Y <= aVector.Y && this.Z <= aVector.Z);
    }
    
    public boolean isGreaterThanEqualTo(Vector3 aVector) {
        return (this.X >= aVector.X && this.Y >= aVector.Y && this.Z >= aVector.Z);
    }

    public boolean isConfinedToRegion(Vector3 Maximum, Vector3 Minimum) {
        boolean myDebug = isLessThanEqualTo(Maximum);
        boolean myDebug2 = isGreaterThanEqualTo(Minimum);
        return( myDebug && myDebug2);
    }
}
