public class Cell {

    //TODO I'm not sure if this should really subclass Organelle. it might should just be a bounding box to use and that's it

    float x, y, w, h;

    public Cell(float x, float y, float w, float h){
        setBounds(x, y, w, h);
    }

    public void setBounds(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

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

    public Cell shrink(float margin){
        return shrink(margin, margin);
    }

    public Cell fit(float fitW, float fitH){
        float resultW = w;
        float resultH = h;
        if (fitW != 0) resultW = fitW;
        if (fitH != 0) resultH = fitH;
        return new Cell(x + (w - resultW)/2, y + (h - resultH)/2, resultW, resultH);
    }
}
