import processing.core.PGraphics;

public class ImageSlot extends Porthole {

    public ImageSlot(){
        w = 100;
        h = 100;
    }

    @Override
    public void draw(PGraphics g){
        g.stroke(223, 64);
        g.strokeWeight(4);
        g.rect(x, y, w, h);
    }

}