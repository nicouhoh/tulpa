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
    public void commit(){
        System.out.println(callosum.library.search(bodyText));
        // search
    }

    // search as you type
}
