import processing.core.PGraphics;
import java.util.ArrayList;

public class Panel extends Monad {

    Callosum callosum;
    Rummager rum;
    int bgColor = 70;

    float panelWidth = 300;
    float pillow = 30;
    float searchH = 40;

    float tagWallX = 150;
    float tagWallY = 150;
    float tagWallW;
    float tagWallH;
    int tagColor = 235;

    boolean panelOpen = false;

    public Panel(Cockpit p, Callosum c){
        this.parent = p;
        this.parent.children.add(this);
        this.callosum = c;
        this.rum = new Rummager(this);
        setBounds(parent.x, parent.y, panelWidth, parent.h);
    }

    @Override
    public void update(){
        if(!panelOpen) return;
        setBounds(parent.x, parent.y, panelWidth, parent.h);
        setPos(parent.x, parent.y);
        this.h = parent.h;
        rum.setBounds(x + pillow, y + pillow, w - 2*pillow, searchH);
    }

    @Override
    public void draw(PGraphics g){
        if(!panelOpen) return;
        g.noStroke();
        g.fill(bgColor);
        g.rect(x, y, w, h);
        drawTagWall(g);
    }

    @Override
    public void cascadeDraw(PGraphics g, float latitude){
        if (panelOpen){
            super.cascadeDraw(g, latitude);
        }
    }

    public void drawTagWall(PGraphics g){
        g.fill(tagColor);
        ArrayList<Tag> allTags = callosum.library.tags;
        float tX = pillow;
        float tY = tagWallY;
        for (int i = 0; i < allTags.size(); i++){
            g.text(allTags.get(i).name, tX, tY);
            tY += g.textSize;
        }
    }

    public void open(){
        panelOpen = true;
        update();
    }

    public void close(){
        panelOpen = false;
        rum.searchBar.text = "";
    }

    public void toggleOpen(){
        if(panelOpen) close();
        else open();
    }

    public boolean isOpen(){
        return panelOpen;
    }
}