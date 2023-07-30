import java.util.ArrayList;
import processing.core.PGraphics;

public class ContactSheet extends Organelle {

    private boolean puzzleMode = false;
    private float foot;
    private int columns = 5;
    private int gutter = 10;

    public ContactSheet(){
    }

    public void update(PGraphics g){
        super.update(g);
    }
    public void draw(PGraphics g){}

    public float getClipSize() {
        return (w - getGutter() * (columns + 1)) / columns;
    }

    public void setFoot(float y){
        foot = y;
    }

    public float getFoot(){
        return foot;
    }

    public float getGutter(){
        return gutter;
    }

    public int getColumns(){
        return columns;
    }

    public void arrangeThumbnails(){
        for (int i = 0; i < getChildren().size(); i++){
            Thumbnail t = (Thumbnail)getChildren().get(i);
            t.setSize(getClipSize(), getClipSize());
            t.setPos(x + gutter + ((i % columns ) * getClipSize()) + gutter * (i % columns),
                    y + gutter + (gutter + getClipSize() * (i / columns)));
        }
//        foot = thumbnails.get(thumbnails.size() - 1).thumbY + clipSize + getGutter();
    }

}