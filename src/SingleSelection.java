import java.sql.Array;
import java.util.ArrayList;

public class SingleSelection implements ISelection {

    Clipping selectedClipping;

    public SingleSelection(Clipping clipping){
        this.selectedClipping = clipping;
        clipping.isSelected = true;
    }

    @Override
    public ISelection add(Clipping newClipping) {
        if (contains(newClipping)) return this;
        ISelection result = new MultipleSelection();
        result.add(selectedClipping);
        result.add(newClipping);
        newClipping.isSelected = true;
        return result;
    }

    @Override
    public ISelection remove(Clipping c) {
        if (c != selectedClipping) return this;
        selectedClipping.isSelected = false;
        return new EmptySelection();
    }

    @Override
    public ISelection toggle(Clipping clipping) {
        if (contains(clipping)) return remove(clipping);
        else return add(clipping);
    }

    @Override
    public ISelection clear() {
        selectedClipping.isSelected = false;
        return new EmptySelection();
    }

    @Override
    public ISelection select(Clipping clipping){
        if (contains(clipping)) return this;
        selectedClipping.isSelected = false;
        return new SingleSelection(clipping);
    }

    @Override
    public ArrayList<Clipping> getSelectedClippings() {
        ArrayList<Clipping> result = new ArrayList<>();
        result.add(selectedClipping);
        return result;
    }

    @Override
    public Clipping get(int index) {
        return selectedClipping;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean contains(Clipping clipping) {
        return (clipping == selectedClipping);
    }
}
