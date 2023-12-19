import java.util.ArrayList;
import processing.core.PGraphics;
import processing.core.PVector;

// Abstract base for components

public abstract class Organelle {

    ArrayList<Organelle> children = new ArrayList<Organelle>();
    Organelle parent;

    ArrayList<Mousish> mousishes = new ArrayList<Mousish>();
    Hoverish hoverish;
    Wheelish wheelish;
    Draggish draggish;
    Keyish keyish;

    ArrayList<Dropzone> dropZones = new ArrayList<Dropzone>();


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

    public void performUpdate(float parentX, float parentY, float parentW, float parentH){
        resize(parentX, parentY, parentW, parentH);
        updateChildren();
    }

    public void resize(float parentX, float parentY, float parentW, float parentH){
        // Override this for update behavior specific to an Organelle. Actually gets called in performUpdate().
        // The default behavior is to fill the parent Organelle.
        // TODO ^^ actually might not be true! I might be in the middle of changing this!
//        setPos(parentX, parentY);
//        setSize(parentW, parentH);
    }

    public void updateChildren(){
        for (Organelle child : getChildren()){
            child.performUpdate(x, y, w, h);
        }
    }

    public void performDraw(PGraphics g, float trimMin, float trimMax){
        // if we're scrolled, translate before drawing. Otherwise, don't bother.
        // Call this to draw, but override draw() for actual draw behavior.
        if (latitude != 0){
            g.push();
            g.translate(0, -latitude);
            draw(g);
            drawChildren(g, trimMin, trimMax);
            drawAfter(g);
            g.pop();
        } else {
            draw(g);
            drawChildren(g, trimMin, trimMax);
            drawAfter(g);
        }
    }

    public void draw(PGraphics g){
        // Override this for the draw behavior of each Organelle. It actually gets called in performDraw()
    }

    public void drawAfter(PGraphics g){
        // Like draw, but draws *after* children. Good for things like the contact sheet drawing dropzones.
    }

    public void drawChildren(PGraphics g, float clipMin, float clipMax){
        for (Organelle child : getChildren()){
            if (child.y > clipMax + latitude) return;
            if (child.y + child.h < clipMin + latitude) continue;
            child.performDraw(g, clipMin, clipMax);
        }
    }

    public void setBounds(float x, float y, float w, float h){
        setPos(x, y);
        setSize(w, h);
    }

    public void setBounds(Cell c){
        setPos(c.x, c.y);
        setSize(c.w, c.h);
    }

    public void setPos(float newX, float newY){
        x = newX;
        y = newY;
    }

    public void setSize(float newW, float newH){
        w = newW;
        h = newH;
    }

    public void setSize(PVector v){
        w = v.x;
        h = v.y;
    }

    public void drawDebug(PGraphics g){
        g.stroke(255, 0, 255);
        g.noFill();
        g.rect(x, y, w - 1, h - 1);
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

    public float getLatitude(){
        return latitude;
    }

    public void setLatitude(float lat){
        latitude = lat;
    }

    public void setHot(boolean hot){
        this.hot = hot;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public void mirage(PGraphics g, float x, float y, float w, float h){}

}