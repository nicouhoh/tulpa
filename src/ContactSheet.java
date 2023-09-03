import processing.core.PGraphics;
import java.util.ArrayList;

public class ContactSheet extends Organelle implements Shape, DrawBehavior{

    PGraphics g;
    private int columns = 5;
    private int gutter = 10;
    ScrollRail scroller;

    public float getGutter(){
        return gutter;
    }

    public float getColumns(){
        return columns;
    }

    public float getThumbSize(){
        return (w - getGutter() * (getColumns() + 1)) / getColumns();
    }

    @Override
    public void shift(){
        if (resized()) {
            x = getParent().x;
            y = getParent().y;
            w = getParent().w - getParent().scrollW;
            h = getParent().h;

            arrangeThumbnails();
        }
    }

    @Override
    public void draw(PGraphics g, float x, float y){}

    public void materialize(ArrayList<Clipping> clippings){
        ArrayList<Organelle> thumbnails = new ArrayList<Organelle>();
        System.out.println("Giving form to clippings");
        for (Clipping clip : clippings){
            thumbnails.add(new Thumbnail(g, clip));
        }
        addChildren(thumbnails);
    }

    public void arrangeThumbnails(){
        for (int i = 0; i < getChildren().size(); i++){
            Thumbnail t = (Thumbnail)getChildren().get(i);
            t.setSize(getThumbSize(), getThumbSize());
            t.setPos(x + getGutter()*(i % getColumns() + 1) + getThumbSize() * (i % getColumns()),
                    y + (int)(i / getColumns()) * getThumbSize() + (int)(i / getColumns() + 1) * getGutter()  );
            if (i == getChildren().size() - 1)
                h = (int)(i / getColumns() + 1) * getThumbSize() + (int)(i / getColumns() + 2) * getGutter();
        }
    }

    boolean resized(){
        Organelle parent = getParent();
        return (x != parent.x || y != parent.y || w != parent.w || h != parent.h);
    }
}
