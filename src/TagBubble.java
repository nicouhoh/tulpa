public class TagBubble extends Skrivsak {

    Thumbnail thumbnail;

    private float minH = 30;

    public TagBubble(Thumbnail thumbnail){
        this.thumbnail = thumbnail;
        this.font = tulpa.SOLE.getSkrivBordFont();
        this.buffer = new GapBuffer("testing");
        this.paperAlpha = 192;
        this.textAlpha = 225;
        setPos(thumbnail.x, thumbnail.y + thumbnail.h - minH);
        setSize(thumbnail.w, minH);
    }

    public void setUpBubble(){
        String[] tags = thumbnail.clipping.getTags();
        for (String tag : tags){
            //copy tags to buffer with #'s in between.
        }
    }
}