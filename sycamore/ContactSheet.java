import java.util.ArrayList;
import processing.core.PGraphics;

public class ContactSheet extends Organelle implements Scrollish {

    private boolean puzzleMode = false;
    private int columns = 5;
    private int gutter = 10;

    public ContactSheet(){
        setLatitude(0);
    }

    public void update(PGraphics g){
        super.update(g);
    }
    public void draw(PGraphics g){}

    public float getClipSize() {
        return (w - scrollW - getGutter() * (columns + 1)) / columns;
    }

    public float getGutter(){
        return gutter;
    }

    public int getColumns(){
        return columns;
    }

    public void arrangeThumbnails(){
        setBounds(getParent().x, getParent().y, getParent().w, getParent().h);
        for (int i = 0; i < getThumbs().size(); i++){
            Thumbnail t = getThumbs().get(i);
            t.setSize(getClipSize(), getClipSize());
            t.setPos(x + gutter + ((i % columns ) * getClipSize()) + gutter * (i % columns),
                    y + gutter + (gutter + getClipSize() * (i / columns)));
            if (i == getThumbs().size() - 1) setFoot(t.y + getClipSize() + getGutter());
        }
    }

    private ArrayList<Thumbnail> getThumbs(){
        ArrayList<Thumbnail> thumbs = new ArrayList<Thumbnail>();
        for (Organelle o : getChildren()){
            if (o instanceof Thumbnail) thumbs.add((Thumbnail)o);
        }
        return thumbs;
    }

}