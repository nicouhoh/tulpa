import processing.core.PApplet;
import processing.core.PGraphics;

public class ScrollGrip extends Organelle implements Drawish {

    int inactiveColor = 0xff6C6C6C, hotColor = 130;

    public ScrollGrip(){
        clickish = new EmptyClickish();
        draggish = new ScrollGripDraggish();
    }

    @Override
    public void shift(float parentX, float parentY, float parentW, float parentH){
        x = getParent().x;
        w = getParent().w;
    }

    @Override
    public void draw(PGraphics g, float drawX, float drawY){
        g.noStroke();
        g.fill(getColor());
        g.rect(drawX, drawY, w, h);
    }

    int getColor(){
        if (hot || held) return hotColor;
        else return inactiveColor;
    }
}