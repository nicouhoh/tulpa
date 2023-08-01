import java.util.ArrayList;

public class Materializer {

    public ArrayList<Organelle> materialize(ArrayList<Clipping> clippings){
        ArrayList<Organelle> thumbnails = new ArrayList<Organelle>();
        System.out.println("Giving form to clippings");
        for (Clipping clip : clippings){
            thumbnails.add(new Thumbnail(clip));
        }
        return thumbnails;
    }
}
