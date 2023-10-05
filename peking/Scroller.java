import processing.core.PApplet;
import processing.core.PGraphics;
import java.util.ArrayList;

public class Scroller extends Decorator implements Drawish, Wheelish {
    ScrollRail rail;
    ScrollGrip grip;
    float latitude;
    float scrollW = 10;
    float scrollSpeed = 50;

    public Scroller(Organelle organelle){
        this.organelle = organelle;
        organelle.setParent(this);
        this.shape = organelle.shape;

        this.rail = new ScrollRail(this);
        this.grip = new ScrollGrip();
        this.addChild(rail);
        rail.addChild(grip);
    }

    //TODO SORT THIS STUFF OUT

    @Override
    public void shift(float parentX, float parentY, float parentW, float parentH){
        if (shape != null){
            shape.shift(this, parentX, parentY, parentW, parentH);
        }
    }

    @Override
    public void update(PGraphics g, Conductor c, float parentX, float parentY, float parentW, float parentH) {
        g.push();
        g.translate(0, -getLatitude());
        organelle.update(g, c, parentX, parentY, parentW - scrollW, parentH);
        g.pop();

        shift(parentX, parentY, parentW, parentH);
        updateGrip();
        updateChildren(g, c);
    }

    @Override
    public void draw(PGraphics g, float x, float y) {

    }

    @Override
    public void wheel(float scrollAmount){
        latitude = PApplet.constrain(getLatitude() + (scrollAmount * scrollSpeed), 0, organelle.h - h);
    }

    private void moveGrip(){
        grip.y = (h * getLatitude()) / organelle.h;
        grip.h = (h * h) / organelle.h;
    }

    void updateGrip(){
        if (!grip.held){
            moveGrip();
        } else {
            latitude = (organelle.h * grip.y) / rail.h;
        }
    }

    public Organelle findDeepest(float mouseX, float mouseY){
        if (!theFingerPointsAtMe(mouseX, mouseY)) return null;
        Organelle deepestHit = null;
        if (clickish != null) deepestHit = this;

        Organelle childResult = null;
        if (mouseX > w - scrollW){
            for (Organelle child : getChildren()){
                childResult = child.findDeepest(mouseX, mouseY);
                if (childResult != null) deepestHit = childResult;
            }
        } else{
            for (Organelle child : organelle.getChildren()){
                childResult = child.findDeepest(mouseX, mouseY + getLatitude());
                if (childResult != null) deepestHit = childResult;
            }
        }

        return deepestHit;
    }

    @Override
    public float getLatitude(){
        return getParent().getLatitude() + latitude;
    }
}
