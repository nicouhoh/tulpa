import processing.core.PGraphics;
import processing.core.PVector;

public abstract class Dropzone {

    float x, y, w, h;
    float allowance = 20;
    Droppish droppish;
    boolean hovered;



    public Dropzone(Droppish droppish, float x, float y, float w, float h){
        this.droppish = droppish;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(PGraphics g){}

    public void debugDraw(PGraphics g){
        if (!hovered){
            g.fill(128, 128, 255, 44);
            g.stroke(0, 0, 255);
        }
        else {
            g.fill(255, 128, 255, 44);
            g.stroke(255, 0, 255);
        }

        g.strokeWeight(1);
        g.rect(x, y, w, h);
    }

    // TODO hovering dropzones is a mess for now, clean it up someday.
    public void onHovered(){
        System.out.println("Hovering " + this);
    }

    public void setHovered(boolean h){
        hovered = h;
    }
}