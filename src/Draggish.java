public interface Draggish extends Palpable{

    void grab();

    void drag(float mouseX, float mouseY, float originX, float originY, float offsetX, float offsetY);

    void release();
}
