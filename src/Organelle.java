import java.util.ArrayList;

public abstract class Organelle {

    Organelle parent;
    ArrayList<Organelle> children;
    float x, y, w, h;

    abstract void update();

    abstract void draw();
}
