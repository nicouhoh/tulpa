import java.lang.reflect.Array;
import java.util.ArrayList;

public class Agnes implements Virgo {

    float gutter = 10;

    @Override
    public void arrangeThumbnails(ArrayList<Organelle> thumbs, float x, float y, float w, int columns) {
        float thumbSize = getThumbSize(w, columns);
        for (int i = 0; i < thumbs.size(); i++){
            Organelle t = thumbs.get(i);
            t.setSize(thumbSize, thumbSize);
            t.setPos(x + gutter * (i % columns + 1) + thumbSize * (i % columns),
                    y + (i / columns) * thumbSize + (i / columns + 1) * gutter  );
        }

    }

    public float getFoot(ArrayList<Organelle> thumbs, float w, int columns){
        int rows = thumbs.size() / columns;
        float thumbSize = getThumbSize(w, columns);
        return (rows + 1) * thumbSize + ((rows + 2) * getGutter());
    }

//    //FIXME this isn't quite correct; thumbnails earlier in the row might be taller
//    public float getFoot(ArrayList<Organelle> thumbs){
//        Organelle lastThumb = thumbs.get(thumbs.size() - 1);
//        return lastThumb.y + lastThumb.h + gutter;
//    }


    public float getGutter(){
        return gutter;
    }

    public float getThumbSize(float w, int columns){
        return (w - gutter * (columns + 1)) / columns;
    }

}
