import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import processing.core.PGraphics;

public class ContactSheet extends Organelle {

    private int columns = 5;

    Virgo virgo;

    public ContactSheet(){
        virgo = new Jigsaw();
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
//        setBounds(parentX, parentY, parentW, parentH);
        virgo.arrangeThumbnails(getChildren(), x, y, w, getColumns());
        h = virgo.getFoot(getChildren(), w, getColumns());
        clearDropZones();
        createDropZones();
        for (Thumbnail t : getThumbnails()){
            t.createDropZones();
        }
        updateChildren();
    }

    public void drawAfter(PGraphics g){
        for (Dropzone z : getDropzones()){
            z.draw(g);
        }
    }

    public int getColumns(){
        return columns;
    }

    public void zoom(int amount){
        columns = PApplet.constrain(columns + amount, 2, 30);
        virgo.arrangeThumbnails(getChildren(), x, y, w, getColumns());
        h = virgo.getFoot(getChildren(), w, columns);
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

        clearDropZones();

        int allowance = 20;

        for (int i = 0; i < getThumbnails().size() - 1; i++){
            Thumbnail left = getThumbnails().get(i);
            Thumbnail right = getThumbnails().get(i + 1);

            //the first thumbnail
            if (i == 0){
                dropZones.add(new ThumbArrangeDropZone(left, x, left.y, left.x - x + allowance, left.h, x));
                createDropZoneBetweenThumbnails(left, right, allowance);
            }

            // beginning and ending rows
            else if (right.x <= left.x){
                dropZones.add(new ThumbArrangeDropZone(right,
                        left.x + left.w - allowance,
                        left.y,
                        x + w - (left.x + left.w) + allowance,
                        left.h,
                        left.x + left.w));
                dropZones.add(new ThumbArrangeDropZone(right,
                        x, right.y, right.x - x + allowance, right.h, right.x));
            }

            // everything else
            else {
                createDropZoneBetweenThumbnails(left, right, allowance);
            }
        }
    }

    public void createDropZoneBetweenThumbnails(Thumbnail left, Thumbnail right, float allowance){
        dropZones.add(
                new ThumbArrangeDropZone(right,
                        left.x + left.w - allowance,
                        PApplet.min(left.y, right.y),
                        right.x - (left.x + left.w) + 2 * allowance,
                        PApplet.max(left.h, right.h), (left.x + left.w + right.x)/2));
    }

    public ArrayList<Dropzone> getDropzones(){
        return new ArrayList<Dropzone>(dropZones);
    }

    public void clearDropZones(){
        dropZones.clear();
    }

    public void rearrangeThumbnails(Thumbnail frog, Thumbnail lily){
        int frogIndex = children.indexOf(frog);
        int lilyIndex = children.indexOf(lily);
        if (frogIndex < lilyIndex) {
            Collections.rotate(children.subList(frogIndex, lilyIndex), -1);
        }
        else if (frogIndex > lilyIndex){
            Collections.rotate(children.subList(lilyIndex, frogIndex + 1), 1);
        }
        System.out.println("moved " + frog + " in front of " + lily);
    }
}