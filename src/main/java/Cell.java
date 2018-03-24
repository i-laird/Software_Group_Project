import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Cell {
    private int row;
    private int col;

    /**
     * @author: Ian Laird
     * @param r the row of the Cell
     * @param c the column of the Cell
     */
    public Cell(int r, int c){
        row = r;
        col = c;
    }

    /**
     * creates a Cell in a random location in the table
     * @author:Ian Laird
     * @param width of the table
     * @param height of the table
     * @return newly generated Cell
     */
    public static Cell createRandom(int width, int height){
        return new Cell(ThreadLocalRandom.current().nextInt(0, width + 1),
                ThreadLocalRandom.current().nextInt(0, height + 1));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row &&
                col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
