import processing.core.PApplet;
import processing.core.PGraphics;

public class ScrollGrip extends Organelle implements Clickish, Draggish{

    int inactiveColor = 0xff6C6C6C, hotColor = 130;

    @Override
    public void draw(PGraphics g) {
        g.noStroke();
        g.fill(getColor());
        g.rect(x, y, w, h);
    }

    @Override
    public void shift() {
        x = getParent().x;
        w = getParent().w;
    }

    int getColor(){
        if (hot || held) return hotColor;
        else return inactiveColor;
    }

    @Override
    public void hot(){}

    @Override
    public void active(){}

    @Override
    public void click(){}

    @Override
    public void drag(float mouseX, float mouseY){
        y = PApplet.constrain(mouseY - dragY, getParent().y, getParent().h - h);
    }

    @Override
    public void release(){}
}