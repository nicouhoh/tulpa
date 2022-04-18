import java.lang.reflect.Array;
import java.util.ArrayList;
import processing.core.PGraphics;

public class ContactSheet extends Monad{

    Field parent;
    ArrayList<Spegel> children = new ArrayList<Spegel>();

    public ContactSheet(Field parent){
       this.parent = parent;
       this.parent.children.add(this);
       setPos(parent.x, parent.y);
       setSize(parent.w - parent.scrollerW, parent.h);
    }

    public void draw(PGraphics g, float latitude) {
        g.push();
        g.translate(0, -latitude);
        for (Spegel spegel : children) {
            spegel.draw(g);
        }
        g.pop();
    }

    @Override
    public void update(){
        setPos(parent.y, parent.y);
        setSize(parent.w - parent.scrollerW, parent.h);
    }
}