import processing.core.PGraphics;

import java.util.ArrayList;

public class Apparition {

    ArrayList<Organelle> mirages;
    float x, y;

    public Apparition(){
        mirages = new ArrayList<Organelle>();
    }

    public void draw(PGraphics g) {
        g.tint(255, 64);
        for (Organelle o : mirages){
            if (o instanceof ScrollGrip) continue;
            o.draw(g, x - o.dragX, y - o.dragY);
        }
        g.tint(255, 255);
    }

    //TODO Latitude is a problem

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }
}
