import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.KeyEvent;

public class ContactSheet extends Organelle {

    private int columns = 5;

    Virgo virgo;

    ArrayList<DropZone> dropZones = new ArrayList<DropZone>();

    public ContactSheet(){
        virgo = new Jigsaw();
    }

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){
        setPos(parentX, parentY);
        setSize(parentW, parentH);
        virgo.arrangeThumbnails(getChildren(), x, y, w, getColumns());
        h = virgo.getFoot(getChildren());
        clearDropZones();
        createDropZones();
        for (Thumbnail t : getThumbnails()){
            t.createDropZones();
        }
        updateChildren();
    }

    public void drawAfter(PGraphics g){
        for (DropZone z : dropZones){
            z.debugDraw(g);
        }

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

    public void createDropZones(){

        int allowance = 20;

        for (int i = 0; i < getThumbnails().size() - 1; i++){
            Thumbnail left = getThumbnails().get(i);
            Thumbnail right = getThumbnails().get(i + 1);
            if (i == 0){
                dropZones.add(new ThumbArrangeDropZone(left, x, left.y, left.x - x + allowance, left.h));
            }
            if (right.x <= left.x){
                dropZones.add(new ThumbArrangeDropZone(right,
                        left.x + left.w - allowance,
                        left.y,
                        w - (left.x + left.w) + allowance,
                        left.h));
                dropZones.add(new ThumbArrangeDropZone(right,
                        x, right.y, right.x + allowance, right.h));
            }
            else {
                dropZones.add(
                        new ThumbArrangeDropZone(right,
                                left.x + left.w - allowance,
                                PApplet.min(left.y, right.y),
                                right.x - (left.x + left.w) + 2 * allowance,
                                PApplet.max(left.h, right.h)));
            }
        }
    }

    public void clearDropZones(){
        dropZones.clear();
    }
}