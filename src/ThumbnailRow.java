import java.util.ArrayList;

public class ThumbnailRow {

    ArrayList<Thumbnail> thumbnails;
    float x;
    float y;
    float height;
    float gutter;

    public ThumbnailRow(float x, float y, float thumbSize, float gutter){
        thumbnails = new ArrayList<Thumbnail>();
        this.x = x;
        height = thumbSize;
        this.gutter = gutter;
    }

    public void add(Thumbnail thumb){
        thumbnails.add(thumb);
    }

    public int count(){
        return thumbnails.size();
    }

    public void clear(){
        thumbnails.clear();
    }

    public float width(){
        float w = 0;
        for (Thumbnail t : thumbnails){
            w += t.w;
        }
        return w;
    }

    public void resizeRow(float ratio, float gutter){

        height *= ratio;

        for (Thumbnail t : thumbnails){
            t.clearOffset();
            t.fitToHeight(height);
        }
    }

    public void positionThumbs() {
        for (Thumbnail t : thumbnails) {
            t.setPos(x, y);
            x += t.w + gutter;
        }
    }

    public boolean canFit(Thumbnail nextThumb, float contactSheetW){
        return width() + nextThumb.w <= contactSheetW + (count() + 2) * gutter;
    }

    public float resizeRatio(float contactSheetW){
        return (contactSheetW - (count() + 1) * gutter) / width();
    }

}
