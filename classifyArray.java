import java.util.concurrent.RecursiveTask;

public class classifyArray extends RecursiveTask<Integer>{
    int lo; int rhi; int chi; int seqCut; // arguments        moving across rows...
    float[][] grid; float[][] ans;
    int basinNr = 0;
    static final int SEQUENTIAL_CUTOFF=2;

    classifyArray(float[][] a, int lo, int rhi, int chi, float[][] clas, int seqCut){
        grid = a; ans = clas; this.lo = lo; this.rhi = rhi; this.chi = chi; this.seqCut = seqCut;
        main.threads ++;
        System.out.println(rhi + "/Columns: " + lo + "/threads: " + main.threads);
    }

    protected Integer compute() {
        if ((rhi - lo) < SEQUENTIAL_CUTOFF){
            //this.basinNr = 0;
            for (int i = lo; i < rhi; i++){
                for (int j = 1; j < chi; j++){
                    //System.out.println("row/Col " + i + "/" + j);
                    if (determineBasin(i, j))
                    {
                        System.out.println("made it @ row/Col " + i + "/" + j);
                        basinNr++;
                        ans[i][j] = grid[i][j];
                    }
                    else ans[i][j] = 0;
                }
                
            }
            return basinNr;
        }
        else {
            classifyArray left = new classifyArray(grid, lo, (rhi+lo)/2, chi, ans, seqCut);
            classifyArray right = new classifyArray(grid, (rhi+lo)/2, rhi, chi, ans, seqCut);

            left.fork();
            int rightAns = right.compute();
            int leftAns = left.join();
            return leftAns + rightAns;
        }
    }

    private Boolean determineBasin(int i, int j){
        if ((grid[i+1][j+1] - grid[i][j] >= 0.01) && (grid[i-1][j+1] - grid[i][j] >= 0.01) && (grid[i+1][j] - grid[i][j] >= 0.01)
                    && (grid[i][j+1] - grid[i][j] >= 0.01) && (grid[i-1][j] - grid[i][j] >= 0.01) && (grid[i+1][j-1] - grid[i][j] >= 0.01) && (grid[i][j-1] - grid[i][j] >= 0.01)
                    && (grid[i-1][j-1] - grid[i][j] >= 0.01)){
                        return true;
                    }
        else return false;

    }
}