package map;

public class Cell {
    private int x;
    private int y;
    private CellEntityType type;
    private boolean visited;

    public Cell(int x, int y, CellEntityType type, boolean visited) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.visited = visited;
    }

    public void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellEntityType getType() {
        return type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Cell: " + "x = " + x + " y = " + y +
                " type = " + type + " status = " + visited;
    }
}
