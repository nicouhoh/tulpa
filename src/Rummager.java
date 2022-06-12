import processing.core.PGraphics;

public class Rummager extends Monad {

    Scrawler searchBar;
    Panel panel;

    public Rummager(Panel parent){
        this.parent = parent;
        this.panel = (Panel)parent;
        parent.children.add(this);
        searchBar = new SearchBar(this, x, y, w, h, parent.callosum);
    }

    @Override
    public void update(){
        searchBar.setBounds(x, y, w, h);
    }
}
