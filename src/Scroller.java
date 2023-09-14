import processing.core.PApplet;
import processing.core.PGraphics;
import java.util.ArrayList;

public class Scroller extends Decorator implements Drawish, Wheelish {
    ScrollRail rail;
    ScrollGrip grip;
    float latitude;
    float scrollW = 10;
    float scrollSpeed = 50;

    public Scroller(Organelle organelle){
        this.organelle = organelle;
        this.shape = organelle.shape;

        this.rail = new ScrollRail(this);
        this.grip = new ScrollGrip();
        this.addChild(rail);
        rail.addChild(grip);
    }

    //TODO SORT THIS STUFF OUT

    @Override
    public void shift(float parentX, float parentY, float parentW, float parentH){
        if (shape != null){
            shape.shift(this, parentX, parentY, parentW, parentH);
        }
    }

    @Override
    public void update(PGraphics g, Conductor c, float parentX, float parentY, float parentW, float parentH) {
        g.push();
        g.translate(0, -latitude);
        organelle.update(g, c, parentX, parentY, parentW - scrollW, parentH);
        g.pop();

        shift(parentX, parentY, parentW, parentH);
        draw(g, x, y);
        updateGrip();
        updateChildren(g, c);
    }


    @Override
    public void draw(PGraphics g, float drawX, float drawY){
        g.stroke(255, 0, 255);
        g.noFill();
        g.rect(drawX, drawY, w - 1, h - 1);
    }

    //@Override
    public void wheel(float scrollAmount){
        System.out.println("Scrolling");
        latitude = PApplet.constrain(latitude + (scrollAmount * scrollSpeed), 0, organelle.h - h);
    }

    private void moveGrip(){
        grip.y = (h * latitude) / organelle.h;
        grip.h = (h * h) / organelle.h;
    }

    void updateGrip(){
        if (!grip.held){
            moveGrip();
        } else {
            latitude = (organelle.h * grip.y) / rail.h;
        }
    }

    public Organelle findDeepest(float mouseX, float mouseY){
        if (!theFingerPointsAtMe(mouseX, mouseY)) return null;
        Organelle deepestHit = null;
        if (clickish != null) deepestHit = this;

        Organelle childResult = null;
        if (mouseX > w - scrollW){
            for (Organelle child : getChildren()){
                childResult = child.findDeepest(mouseX, mouseY);
                if (childResult != null) deepestHit = childResult;
            }
        } else{
            for (Organelle child : organelle.getChildren()){
                childResult = child.findDeepest(mouseX, mouseY + latitude);
                if (childResult != null) deepestHit = childResult;
            }
        }

        return deepestHit;
    }
}
