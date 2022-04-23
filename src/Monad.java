import processing.core.PGraphics;
import java.util.ArrayList;

public abstract class Monad {

    float x;
    float y;
    float w;
    float h;

    boolean onscreen;

    ArrayList<Monad> children = new ArrayList<Monad>();
    Monad parent;

    public void draw(PGraphics g){};
    public void update(){};
    public void cascadeUpdate(){
        update();
        if (children == null) return;
        for (Monad c : children){
            c.cascadeUpdate();
        }
    }

    // Generally we call this on Cockpit, and it tumbles down from there.
    public void cascadeDraw(PGraphics g, float latitude){
        if(isOnscreen(latitude)) {
            draw(g);
            if (children == null) return;
            for (Monad c : children) {
                c.cascadeDraw(g, latitude);
            }
        }
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float newW, float newH) {
        w = newW;
        h = newH;
    }

    public void setBounds(float x, float y, float w, float h){
        setPos(x, y);
        setSize(w, h);
    }

    public ArrayList<Monad> getChildren(){
       return children;
    }

    public boolean isOnscreen(float latitude) {
        if (y < parent.y + parent.h && y + h >= parent.y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean pinPoint(float pinX, float pinY, float latitude){
//        pinY -= latitude;
        //            monadDebugInfo();
        return pinX >= x && pinX <= x + w
                && pinY >= y && pinY <= y + h;
    }

    public Monad getChildAtPoint(float pointX, float pointY, float latitude){
        if (children.size() < 1) return this;
        for (Monad m : children){
            if (!m.isOnscreen(latitude)) continue;
            if (m.pinPoint(pointX, pointY, latitude)){
                return m.getChildAtPoint(pointX, pointY, latitude);
            }
        }
        return this;
    }

    public Scrollable getScrollableAtPoint(float pointX, float pointY, float latitude){
        Scrollable out = null;
        if (this instanceof Scrollable) out = (Scrollable)this;

        if (children.size() >= 1) {
            for (Monad m : children) {
                if (!m.isOnscreen(latitude)) continue;
                if (m.pinPoint(pointX, pointY, latitude)) {
                    Scrollable littleOut = m.getScrollableAtPoint(pointX, pointY, latitude);
                    if (littleOut != null) out = littleOut;
                }
            }
        }
        return out;
    }

    public Clickable getClickableAtPoint(float pointX, float pointY, float latitude){
        Clickable out = null;
        if (this instanceof Clickable) out = (Clickable)this;

        if (children.size() >= 1) {
            for (Monad m : children) {
                if (!m.isOnscreen(latitude)) continue;
                if (m.pinPoint(pointX, pointY, latitude)) {
                    Clickable littleOut = m.getClickableAtPoint(pointX, pointY, latitude);
                    if (littleOut != null) out = littleOut;
                }
            }
        }
        return out;
    }
//    public Clickable getClickableAtPoint(float pointX, float pointY, float latitude){
//        Clickable output = null; //TODO This will set output to null instead of finding non-bottom-level targets!
//        if (this instanceof Clickable) output = (Clickable)this;
//        if (children.size() < 1) return output;
//        for (Monad m : children){
//            if (!m.isOnscreen(latitude)) continue;
//            if (m.pinPoint(pointX, pointY)){
//                return m.getClickableAtPoint(pointX, pointY, latitude);
//            }
//        }
//        return output;
//    }


    public float scrollWidth(){
        return 0;
    }

    public void monadDebugInfo(){
        System.out.println();
        System.out.println(this);
        System.out.println(x + ", " + y);
        System.out.println(w + " x " + h);
        System.out.println("parent: " + parent);
        System.out.println("children: " + children);
    }

}