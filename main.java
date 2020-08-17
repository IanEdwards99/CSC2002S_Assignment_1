package src;

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class main{
    public static void main(String [] args)
    {
        String inputFile = args[0]; String outputFile = args[1];
        float[][] grid = null; float[][] classified = null;//grid for inputs.
        int cols, rows, basins = 0;

        try{
            FileReader fIn = new FileReader(inputFile);
            Scanner dataFile = new Scanner(fIn);
            rows = dataFile.nextInt(); cols = dataFile.nextInt();
            grid = new float[rows][cols]; classified = new float[rows][cols];//instantiate 2d array.

            for (int i = 0; i <= rows; i++){
                for (int j = 0; j <= cols; j++){
                    grid[i][j] = dataFile.nextFloat();
                    if (i == 0 || i == rows)
                        basins++;
                    else if (j ==0 || j == cols){
                        basins++;
                    }
                }
            }

            for (int i = 1; i <= rows-1; i++){
                for (int j = 1; j <= cols-1; j++){
                    if ((grid[i+1][j+1] - grid[i][j] >= 0.01) && (grid[i-1][j+1] - grid[i][j] >= 0.01) && (grid[i+1][j] - grid[i][j] >= 0.01)
                    && (grid[i][j+1] - grid[i-1][j-1] >= 0.01) && (grid[i-1][j] - grid[i][j-1] >= 0.01) && (grid[i+1][j-1] - grid[i][j] >= 0.01)){
                        basins++;
                        classified[i][j] = grid[i][j];
                    }
                    else classified[i][j] = 0;

                }
            }

            File fOut = new File(outputFile);
            FileWriter output = new FileWriter(fOut);
            output.write(Integer.toString(basins) + "\n");

            for (int i = 1; i <= rows-1; i++){
                for (int j = 1; j <= cols-1; j++){
                    if (classified[i][j] != 0){
                        output.write(Integer.toString(i) + " " + Integer.toString(j)+ "\n");
                    }
                }
            }

            dataFile.close();
        } catch (Exception e){ System.err.println(e);}
    }
}