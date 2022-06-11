import processing.core.PGraphics;
import java.util.ArrayList;

public class Panel extends Monad {

    Callosum callosum;
    int bgColor = 70;

    float tagWallX = 150;
    float tagWallY = 150;
    float tagWallW;
    float tagWallH;
    int tagColor = 235;

    boolean open = false;

    public Panel(Cockpit p, Callosum c){
        this.parent = p;
        this.parent.children.add(this);
        this.callosum = c;
        setBounds(parent.x, parent.y, 0, parent.h);
    }

    @Override
    public void update(){
        setPos(parent.x, parent.y);
        this.h = parent.h;
    }

    @Override
    public void draw(PGraphics g){
        if(!open) return;
        g.noStroke();
        g.fill(bgColor);
        g.rect(x, y, w, h);
        drawTagWall(g);
    }

    public void drawTagWall(PGraphics g){
        g.fill(tagColor);
        ArrayList<Tag> allTags = callosum.library.tags;
        float tX = tagWallX;
        float tY = tagWallY;
        for (int i = 0; i < allTags.size(); i++){
            g.text(allTags.get(i).name, tX, tY);
            tY += g.textSize;
        }
    }

    public void setOpen(boolean b){
        update();
        open = b;
    }

    public void toggleOpen(){
        update();
        open = !open;
    }
}