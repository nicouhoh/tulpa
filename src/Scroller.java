import processing.core.PApplet;
import processing.core.PGraphics;

public class Scroller extends Symbiote implements Wheelish {

    ScrollerRail rail;
    ScrollerGrip grip;

    float latitude = 0;
    float scrollW = 10;
    float scrollSpeed = 50;

    public Scroller(Organelle organelle) {
        this.host = organelle;
        rail = new ScrollerRail(this);
        grip = new ScrollerGrip(this);
        rail.addChild(grip);
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH) {
        setPos(parentX, parentY);
        setSize(parentW, parentH);
        host.update(parentX, parentY, parentW - scrollW, parentH);
        updateChildren();
        grip.setGrip(h, host.h, latitude);
    }

    @Override
    public void updateChildren(){
        rail.update(x, y, w, h);
        host.updateChildren();
    }

    @Override
    public void draw(PGraphics g){
        g.push();
        g.translate(0, -getLatitude());
        host.draw(g);
        g.pop();
        rail.draw(g);
    }

    @Override
    public float getLatitude(){
        return latitude;
    }

    public void changeLatitude(float amount){
        latitude = PApplet.constrain(latitude + amount, 0, host.h - h);
        grip.setGrip(rail.h, host.h, latitude);
    }

    public void setLatitudeToGrip(){
        latitude = (grip.y * host.h) / h;
    }

    public void moveGrip(float y){
        grip.y = PApplet.constrain(y, this.y, this.h - grip.h);
        setLatitudeToGrip();
    }

    @Override
    public Organelle pinpoint(MouseState state, Class<? extends Palpable> palp) {
        if (!checkMouseOver(state.getX(), state.getY() + state.getLatitude())) return null;
        for (Organelle child : getChildren()) {
            state.addLatitude(latitude);
            Organelle childResult = child.pinpoint(state, palp);
            if (childResult == null) childResult = rail.pinpoint(state, palp);
            if (childResult != null) return childResult;
        }
        if (palp.isInstance(this)) return this;
        else return null;
    }

    public void wheel(int count){
        changeLatitude(count * scrollSpeed);
    }
}
