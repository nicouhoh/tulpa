import java.util.ArrayList;
import processing.core.PGraphics;

public abstract class Organelle {

    private Organelle parent;
    private ArrayList<Organelle> children = new ArrayList<Organelle>();
    private float foot;
    private float latitude;
    float x, y, w, h;

    public void update(PGraphics g){
        if (!(this instanceof Thumbnail))System.out.println("UPDATING " + this + " -- " + "x: " + x + " y: " + y + " w: " + w + " h: " + h);
        draw(g);
        for (Organelle child : getChildren()) child.update(g);
    };

    abstract void draw(PGraphics g);

    public void addChild(Organelle child){
        children.add(child);
        child.setParent(this);
    }

    public void addChildren(ArrayList<? extends Organelle> children){
        this.children.addAll(children);
        for (Organelle child : children){
            child.setParent(this);
        }
    }

    public void setParent(Organelle parent){
        this.parent = parent;
    }

    public Organelle getParent(){
        return parent;
    }

    public ArrayList<Organelle> getChildren(){
        return children;
    }

    public void setBounds(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void liquidBounds(){
        this.x = parent.x;
        this.y = parent.y;
        this.w = parent.w;
        this.h = parent.h;
    }

    public float getLatitude(){
        return latitude;
    }

    public void setLatitude(float latitude){
        this.latitude = latitude;
    }

    public float getFoot(){
        return foot;
    }

    public void setFoot(float bottomOfScroll){
        foot = bottomOfScroll;
    }

}