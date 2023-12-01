public interface Draggish extends Palpable{

    void grab(Controller controller);

    void drag(Controller controller, float mouseX, float mouseY, float originX, float originY, float offsetX, float offsetY);

    void release(Controller controller);
}
