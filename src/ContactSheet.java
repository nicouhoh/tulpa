import java.lang.reflect.Array;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;

public class ContactSheet extends Monad{

    float clipSize;
    float pillow;
    int sheetZoom;
    float foot;

    ArrayList<Spegel> children = new ArrayList<Spegel>();

    public ContactSheet(Field parent){
       this.parent = parent;
       this.parent.children.add(this);
       pillow = 10;
       clipSize = 200;
       sheetZoom = 5;
       setPos(parent.x, parent.y);
       setSize(parent.w - parent.scrollWidth(), parent.h);
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
        setSize(parent.w - parent.scrollWidth(), parent.h);
        setClipSize();
        arrangeSpegels();
    }

    public void setClipSize(){
        clipSize = PApplet.constrain((w - (pillow * (sheetZoom + 1))) / sheetZoom, 10, w);
    }

    public void newSpegel(Clipping clip){
        Spegel speg = new Spegel(this, clip);
        children.add(speg);
    }

    public void arrangeSpegels(){
        float fussX = pillow;
        float fussY = pillow;
        for (int i = 0; i < children.size(); i++){
            fussX = pillow + (i % sheetZoom) * (pillow + clipSize);
            fussY = pillow + (i / sheetZoom) * (pillow + clipSize);
            children.get(i).setSize(clipSize, clipSize);
            children.get(i).setPos(fussX, fussY);
        }
        setFoot(fussY + clipSize + pillow);
    }

    public void setFoot(float footY){
        foot = footY + clipSize + pillow;
    }
}