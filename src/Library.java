import java.util.ArrayList;

public class Library {

    ArrayList<Clipping> clippings;

    public Library(){
        clippings = new ArrayList<Clipping>();
    }

    public void add(Clipping clipping){
        clippings.add(clipping);
    }

    public void add(ArrayList<Clipping> clips){
        clippings.addAll(clips);
    }

    public void remove(Clipping clipping){
        clippings.remove(clipping);
    }

    public int indexOf(Clipping clipping){
        return clippings.indexOf(clipping);
    }
}
