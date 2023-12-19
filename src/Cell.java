public class Cell extends Organelle {

    //TODO I'm not sure if this should really subclass Organelle. it might should just be a bounding box to use and that's it

    public Cell(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){}

    public Cell divideTop(float cutH){
        Cell newCell = new Cell(x, y, w, cutH);
        setBounds(x, y + cutH, w, h - cutH);
        return newCell;
    }

    public Cell divideBottom(float cutH){
        Cell newCell = new Cell(x, y + h - cutH, w, cutH);
        setBounds(x, y, w, h - cutH);
        return newCell;
    }

    public Cell divideLeft(float cutW){
        Cell newCell = new Cell(x, y, cutW, h);
        setBounds(x + cutW, y, w - cutW, h);
        return newCell;
    }

    public Cell divideRight(float cutW){
        Cell newCell = new Cell(x + w - cutW, y, cutW, h);
        setBounds(x, y, w - cutW, h);
        return newCell;
    }

    public Cell shrink(float shrinkX, float shrinkY){
        return new Cell(x + shrinkX, y + shrinkY, w - 2 * shrinkX, h - 2 * shrinkY);
    }
}
