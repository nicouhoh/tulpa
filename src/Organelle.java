import java.util.ArrayList;
import processing.core.PGraphics;

public abstract class Organelle implements Shape, DrawBehavior{

    private Organelle parent;
    private ArrayList<Organelle> children = new ArrayList<Organelle>();

    boolean hot;
    boolean active;

    float x, y, w, h;
    float scrollW = 0;
    float latitude = 0;

    public void performUpdate(PGraphics g){
        shift();
//        System.out.println("UPDATED " + this + " -- x: " + x + " y: " + y + " w: " + w + " h: " + h);
        draw(g);
        updateChildren(g);
    }

    public void updateChildren(PGraphics g){
        if (getChildren() != null) for (Organelle child : getChildren()) child.performUpdate(g);
    }

    public ArrayList<Organelle> getChildren(){
        return children;
    }

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

    public Organelle getParent(){
        return parent;
    }

    public void setParent(Organelle parent){
        this.parent = parent;
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
}