public class SearchBar extends Scrawler {

    Callosum callosum;

    int boxColor = 30;
    int textColor = 235;

    public SearchBar(Rummager parent, float x, float y, float w, float h, Callosum c){
        this.parent = parent;
        parent.children.add(this);
        callosum = c;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.blankText = "Type to search";
    }

    @Override
    public void type(char key, int kc){
        if (key == '\t') return;
        super.type(key, kc);
    }

    @Override
    public void commit(){
        //callosum.library.setSearchResults(callosum.library.search(bodyText));
        //System.out.println(callosum.library.getSearchResults());
        // search
        callosum.search(bodyText);
    }

    // search as you type
}
