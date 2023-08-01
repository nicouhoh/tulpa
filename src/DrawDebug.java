import processing.core.PGraphics;
public class DrawDebug implements DrawBehavior{

    PGraphics g;
    Organelle organelle;

    public DrawDebug(PGraphics g, Organelle organelle){
        this.g = g;
        this.organelle = organelle;
    }

    public void draw(){
        g.noFill();
        g.stroke(255, 0, 255);
        g.rect(organelle.x, organelle.y, organelle.w, organelle.h);
    }
}