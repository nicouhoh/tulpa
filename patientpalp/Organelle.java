import java.util.ArrayList;

public abstract class Organelle {

    Organelle parent;
    ArrayList<Organelle> children;
    float x, y, w, h;

    void update(){
        for (Organelle child : children) child.update();
    };

    void draw(){};
}