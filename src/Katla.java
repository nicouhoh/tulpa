import processing.core.PVector;
import java.util.ArrayList;

public class Katla {

    // Drag-and-drop manager.

    // TODO initiate drag
    // TODO Dragging logic
    // TODO Ghost image
    //

    ArrayList<Draggish> organelles = new ArrayList<Draggish>();

    Draggish heldItem;
    PVector dragOrigin = new PVector(0, 0);
    PVector dragOffset = new PVector(0, 0);


    public void handleDrag(Draggish draggish, int mouseX, int mouseY){
        if (heldItem == null) {
            startDrag(draggish, mouseX, mouseY);
        } else {

        }
    }

    public void startDrag(Draggish draggish, float originX, float originY){
        setHeldItem(draggish);
        heldItem.grab();
    }

    public void handleDrop(int mouseX, int mouseY){}

    public void setHeldItem(Draggish draggish){
        heldItem = draggish;
    }

    public void clearHeldItem(){
        heldItem = null;
    }

    public void registerDraggishOrganelle(Draggish draggish){
        organelles.add(draggish);
    }

    public void setDragOrigin(float x, float y){
        dragOrigin = new PVector(x, y);
    }

}