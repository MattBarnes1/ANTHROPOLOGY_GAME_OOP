/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.MapTiles.MapTile_Land;
import java.util.Random;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Duke
 */
public class PerlinNoiseGeneratorForTerrain extends Service {

    private final int Seed;
    private final int arrayX;
    private final int arrayY;
    private float[][] smoothArray;
    private float[][] WhiteNoiseArray;

    private float currentOctave;
    private final float maxOctave = 0.5F;

    public PerlinNoiseGeneratorForTerrain(int Seed, int arrayX, int arrayY) {
        this.Seed = Seed;
        this.arrayX = arrayX;
        this.arrayY = arrayY;
    }

    float[][] perlinNoise;

   public float[][] getPerlin()
   {
       return perlinNoise;
   }
    @Override
    protected Task createTask() {
        Task aTask = new Task<float[][]>() {
            @Override
            protected float[][] call() throws Exception {
                smoothArray = null;
                WhiteNoiseArray = null;
                this.updateMessage("Creating White Noise for Perlin...");
                generateWhiteNoise();
                this.updateMessage("Creating Smooth Noise for Perlin...");
                float[][] Debug = generatePerlinNoise();
                return Debug;


            }

            private void generateWhiteNoise() {
                Random random = new Random(Seed); //Seed to 0 for testing
                WhiteNoiseArray = new float[arrayX][arrayY];
                smoothArray = new float[arrayX][arrayY];
                for (int i = 0; i < arrayX; i++) {
                    for (int j = 0; j < arrayY; j++) {
                        WhiteNoiseArray[i][j] = (float) random.nextDouble();
                    }
                }
            }

            private float[][] generateSmoothNoise(int Octave) {
                int samplePeriod = 2 ^ Octave; // calculates 2 ^ k
                float sampleFrequency = 1.0f / samplePeriod;
                for (int x = 0; x < arrayX; x++) {
                    int sample_i0 = (x / samplePeriod) * samplePeriod;
                    int sample_i1 = (sample_i0 + samplePeriod) % arrayX;
                    float horizontal_blend = (x - sample_i0) * sampleFrequency;
                    for (int y = 0; y < arrayY; y++) {
                        int sample_j0 = (y / samplePeriod) * samplePeriod;
                        int sample_j1 = (sample_j0 + samplePeriod) % arrayY; //wrap around
                        float vertical_blend = (y - sample_j0) * sampleFrequency;

                        //blend the top two corners
                        float top = Interpolate((float) WhiteNoiseArray[sample_i0][sample_j0],
                                (float) WhiteNoiseArray[sample_i1][sample_j0], horizontal_blend);

                        //blend the bottom two corners
                        float bottom = Interpolate((float) WhiteNoiseArray[sample_i0][sample_j1],
                                (float) WhiteNoiseArray[sample_i1][sample_j1], horizontal_blend);

                        //final blend
                        smoothArray[x][y] = Interpolate(top, bottom, vertical_blend);
                    }
                }
                return smoothArray;
            }

            
            float Interpolate(float x0, float x1, float alpha) {
                return x0 * (1 - alpha) + alpha * x1;
            }

            private int startingOct = 2;

            private float[][] generatePerlinNoise() {
                int LocalOctaveCount = startingOct;
                int width = arrayX;
                int height = arrayY;

                float[][][] smoothNoise = new float[LocalOctaveCount][][]; //an array of 2D arrays containing

                float persistance = 0.5f;

                //generate smooth noise
                for (int i = 0; i < LocalOctaveCount; i++) {
                    smoothNoise[i] = generateSmoothNoise(i);
                }

                perlinNoise = new float[arrayX][arrayY];
                float amplitude = 1.0f;
                float totalAmplitude = 0.0f;

                //blend noise together
                for (int octave = LocalOctaveCount - 1; octave >= 0; octave--) {
                    amplitude *= persistance;
                    totalAmplitude += amplitude;

                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                        }
                    }
                }

                //normalisation
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        perlinNoise[i][j] /= totalAmplitude; //makes this between 0 and 1 always
                    }
                }
                return perlinNoise;
            }
        };
        return aTask;
    }
}
