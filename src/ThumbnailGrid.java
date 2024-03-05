import java.util.ArrayList;

public interface ThumbnailGrid {

    void arrangeThumbnails(ArrayList<Organelle> thumbs, float x, float y, float w, int columns);

    float getFoot(ArrayList<Organelle> thumbs, float w, int columns);

    float getGutter();

    ThumbnailGrid toggle();

}
