import java.util.ArrayList;
import processing.core.PGraphics;

// Abstract base for components

public abstract class Organelle {

    ArrayList<Organelle> children = new ArrayList<Organelle>();
    Organelle parent;

    ArrayList<Mousish> mousishes = new ArrayList<Mousish>();
    Hoverish hoverish;
    Wheelish wheelish;
    Draggish draggish;

    Katla katla;

    float x, y, w, h;
    float latitude = 0;

    boolean hot, active, held;

    public void addChild(Organelle organelle){
        children.add(organelle);
    }

    public void addChildren(ArrayList<Organelle> organelles){
        children.addAll(organelles);
    }

    public void removeChild(Organelle organelle){
        children.remove(organelle);
    }

    public Organelle getChild(int index){
        return children.get(index);
    }

    public ArrayList<Organelle> getChildren(){
        return children;
    }

    public void update(float parentX, float parentY, float parentW, float parentH){
        setPos(parentX, parentY);
        setSize(parentW, parentH);
        updateChildren();
    }

    public void updateChildren(){
        for (Organelle child : getChildren()){
            child.update(x, y, w, h);
        }
    }

    public void performDraw(PGraphics g){
        // if we're scrolled, translate before drawing. Otherwise, don't bother.
        // Call this to draw, but override draw() for actual draw behavior.
        if (latitude != 0){
            g.push();
            g.translate(0, -latitude);
            draw(g);
            drawChildren(g);
            g.pop();
        } else {
            draw(g);
            drawChildren(g);
        }
    }

    public void draw(PGraphics g){
        // Override this for the draw behavior of each Organelle. It actually gets called in performDraw()
    }

    public void drawChildren(PGraphics g){
        for (Organelle child : getChildren()){
            child.performDraw(g);
        }
    }

    public void setPos(float newX, float newY){
        x = newX;
        y = newY;
    }

    public void setSize(float newW, float newH){
        w = newW;
        h = newH;
    }

    public void drawDebug(PGraphics g){
        g.stroke(255, 0, 255);
        g.noFill();
        g.rect(x, y, w - 1, h - 1);
    }

    public boolean mouseOver(float mouseX, float mouseY){
        return (mouseX > x) && (mouseX < x + w) && (mouseY > y) && (mouseY < y + h);
    }

//    // I thought I was going to cry from figuring out this method ;__;
//    public Organelle pinpoint(MouseState state, Class<? extends Palpable> palp) {
//        if (!mouseOver(state.getX(), state.getY() + state.getLatitude())) return null;
//        for (Organelle child : getChildren()) {
//            Organelle childResult = child.pinpoint(state, palp);
//            if (childResult != null) return childResult;
//        }
//        if (palp.isInstance(this)) return this;
//        else return null;
//    }

    // It took me more than a week to get here, I want to cry
    public void captureAndBubble(Squeak state){
        if (state.consumed) return;
        if (!mouseOver(state.getX(), state.getY() + state.getLatitude())) return;

        for (Organelle child : getChildren()){
            child.captureAndBubble(state);
            if (state.consumed) return;
        }
        handleMouseState(state);
    }

//    public Palpable captureAndBubbleNEW(Squeak squeak, Mouse mouse){
//        if (squeak.consumed) return null;
//        if (!mouseOver(squeak.getX(), squeak.getY() + squeak.getLatitude())) return null;
//
//        Palpable deepestWithListener = null;
//
//        for (Organelle child : getChildren()){
//            Palpable result = child.captureAndBubbleNEW(squeak,mouse);
//            if (result != null) deepestWithListener = result;
//            if (squeak.consumed) return deepestWithListener;
//        }
//
//        if (deepestWithListener == null){
//            deepestWithListener = handleMouseState(squeak);
//        }
//
//        return deepestWithListener;
//    }

    public void handleMouseState(Squeak state){ // TODO could we do this stuff better by subclassing the MouseState?
        switch (state.getAction()){
            case Squeak.KEY -> {
                for (Mousish mousish : mousishes) {
                    mousish.click();
                    state.consume();
                }
            }
            case Squeak.WHEEL -> {
                if (wheelish == null) return;
                wheelish.wheel(state.getCount());
                state.consume();
            }
            case Squeak.DRAG -> {
                if (draggish == null) return;
                state.katla.handleDrag(draggish, state.getX(), state.getY());
                state.consume();
            }
        }
    }

//    public boolean receiveEvent(MouseState state){return null;}

    public void addMousish(Mousish mousish){
        mousishes.add(mousish);
    }

    public void addWheelish(Wheelish wheelish){
        this.wheelish = wheelish;
    }

    public void addDraggish(Draggish draggish){
        this.draggish = draggish;
    }

    public void registerKatla(Katla katla){
        this.katla = katla;
    }

    public float getLatitude(){
        return latitude;
    }

}