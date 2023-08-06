import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class DrawThumbnail implements DrawBehavior{

    PGraphics g;
    Thumbnail t;

    public DrawThumbnail(PGraphics g, Thumbnail t){
        this.g = g;
        this.t = t;
    }

    @Override
    public void draw() {
        if (t.clipping.img != null) g.image(t.clipping.img, t.getThumbX(), t.getThumbY(), t.getThumbSize().x, t.getThumbSize().y);
    }
}
