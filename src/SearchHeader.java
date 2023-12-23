import processing.core.PGraphics;

public class SearchHeader extends Organelle {

    String query;

    int color;

    public SearchHeader(String query){
        setQuery(query);
    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(64);
        g.rect(x, y, w, h);
        g.fill(233);
        g.text("showing \"" + query + "\"", x + 10, y + y/2 + g.textSize);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        Cell header = getBounds();
    }

    public void setQuery(String string){
        this.query = string;
    }

}
