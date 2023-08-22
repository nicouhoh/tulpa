import processing.core.PApplet;
import processing.core.PGraphics;

public class Scroller extends Organelle implements Shape, DrawBehavior, Scrollish{

    float scrollSpeed = 50;
    float foot = 0;
    float gripY;
    float gripH;

    ScrollRail scrollRail;

    public Scroller(){
        scrollW = 20;
        scrollRail = new ScrollRail();
        addChild(scrollRail);
    }

    @Override
    public void performUpdate(PGraphics g){
        shift();
        draw(g);
        g.push();
        g.translate(0, -latitude);
        updateChildren(g);
        for (Organelle child : getChildren()){
            if (!(child instanceof ScrollRail)){
                child.performUpdate(g);
                foot = child.h;
            }
        }
        g.pop();
        trackScrollStatus();
        scrollRail.performUpdate(g);
    }

    @Override
    public void draw(PGraphics g) {

    }

    @Override
    public void shift() {
        x = getParent().x;
        y = getParent().y;
        w = getParent().w;
        h = getParent().h;
    }

    @Override
    public void scroll(float scrollAmount){
        latitude = PApplet.constrain(latitude + (scrollAmount * scrollSpeed), 0, foot - h);
    }

    void trackScrollStatus(){
        scrollRail.gripY = (h * latitude) / foot;
        scrollRail.gripH = (h * h) / foot;
    }

}
