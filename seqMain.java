import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Locale;

public class seqMain {
    static long startTime = 0;
    static public int threads = 0;

	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	private static long tock(){
		return (System.currentTimeMillis() - startTime);
    }
    
    public static void main(String [] args)
    {
        String inputFile = args[0]; String outputFile = args[1];
        float[][] grid = null; float[][] classified = null; //grid for inputs.
        int cols, rows, basins = 0;
        long[] time = new long[20];
        

        try{
            File fIn = new File(inputFile);
            Scanner dataFile = new Scanner(fIn).useLocale(Locale.US);
            rows = dataFile.nextInt(); cols = dataFile.nextInt();
            grid = new float[rows][cols]; classified = new float[rows][cols];//instantiate 2d array.
            
            for (int i = 0; i <= rows-1; i++){
                for (int j = 0; j <= cols-1; j++){
                    grid[i][j] = dataFile.nextFloat();
                }
            }

            String benchmarkFile = outputFile;
            if (benchmarkFile.contains(".txt"))
                benchmarkFile = benchmarkFile.replace(".txt", "");
            File fBenchmark = new File(benchmarkFile + "_Benchmark.txt");
            FileWriter outputter = new FileWriter(fBenchmark, true);

            for (int k=0; k<20; k++){
                basins = 0;
                System.gc();
                tick();
                    for (int i = 1; i < rows-1; i++){
                        for (int j = 1; j < cols-1; j++){
                            if (determineBasin(i, j, grid))
                            {
                                basins++;
                                classified[i][j] = grid[i][j];
                            }
                            else classified[i][j] = 0;
                        }
                        
                    }
                time[k] = tock();
                outputter.append(Long.toString(time[k])+ "\n");
            }
            outputter.close();

            File fOut = new File(outputFile);
            FileWriter output = new FileWriter(fOut);
            String text = Integer.toString(basins) + "\n";

            for (int i = 0; i <= rows-1; i++){
                for (int j = 0; j <= cols-1; j++){
                    if (classified[i][j] != 0){
                        text += Integer.toString(i) + " " + Integer.toString(j)+ "\n";
                    }
                }
            }

            output.write(text);
            output.close();
            dataFile.close();
        } catch (Exception e){ System.err.println(e);}
    }

    private static Boolean determineBasin(int i, int j, float[][] grid){
        if ((grid[i+1][j+1] - grid[i][j] >= 0.01) && (grid[i-1][j+1] - grid[i][j] >= 0.01) && (grid[i+1][j] - grid[i][j] >= 0.01)
                    && (grid[i][j+1] - grid[i][j] >= 0.01) && (grid[i-1][j] - grid[i][j] >= 0.01) && (grid[i+1][j-1] - grid[i][j] >= 0.01) && (grid[i][j-1] - grid[i][j] >= 0.01)
                    && (grid[i-1][j-1] - grid[i][j] >= 0.01)){
                        return true;
                    }
        else return false;

    }
}
