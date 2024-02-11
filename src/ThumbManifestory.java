import processing.core.PGraphics;

import java.util.ArrayList;


public class ThumbManifestory {

    ArrayList<Clipping> clippings;

    public ThumbManifestory(ArrayList<Clipping> clippings){
        this.clippings = clippings;
    }

    public ThumbManifestory(Clipping clipping){
        this.clippings = new ArrayList<Clipping>();
        clippings.add(clipping);
    }

    public ArrayList<Organelle> manifestThumbnails(PGraphics g){
        ArrayList<Organelle> thumbs = new ArrayList<Organelle>();
        for (Clipping clip : clippings){
            thumbs.add(new Thumbnail(clip));
        }
        return thumbs;
    }
}
