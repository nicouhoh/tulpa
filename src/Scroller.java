import processing.core.PApplet;
import processing.core.PGraphics;

public class Scroller extends Organelle implements Mousish, Wheelish {

    Organelle host;
    ScrollerRail rail;
    ScrollerGrip grip;

    float scrollW = 10;
    float scrollSpeed = 50;

    public Scroller(Organelle organelle) {
        host = organelle;
        rail = new ScrollerRail(this);
        grip = new ScrollerGrip(this);
        addChild(rail);
        addChild(host);
        rail.addChild(grip);
        addMousish(this);
        addWheelish(this);
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){
        setPos(parentX, parentY);
        setSize(parentW, parentH);
        updateChildren();
    }

    @Override
    public void updateChildren(){
        host.update(x, y, w - scrollW, h);
        rail.update(x, y, w, h);
        grip.setGrip(h, host.h, host.latitude);
    }

    public void changeHostLatitude(float amount){
        host.latitude = PApplet.constrain(host.latitude + amount, 0, host.h - h);
        grip.setGrip(rail.h, host.h, host.latitude);
    }

    public void setHostLatitudeToGrip(){
        host.latitude = (grip.y * host.h) / h;
    }

    public void moveGrip(float y){
        grip.y = PApplet.constrain(y, this.y, this.h - grip.h);
        setHostLatitudeToGrip();
    }

//    @Override
//    public void captureAndBubble(MouseState state){
//        if (state.consumed) return;
//        if (!mouseOver(state.getX(), state.getY() + state.getLatitude())) return;
//
//        MouseState adjustedState = new MouseState(state, latitude);
//
//        for (Organelle child : getChildren()){
//            child.captureAndBubble(adjustedState);
//            if (adjustedState.consumed){
//                state.consume();
//                return;
//            }
//        }
//
//        rail.captureAndBubble(state);
//
//        receiveMouseState(state);
//    }

    public void wheel(int count){
        changeHostLatitude(count * scrollSpeed);
    }

    @Override
    public void click() {
        System.out.println("clicked " + this);
    }
}
