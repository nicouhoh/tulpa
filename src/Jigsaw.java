import processing.core.PVector;

import java.util.ArrayList;

public class Jigsaw implements Virgo{

    float gutter = 2;

    @Override
    public void arrangeThumbnails(ArrayList<Organelle> thumbs, float x, float y, float w, int columns) {
        float thumbSize = (w - gutter * (columns + 1)) / columns;
        float rowY = y + gutter;
        float rowW = 0;
        float rowH = thumbSize;
        ArrayList<Thumbnail> row = new ArrayList<Thumbnail>();
        for (int i = 0; i < thumbs.size(); i++){
            Thumbnail nextThumb = (Thumbnail)thumbs.get(i);
            nextThumb.fitToHeight(rowH);
            // if it doesn't fit
            if (rowW + nextThumb.w > w + (row.size() + 2) * gutter || i == thumbs.size() - 1) {
                if (i == thumbs.size() - 1){
                    row.add(nextThumb);
                    rowW += nextThumb.w;
                }
                //resize current row
                float ratio = (w - (row.size() + 1) * gutter) / rowW;
                float rowX = x + gutter;
                for (Thumbnail t : row){
                    t.clearOffset();
                    t.fitToHeight(ratio * rowH);
                    t.setPos(rowX, rowY);
                    rowX += t.w + gutter;
                }
                //reset & start a new row
                row.clear();
                rowW = 0;
                rowY += ratio * rowH + gutter;
            }
            // add to the next row
            row.add(nextThumb);
            rowW += nextThumb.w;
        }

    }

    @Override
    public float getFoot(ArrayList<Organelle> thumbs) {
        Organelle lastThumb = thumbs.get(thumbs.size() - 1);
        return lastThumb.y + lastThumb.h + gutter;
    }

    @Override
    public float getGutter(){
        return gutter;
    }
}
