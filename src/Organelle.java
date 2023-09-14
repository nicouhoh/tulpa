import java.util.ArrayList;
import processing.core.PGraphics;

public abstract class Organelle implements Drawish {

    private Organelle parent;
    private ArrayList<Organelle> children = new ArrayList<Organelle>();

    public Shape shape = null;
    public Clickish clickish = null;
    public Draggish draggish = null;
    public Droppish droppish = null;

    boolean hot;
    boolean active;
    boolean held;
    float dragX, dragY;

    float x, y, w, h;

    public ArrayList<Organelle> getChildren() {
        return children;
    }

    public void addChild(Organelle child) {
        children.add(child);
        child.setParent(this);
    }

    public void addChildren(ArrayList<? extends Organelle> children) {
        this.children.addAll(children);
        for (Organelle child : children) {
            child.setParent(this);
        }
    }

    public Organelle getParent() {
        return parent;
    }

    public void setParent(Organelle parent) {
        this.parent = parent;
    }

    public void drawDebug(PGraphics g) {
        g.stroke(255, 0, 255);
        g.noFill();
        g.rect(x - 1, y - 1, w + 2, h + 2);
    }

    public void shift(float parentX, float parentY, float parentW, float parentH) {
        if (shape != null) {
            shape.shift(this, parentX, parentY, parentW, parentH);
        }
    }

    public void update(PGraphics g, Conductor c, float parentX, float parentY, float parentW, float parentH) {
        shift(parentX, parentY, parentW, parentH);
        draw(g, x, y);
        updateChildren(g, c);
    }

    public void updateChildren(PGraphics g, Conductor c) {
        if (getChildren() != null) {
            for (Organelle child : getChildren()) {
                child.update(g, c, x, y, w, h);
            }
        }
    }

    public void draw(PGraphics g, float x, float y){}

    public void passMouseInput(float mouseX, float mouseY, int action) {
        if (!theFingerPointsAtMe(mouseX, mouseY)) return;
    }

    public Organelle findDeepest(float mouseX, float mouseY){
        if (!theFingerPointsAtMe(mouseX, mouseY)) return null;
        Organelle deepestHit = null;
        if (clickish != null) deepestHit = this;
        if (getChildren().size() > 0){
            for (Organelle child : getChildren()){
                Organelle childResult = child.findDeepest(mouseX, mouseY);
                if (childResult != null) deepestHit = childResult;
            }
        }
        return deepestHit;
    }

    public Wheelish findDeepestWheelish(float mouseX, float mouseY){
        if (!theFingerPointsAtMe(mouseX, mouseY)) return null;
        Wheelish deepestHit = null;
        if (this instanceof Wheelish) deepestHit = (Wheelish)this;
        if (getChildren().size() > 0){
            for (Organelle child : getChildren()){
                Wheelish childResult = child.findDeepestWheelish(mouseX, mouseY);
                if (childResult != null) deepestHit = childResult;
            }
        }
        return deepestHit;
    }

    public boolean theFingerPointsAtMe(float fingerX, float fingerY) {
        if (fingerX < x || fingerX > x + w || fingerY < y || fingerY > y + h) {
            return false;
        } else {
            return true;
        }
    }
}