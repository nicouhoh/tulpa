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
    Keyish keyish;

    ClawMachine katla;

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

    public void addMousish(Mousish mousish){
        mousishes.add(mousish);
    }

    public void addWheelish(Wheelish wheelish){
        this.wheelish = wheelish;
    }

    public void addDraggish(Draggish draggish){
        this.draggish = draggish;
    }

    public void addKeyish(Keyish keyish){
        this.keyish = keyish;
    }

    public void registerKatla(ClawMachine katla){
        this.katla = katla;
    }

    public float getLatitude(){
        return latitude;
    }

}