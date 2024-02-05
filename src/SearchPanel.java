import processing.core.PGraphics;

public class SearchPanel extends Organelle {

    int color = 32;
    float openWidth = 300;

    SearchBar searchBar;
    TagWall tagWall;

    public SearchPanel(TulpaHeart heart){
        searchBar = new SearchBar();
        searchBar.setMargin(2);
        tagWall = new TagWall(heart.library.tagList);
        addChild(searchBar);
        addChild(tagWall);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        Cell panel = new Cell(x, y, w, h);
        panel.divideTop(50);
        searchBar.setBounds(panel.divideTop(30).shrink(10, 0));
        panel.divideTop(50);
        tagWall.setBounds(panel.shrink(10, 10));
    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(color);
        g.rect(x, y, w, h);
    }
}
