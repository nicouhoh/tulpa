import processing.core.PApplet;
import processing.core.PGraphics;

public class Scroller extends Symbiote implements Mousish, Wheelish {

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
        addMousish(this);
        addWheelish(this);
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
    public void captureAndBubble(MouseState state){
        if (state.consumed) return;
        if (!mouseOver(state.getX(), state.getY() + state.getLatitude())) return;

        MouseState adjustedState = new MouseState(state, latitude);

        for (Organelle child : getChildren()){
            child.captureAndBubble(adjustedState);
            if (adjustedState.consumed){
                state.consume();
                return;
            }
        }

        rail.captureAndBubble(state);

        receiveMouseState(state);
    }

    public void wheel(int count){
        changeLatitude(count * scrollSpeed);
    }

    @Override
    public void click() {
        System.out.println("clicked " + this);
    }
}
