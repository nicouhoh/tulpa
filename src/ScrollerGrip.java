import processing.core.PGraphics;
import processing.core.PApplet;

public class ScrollerGrip extends Organelle implements Draggish {

    Scroller scroller;
    int inactiveColor = 0xff6C6C6C, hotColor = 130;

    public ScrollerGrip(Scroller scroller){
        this.scroller = scroller;
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){
        x = parentX;
        w = parentW;
    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(getColor());
        g.rect(x, y, w, h);
    }

    public void setGrip(float scrollerH, float contentH, float latitude){
        y = (scrollerH * latitude) / contentH;
        h = (scrollerH * scrollerH) / contentH;
    }

    public void grab(){
        held = true;
    }

    public void drag(float dragX, float dragY, float offsetX, float offsetY){
        System.out.println("Dragging " + this);
        scroller.moveGrip(dragY - offsetY);
    }

    public void drawCasper(PGraphics g, float dragX, float dragY, float offsetX, float offsetY) {}

    public int getColor(){
        if (hot) return hotColor;
        else return inactiveColor;
    }

}
