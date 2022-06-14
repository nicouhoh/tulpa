public class TagBubble extends Scrawler {

    public TagBubble(Spegel parent, float x, float y, float w, float h){
        this.parent = parent;
        parent.children.add(this);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.blankText = "Type a #tag";
    }

    @Override
    public void type(char key, int kc){
        if (key == '\t') return;
        super.type(key, kc);
    }

}
