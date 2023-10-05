
import java.util.ArrayList;

public class ContactSheet extends Organelle {

    PGraphics g;
    private int columns = 5;
    private int gutter = 10;
    private int puzzleGutter = 2;
    private boolean puzzle = true;

    ArrayList<DropZone> dropZones;

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
        if (puzzle) return puzzleGutter;
        else return gutter;
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
        if (puzzle) puzzleArrange();
        else gridArrange();
    }

    public void gridArrange(){
        for (int i = 0; i < getChildren().size(); i++){
            Thumbnail t = (Thumbnail)getChildren().get(i);
            t.setSize(getThumbSize(), getThumbSize());
            t.setPos(x + getGutter()*(i % getColumns() + 1) + getThumbSize() * (i % getColumns()),
                    y + (int)(i / getColumns()) * getThumbSize() + (int)(i / getColumns() + 1) * getGutter()  );
            if (i == getChildren().size() - 1)
                h = (int)(i / getColumns() + 1) * getThumbSize() + (int)(i / getColumns() + 2) * getGutter();
        }
    }
    
    public void puzzleArrange(){
        float rowY = y + getGutter();
        float rowW = 0;
        float rowH = getThumbSize();
        ArrayList<Thumbnail> row = new ArrayList<Thumbnail>();
        for (int i = 0; i < getChildren().size() + 1; i++){
            Thumbnail nextThumb = (Thumbnail)getChildren().get(i);
            nextThumb.fitToHeight(rowH);
            // if it doesn't fit
            if (rowW + nextThumb.w > w + (row.size() + 2) * getGutter() || i == getChildren().size() - 1) {
                if (i == getChildren().size() - 1){
                    row.add(nextThumb);
                    rowW += nextThumb.w;
                }
                //resize current row
                float ratio = (w - (row.size() + 1) * getGutter()) / rowW;
                float rowX = getGutter();
                for (Thumbnail t : row){
                    t.fitToHeight(ratio * rowH);
                    t.setPos(rowX, rowY);
                    rowX += t.w + getGutter();
                }
                //reset & start a new row
                row.clear();
                rowW = 0;
                rowY += ratio * rowH + getGutter();
            }
            if (i == getChildren().size() - 1){
                h = nextThumb.y + nextThumb.h + getGutter();
                return;
            }
            // add to the next row
            row.add(nextThumb);
            rowW += nextThumb.w;
        }
    }

    boolean resized(){
        Organelle parent = getParent();
        return (x != parent.x || y != parent.y || w != parent.w || h != parent.h);
    }

    public void conjureDropZones(){
        for (Organelle thumbnail : getChildren()){
            dropZones.add(new DropZone(thumbnail));
        }
    }

}
