import processing.core.PGraphics;
import java.util.ArrayList;

public class ContactSheet extends Organelle {

    PGraphics g;
    private int columns = 5;
    private int gutter = 10;

    public ContactSheet(){
        shape = new ShapeOfGas();
    }

    @Override
    public void update(PGraphics g, Conductor c, float parentX, float parentY, float parentW, float parentH){
        shift(parentX, parentY, parentW, parentH);
        arrangeThumbnails();
        updateChildren(g, c);
    }

    public float getGutter(){
        return gutter;
    }

    public float getColumns(){
        return columns;
    }

    public float getThumbSize(){
        return (w - getGutter() * (getColumns() + 1)) / getColumns();
    }

    public void materialize(ArrayList<Clipping> clippings){
        ArrayList<Organelle> thumbnails = new ArrayList<Organelle>();
//        System.out.println("Giving form to clippings");
        for (Clipping clip : clippings){
            thumbnails.add(new Thumbnail(g, clip));
        }
        addChildren(thumbnails);
        arrangeThumbnails();
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
