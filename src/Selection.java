import java.util.ArrayList;

public class Selection {
    ArrayList<Clipping> clippings;

    public Selection(){
        clippings = new ArrayList<Clipping>();
    }

    public Selection(Clipping clipping){
        clippings = new ArrayList<Clipping>();
        this.clippings.add(clipping);
    }

    public Selection(ArrayList<Clipping> clippings){
        this.clippings = clippings;
    }

    public void add(Clipping clipping){
        if (selected(clipping)) return;
        clipping.isSelected = true;
        clippings.add(clipping);
    }

    public void remove(Clipping clipping){
        if (!selected(clipping)) return;
        clipping.isSelected = false;
        clippings.remove(clipping);
    }

    public void toggle(Clipping clipping){
        if (selected(clipping)) remove(clipping);
        else add(clipping);
    }

    public void select(Clipping clipping){
        clear();
        add(clipping);
    }

    public void clear(){
        for (Clipping clipping : clippings){
            clipping.isSelected = false;
        }
        clippings.clear();
    }

    public boolean selected(Clipping clipping){
        return clippings.contains(clipping);
    }

    public ArrayList<Clipping> getClippings(){
        return clippings;
    }

    public Clipping get(int index){
        return clippings.get(index);
    }

    public boolean isEmpty(){
        return clippings.isEmpty();
    }

    public int size(){
        return clippings.size();
    }
}
