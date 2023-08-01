import processing.core.PApplet;
import processing.core.PGraphics;

public class Scroller extends Organelle{

    Scrollish target;
    int barColor = 0xff1A1A1A;

    public Scroller(Scrollish target){
        this.target = target;
        w = target.scrollW;
        addChild(new ScrollerGrip());
    }

    public void update(PGraphics g){
        x = getParent().x + getParent().w - w;
        y = getParent().y;
        h = getParent().h;
        super.update(g);
    }

    void draw(PGraphics g){
        g.noStroke();
        g.fill(barColor);
        g.rect(x, y, w, h);
    }

    float getGripY(){
        System.out.println("Scroller parent: " + getParent() + ", latitude: " + getParent().getLatitude() + ", foot: " + getParent().getFoot());
        return PApplet.constrain(getParent().getLatitude() / getParent().getFoot() * getParent().h, 0, getParent().h - h);
    }

   float getGripH(){
        return PApplet.constrain((h / getParent().getFoot()) * h, 0, h);
    }

}