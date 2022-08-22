public class Matrix {
    private int[][] matrix;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols){
        this.matrix = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public void fill(){
        int value = 0;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                matrix[i][j] = value++;
            }
        }
    }

    public void moveEvil(int[] coordinates){
        int x = coordinates[0];
        int y = coordinates[1];

        while (x >= 0 && y >= 0)
        {

            if (x >= 0 && x < this.rows && y >= 0 && y < this.cols)
            {
                matrix[x][y] = 0;
            }

            x--;
            y--;
        }
    }

    public long collectStars(int[] coordinates){
        int x = coordinates[0];
        int y = coordinates[1];

        long sum = 0;

        while (x >= 0 && y < this.cols)
        {

            if (x >= 0 && x <this.rows && y >= 0 && y < this.cols)
            {
                sum += matrix[x][y];
            }

            y++;
            x--;
        }

        return sum;
    }
}
