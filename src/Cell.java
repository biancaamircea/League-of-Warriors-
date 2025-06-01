public class Cell {
    private CellEntityType type;
    private boolean visited;
    private boolean targeted;
    private boolean isPortal;
    private boolean hasEnemy;
    private boolean hasSanctuary;
    private boolean isEmpty;

    public Cell(CellEntityType type) {
        this.type = type;
        this.visited = false;
        this.targeted = false;
        this.isPortal = false;
        this.hasEnemy = false;
        this.hasSanctuary = false;
        this.isEmpty = false;
    }

    public Cell() {
        this.visited = false;
        this.targeted = false;
        this.isPortal = false;
        this.hasEnemy = false;
        this.hasSanctuary = false;
        this.isEmpty = false;
    }

    public CellEntityType getType() {
        return type;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        this.visited = true;
    }

    public void resetVisit() {
        this.visited = false;
    }

    public boolean isTargeted() {
        return targeted;
    }

    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }

    public boolean isPortal() {
        return isPortal;
    }

    public void setPortal(boolean isPortal) {
        this.isPortal = isPortal;
    }

    public boolean hasEnemy() {
        return hasEnemy;
    }

    public void setHasEnemy(boolean hasEnemy) {
        this.hasEnemy = hasEnemy;
    }

    public boolean hasSanctuary() {
        return hasSanctuary;
    }

    public void setHasSanctuary(boolean hasSanctuary) {
        this.hasSanctuary = hasSanctuary;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
        if (isEmpty) {
            this.hasEnemy = false;
            this.hasSanctuary = false;
        }
    }

    public void reset() {
        this.type = CellEntityType.EMPTY;
        this.visited = false;
    }

    public void setVisited(boolean b) {
    }
}
