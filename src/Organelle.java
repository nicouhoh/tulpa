import java.util.ArrayList;
import processing.core.PGraphics;

public abstract class Organelle {

    ArrayList<Organelle> children = new ArrayList<Organelle>();
    Organelle parent;

    Mousish mousish;
    Wheelish wheelish;

    float x, y, w, h;
    boolean hot, active, held;

    float latitude;

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

    public void drawAt(PGraphics g,  float drawX, float drawY, float drawW, float drawH){
        drawChildren(g);
    }

    public void draw(PGraphics g){
        drawChildren(g);
    }

    public void drawChildren(PGraphics g){
        for (Organelle child : getChildren()){
            child.draw(g);
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
    public void captureAndBubble(MouseState state){
        if (state.consumed) return;
        if (!mouseOver(state.getX(), state.getY() + state.getLatitude())) return;

        for (Organelle child : getChildren()){
            child.captureAndBubble(state);
            if (state.consumed) return;
        }
        receiveMouseState(state);
    }

    public void receiveMouseState(MouseState state){
        switch (state.getAction()){
            case MouseState.KEY -> {
                if (mousish == null) return;
                mousish.click();
                state.consume();
            }
            case MouseState.WHEEL -> {
                if (wheelish == null) return;
                wheelish.wheel(state.getCount());
                state.consume();
            }
        }
    }

//    public boolean receiveEvent(MouseState state){return null;}

    public void addMousish(Mousish mousish){
        this.mousish = mousish;
    }

    public void addWheelish(Wheelish wheelish){
        this.wheelish = wheelish;
    }

    public float getLatitude(){
        return 0;
    }

}