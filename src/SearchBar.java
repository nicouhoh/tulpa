import processing.core.PGraphics;

public class SearchBar extends Organelle {

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){

    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(16);
        g.rect(x, y, w, h);
    }
}
