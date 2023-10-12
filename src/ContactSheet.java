import java.util.ArrayList;
import processing.core.PGraphics;

public class ContactSheet extends Organelle {

    private int columns = 5;

    Virgo virgo;

    public ContactSheet(Mouse mouse){
        virgo = new Agnes();
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){
        setPos(parentX, parentY);
        setSize(parentW, parentH);
        virgo.arrangeThumbnails(getChildren(), x, y, w, getColumns());
        h = virgo.getFoot(getChildren());
    }

    @Override
    public void draw(PGraphics g){
        drawChildren(g);
    }

    public int getColumns(){
        return columns;
    }

    public ArrayList<Thumbnail> getThumbnails(){
        ArrayList<Thumbnail> thumbs = new ArrayList<Thumbnail>();
        for (Organelle o : getChildren()){
            thumbs.add((Thumbnail)o);
        }
        return thumbs;
    }
}