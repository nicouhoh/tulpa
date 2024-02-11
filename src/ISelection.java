import java.util.ArrayList;

public interface ISelection {

   ISelection add(Clipping clipping);
   ISelection remove(Clipping clipping);
   ISelection toggle(Clipping clipping);
   ISelection clear();
   ISelection select(Clipping clipping);
   ArrayList<Clipping> getSelectedClippings();
   Clipping get(int index);
   boolean isEmpty();
   int size();
   boolean contains(Clipping clipping);
}
