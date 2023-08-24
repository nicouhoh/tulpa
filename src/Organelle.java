import java.util.ArrayList;
import processing.core.PGraphics;

public abstract class Organelle implements Shape, DrawBehavior{

    private Organelle parent;
    private ArrayList<Organelle> children = new ArrayList<Organelle>();

    boolean hot;
    boolean active;
    boolean held;
    float dragX, dragY;

    float x, y, w, h;
    float scrollW = 0;
    float latitude = 0;

    public void performUpdate(PGraphics g){
        shift();
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

    public void drawDebug(PGraphics g){
        g.stroke(255, 0, 255);
        g.noFill();
        g.rect(x - 1, y - 1, w + 2, h + 2);
    }
}