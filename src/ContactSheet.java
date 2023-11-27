import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.KeyEvent;

public class ContactSheet extends Organelle {

    private int columns = 5;

    Virgo virgo;

    public ContactSheet(){
        virgo = new Agnes();
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){
        setPos(parentX, parentY);
        setSize(parentW, parentH);
        virgo.arrangeThumbnails(getChildren(), x, y, w, getColumns());
        h = virgo.getFoot(getChildren());
        updateChildren();
    }

    public int getColumns(){
        return columns;
    }

    public void zoom(int amount){
        columns = PApplet.constrain(columns + amount, 1, 30);
        virgo.arrangeThumbnails(getChildren(), x, y, w, getColumns());
        h = virgo.getFoot(getChildren());
    }

    public ArrayList<Thumbnail> getThumbnails(){
        ArrayList<Thumbnail> thumbs = new ArrayList<Thumbnail>();
        for (Organelle o : getChildren()){
            thumbs.add((Thumbnail)o);
        }
        return thumbs;
    }

    public void toggleViewMode(){
        if (virgo instanceof Agnes){
            virgo = new Jigsaw();
        } else if (virgo instanceof Jigsaw){
            virgo = new Agnes();
        }
    }

    public float getGutter(){
        return virgo.getGutter();
    }

}