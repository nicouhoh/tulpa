public interface Draggish {

    void grab(Conductor c, Organelle o, float mouseX, float mouseY);
    void drag(Conductor c, Organelle o, float mouseX, float mouseY);
    void release(Conductor c, Organelle o, float mouseX, float mouseY);
}
