import processing.core.PGraphics;

public abstract class DropZone {

    float x, y, w, h;
    Droppish droppish;

    public DropZone(Droppish droppish, float x, float y, float w, float h){
        this.droppish = droppish;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void debugDraw(PGraphics g){
        g.fill(128, 128, 255, 44);
        g.stroke(0, 0, 255);
        g.strokeWeight(1);
        g.rect(x, y, w, h);
    }
}