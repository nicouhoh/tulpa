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

    // we make our translation here in Contact Sheet so nothing else needs to worry about it.
    @Override
    public void cascadeDraw(PGraphics g, float latitude) {
        if(isOnscreen(latitude)) {
            draw(g);
            if (children == null) return;
            g.push();
            g.translate(0, latitude);
            for (Spegel s : children) {
                s.cascadeDraw(g, latitude);
            }
            g.pop();
        }
    }

    @Override
    public void update(){
        setPos(parent.x, parent.y);
        setSize(parent.w - parent.scrollerW, parent.h);
    }
}