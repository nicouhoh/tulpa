import processing.core.PGraphics;
import java.util.ArrayList;

public class ContactSheet extends Organelle {

    PGraphics g;
    private int columns = 5;
    private int gutter = 10;

    public ContactSheet(PGraphics g){
        this.g = g;
        this.shape = new ShapeOfGas();
        this.drawBehavior = new DrawDebug(g, this);
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
        System.out.println("Giving form to clippings");
        for (Clipping clip : clippings){
            thumbnails.add(new Thumbnail(g, clip));
        }
        addChildren(thumbnails);
    }

    public void arrangeThumbnails(){
        shape.shift(this);
        for (int i = 0; i < getChildren().size(); i++){
            Thumbnail t = (Thumbnail)getChildren().get(i);
            t.setSize(getThumbSize(), getThumbSize());
            t.setPos(x + getGutter()*(i % getColumns() + 1) + getThumbSize() * (i % getColumns()),
                    y + (int)(i / getColumns()) * getThumbSize() + (int)(i / getColumns() + 1) * getGutter()  );
        }
    }
}
