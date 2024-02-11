import java.sql.Array;
import java.util.ArrayList;

public class EmptySelection implements ISelection {

    @Override
    public ISelection add(Clipping clipping) {
       return new SingleSelection(clipping);
    }

    @Override
    public ISelection remove(Clipping clipping) {
        return this;
    }

    @Override
    public ISelection toggle(Clipping clipping) {
        return add(clipping);
    }

    @Override
    public ISelection clear() {
        return this;
    }

    @Override
    public ISelection select(Clipping clipping){
        return new SingleSelection(clipping);
    }

    @Override
    public ArrayList<Clipping> getSelectedClippings() {
        return new ArrayList<>();
    }

    @Override
    public Clipping get(int index) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(Clipping clipping){
        return false;
    }
}
