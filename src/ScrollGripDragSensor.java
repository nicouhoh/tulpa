public class ScrollGripDragSensor extends DragSensor {

    DragGestureListener listener;

    public ScrollGripDragSensor(Organelle organelle){
        listener = new DragGestureListener();
        organelle.addMousish(new DragSpy());
    }


    public void addDragGestureListener(){
    }
}
