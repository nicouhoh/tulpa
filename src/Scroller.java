import processing.core.PApplet;
import processing.core.PGraphics;

public class Scroller extends Organelle implements Shape, DrawBehavior, Scrollish{

    float scrollSpeed = 50;

    ScrollRail rail;
    ScrollGrip grip;
    Organelle contents;

    public Scroller(Organelle contents){
        this.contents = contents;
        this.addChild(contents);
        scrollW = 10;
        rail = new ScrollRail();
        addChild(rail);
        grip = new ScrollGrip();
        rail.addChild(grip);
    }

    @Override
    public void performUpdate(PGraphics g){
        shift();
        draw(g, x, y);
        g.push();
        g.translate(0, -contents.latitude);
        updateChildren(g);
        contents.performUpdate(g);
        g.pop();
        rail.performUpdate(g);
        syncGrip();
    }

    @Override
    public void draw(PGraphics g, float x, float y){}

    @Override
    public void shift() {
        x = getParent().x;
        y = getParent().y;
        w = getParent().w;
        h = getParent().h;
    }

    @Override
    public void scroll(float scrollAmount){

        contents.latitude = PApplet.constrain(contents.latitude + (scrollAmount * scrollSpeed), 0, contents.h - h);
    }

    public Organelle getChild(){
        for (Organelle child : getChildren()){
            if (!(child instanceof ScrollRail)){
                return child;
            }
        }
        return null;
    }

    void trackScrollStatus(){
        grip.y = (h * contents.latitude) / contents.h;
        grip.h = (h * h) / contents.h;
    }

    void syncGrip(){
        if (!grip.held){
            trackScrollStatus();
        } else{
            contents.latitude = (contents.h * grip.y) / rail.h;
        }
    }
}
