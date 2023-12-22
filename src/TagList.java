import processing.core.PGraphics;
import java.util.ArrayList;

public class TagList extends Organelle {

    ArrayList<Tag> tags;

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){}

    @Override
    public void draw(PGraphics g){
        if (tags == null) return;
        g.noFill();
        g.stroke(255, 128, 255);
        g.rect(x, y, w, h);
        float dumbY = y + g.textSize;
        g.noStroke();
        g.fill(0, 255, 128);
        for (Tag t : tags){
            g.text(t.name, x, dumbY);
            dumbY += g.textSize;
        }
    }

    public void setTags(ArrayList<Tag> tags){
        this.tags = tags;
    }
}
