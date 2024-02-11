import java.util.ArrayList;

public class MultipleSelection implements ISelection {

    ArrayList<Clipping> selectedClippings;

    public MultipleSelection(){
        selectedClippings = new ArrayList<Clipping>();
    }

    public MultipleSelection(ArrayList<Clipping> clippings){
        selectedClippings = clippings;
    }

    @Override
    public ISelection add(Clipping clipping) {
        if (!selectedClippings.contains(clipping)) selectedClippings.add(clipping);
        clipping.isSelected = true;
        return this;
    }

    @Override
    public ISelection remove(Clipping clipping) {
        selectedClippings.remove(clipping);
        clipping.isSelected = false;
        return this;
    }

    @Override
    public ISelection toggle(Clipping clipping) {
        if (contains(clipping)) return remove(clipping);
        else return add(clipping);
    }

    @Override
    public ISelection clear() {
        for (Clipping c : selectedClippings) c.isSelected = false;
        return new EmptySelection();
    }

    @Override
    public ISelection select(Clipping clipping){
        for (Clipping c : selectedClippings) if (c != clipping) c.isSelected = false;
        return new SingleSelection(clipping);
    }

    @Override
    public ArrayList<Clipping> getSelectedClippings() {
        return selectedClippings;
    }

    @Override
    public Clipping get(int index) {
        return selectedClippings.get(index);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return selectedClippings.size();
    }

    @Override
    public boolean contains(Clipping clipping){
        return selectedClippings.contains(clipping);
    }
}
