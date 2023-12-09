import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Skrivbord extends Organelle {

    Passage passage;

    int margin = 50;
    int minW = 400;
    int maxW = 800;
    int minH =  50;

    public Skrivbord(){

    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){
        setSize(parentW, parentH);
        setPos(parentX + parentW/2 - w/2, parentY);
    }

    @Override
    public void draw(PGraphics g){
        g.fill(49);
        g.noStroke();
        g.rect(x, y, w, h, 8);
        g.fill(223);
        if (passage != null) {
            System.out.println(passage.text.toString());
            g.text(passage.text.toString(), x, y, w, h);
        }
    }

    @Override
    public void setSize(float parentW, float parentH){
        w = PApplet.constrain(parentW, minW, maxW);
        h = parentH;
    }

    public void setPassage(Passage passage){
       this.passage = passage;
    }
}
