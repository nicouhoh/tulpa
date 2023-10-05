import processing.core.PGraphics;

public class Nothing extends Organelle {

    int BG_COLOR = 49;

    public Nothing(){
        x = 0;
        y = 0;
    }

    @Override
    public void draw(PGraphics g){
        g.background(BG_COLOR);
        for (Organelle child : getChildren()){
            child.draw(g);
        }
    }
}
