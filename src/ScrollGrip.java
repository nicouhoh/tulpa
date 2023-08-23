import processing.core.PGraphics;

public class ScrollGrip extends Organelle implements Clickish{

    int inactiveColor = 0xff6C6C6C, activeColor = 225;

    @Override
    public void draw(PGraphics g) {
        g.noStroke();
        g.fill(getColor());
        g.rect(x, y, w, h);
    }

    @Override
    public void shift() {
        ScrollRail rail = (ScrollRail)getParent();
        setBounds(rail.x, rail.gripY, rail.w, rail.gripH);
        //TODO update y + h based on Scroller's latitude and foot
    }

    int getColor(){
        if (active) return activeColor;
        else return inactiveColor;
    }

    @Override
    public void hot(){}

    @Override
    public void active() {
    }

    @Override
    public void click() {
    }
}