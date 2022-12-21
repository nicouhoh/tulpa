import processing.core.PApplet;
import processing.core.PGraphics;

public class Grip extends Monad implements Clickable, Draggable{

    int color;

    float grabY;
    Field field;

    public Grip(Scroller parent){

        this.parent = parent;
        parent.children.add(this);
        field = (Field)parent.parent; //TODO What an eyesore. Smite this one day.
        setPos(parent.x, parent.y);
        w = parent.parent.scrollWidth();
        color = 0xff6C6C6C;
        grabDist = 0;
    }

    @Override
    public boolean isOnscreen(float latitude) {
        if (y < latitude + parent.h && y + h >= parent.y) {
            return true;
        } else {
            System.out.println("GRIP OFFSCREEN");
            return false;
        }
    }

    @Override
    public void draw(PGraphics g){
        g.fill(this.color);
        g.rect(x, y, w, h);
    }

    @Override
    public void setPos(float x, float y){
        this.x = x;
        this.y = PApplet.constrain(y, parent.y, parent.h - h);
    }

    public void updateGripSize(float lat, float foot){
        setSize(parent.w, PApplet.constrain(parent.h / foot * parent.h, 0, parent.h));
    }

    public void updateGripPos(float lat, float foot){
        setPos(parent.x, PApplet.constrain(lat / foot * parent.h, 0, parent.h - h));
    }

    public void setGripPos(float targetH, float gripY){ // put gripY at newH
        setPos(parent.x, targetH - gripY);
    }

    // normally the grip's position is determined by latitude.
    // but when the grip be gripped, the flow reverses.
    public void setGrabY(Operator operator, float mouseGrabY){
        grabY = mouseGrabY - y;
    }

    @Override
    public void grabbed(Operator operator, int mod, float grabbedX, float grabbedY, Callosum c){
        setGrabY(operator, grabbedY);
    }

    @Override
    public void dragged(Operator operator, int mod, float dragX, float dragY, float lockedX, float lockedY, Callosum c){
        setPos(parent.x, dragY - grabY);
        field.followScroller();
    }

    @Override
    public void dropped(Operator operator, int mod, float dropX, float dropY, Callosum c){
        inClutches = false;
    }
}
