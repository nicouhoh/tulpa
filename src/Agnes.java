import java.util.ArrayList;

public class Agnes implements Virgo {

    private float gutter = 10;

    @Override
    public void arrangeThumbnails(ArrayList<Organelle> thumbs, float x, float y, float w, int columns) {
        float thumbSize = getThumbSize(w, columns);
        sizeThumbnails(thumbs, thumbSize);
        positionThumbnails(thumbs, x, y, thumbSize, columns);
    }

    public float getFoot(ArrayList<Organelle> thumbs, float w, int columns){
        int rows = rowCount(thumbs, columns);
        return (rows + 1) * getThumbSize(w, columns) + ((rows + 2) * gutter);
    }

    public int rowCount(ArrayList<Organelle> thumbs, int columns){
        return thumbs.size() / columns;
    }

    public float getGutter(){
        return gutter;
    }

    public float getThumbSize(float w, int columns){
        return (w - gutter * (columns + 1)) / columns;
    }

    public void sizeThumbnails(ArrayList<Organelle> thumbs, float thumbSize) {
        for (Organelle thumb : thumbs){
            thumb.setSize(thumbSize, thumbSize);
        }
    }

    public void positionThumbnails(ArrayList<Organelle> thumbs, float x, float y, float thumbSize, int columns){
        for (int i = 0; i < thumbs.size(); i++) {
            thumbs.get(i).setPos(findThumbX(i, thumbSize, columns, x), findThumbY(i, thumbSize, columns, y));
        }
    }

    public float findThumbX(int index, float thumbSize, int columns, float originX){
        return originX + gutter * (index % columns + 1) + thumbSize * (index % columns);
    }

    public float findThumbY(int index, float thumbSize, int columns, float originY){
        return originY + (index / columns) * thumbSize + (index / columns + 1) * gutter;
    }
}
