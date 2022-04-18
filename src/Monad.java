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
    public void mouseOver(){}


    public void cascadeUpdate(){
        update();
        if (children == null) return;
        for (Monad c : children){
            c.cascadeUpdate();
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

    public boolean isOnscreen(float latitude) {
        if (y < latitude + tulpa.SOLE.height && y >= latitude - h * 1.5) {
            return true;
        } else {
            return false;
        }
    }


    public Monad spearMonad(float spearX, float spearY, float latitude) {
        if (children.size() == 0) return this;
        for (Monad school : children) {
            if (!school.isOnscreen(latitude)) continue;
            if (!school.bullseye(school, spearX, spearY, latitude)) continue;
            return school.spearMonad(spearX, spearY, latitude);
        }
        return null;
    }



//        for (Monad school : children) {
//            if (!school.isOnscreen(latitude)) continue;
//            if (!school.bullseye(school, spearX, spearY, latitude)) continue;
//            if (school.children.size() > 0) {
//                return school.spearMonad(spearX, spearY, latitude);
//            }
//            return school;
//        }
//        return null;
//    }

//    public Monad spearMonad(float spearX, float spearY, float latitude) {
//        for (Monad school : children) {
//            if (!school.isOnscreen(latitude)) continue;
//            if (!school.bullseye(school, spearX, spearY, latitude)) continue;
//            if (school.children.size() > 0) {
//                return school.spearMonad(spearX, spearY, latitude);
//            }
//            return school;
//        }
//        return null;
//    }

    public boolean bullseye(Monad monad, float x, float y, float latitude) {
        if (x >= monad.x && x <= monad.x + monad.w &&
                y >= monad.y - latitude
                && y <= monad.y + monad.h - latitude) {
            return true;
        } else {
            return false;
        }
    }
}