public class SearchBar extends Scrawler {

    public SearchBar(Rummager parent, float x, float y, float w, float h, Callosum c){
        this.parent = parent;
        parent.children.add(this);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.text = "";
        this.blankText = "Type to search";
    }

    @Override
    public void update(){
        setBounds(parent.x, parent.y, parent.w, parent.h);
        textSize = h - h/4;
    }

    @Override
    public void type(char key){
        if (key == '\t') return;
        super.type(key);
    }

    @Override
    public void commit(Callosum c){
        c.search(text);
    }

    // TODO incremental search
}
