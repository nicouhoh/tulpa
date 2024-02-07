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
        virgo.arrangeThumbnails(getChildren(), x, y, w, getColumns());
        h = virgo.getFoot(getChildren(), w, getColumns());
        refreshDropZones();
        createThumbnailDropzones();
        resizeChildren();
    }

    private void createThumbnailDropzones() {
        for (Thumbnail t : getThumbnails()){
            t.createDropZones();
        }
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
        virgo.arrangeThumbnails(getChildren(), x, y, w, columns);
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
        virgo = virgo.toggle();
    }

    public float getGutter(){
        return virgo.getGutter();
    }

    public void refreshDropZones(){
        ArrangerDropzoneFactory factory = new ArrangerDropzoneFactory(getThumbnails(), 20, x, w);
        dropZones = factory.produceAllZones();
    }


    public ArrayList<Dropzone> getDropzones(){
        return new ArrayList<Dropzone>(dropZones);
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