package Directions;

import resources.Cell;
import resources.Snake;

public class Right implements Direction {
    @Override
    public Cell performUpdate(Snake s) {
        return new Cell(s.getHeadLocation().getRow(), s.getHeadLocation().getCol() + 1);
    }

    public static Direction create(){
        return new Right();
    }
}
