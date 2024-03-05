import processing.core.PGraphics;

public class SidePanel extends Organelle {

    int color = 32;
    float openWidth = 300;

    SearchBar searchBar;
    TagWall tagWall;

    public SidePanel(TulpaHeart heart){
        searchBar = new SearchBar();
        searchBar.setMargin(2);
        tagWall = new TagWall(heart.library.tagList);
        Virgo stack = new Vertigo(getBounds(), searchBar, tagWall);
        stack.setPadding(10, 30);
        addChild(stack);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        Cell cell = new Cell(parentX, parentY, parentW, parentH);
        setBounds(cell.divideLeft(openWidth));
    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(color);
        g.rect(x, y, w, h);
    }
}
