import processing.core.PGraphics;

public class TagWall extends Organelle implements IObserver {

    TagList pigeonholer;

    public TagWall(TagList pigeonholer){
        this.pigeonholer = pigeonholer;
        pigeonholer.registerObserver(this);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){}

    public void updateTagList(PGraphics g, String[] tags){
        children.clear();

        float linkY = y;
        float rowW = 0;
        float gap = g.textWidth(' ');

        for (String tag : tags){
            TagLink link = new TagLink(tag);
            addChild(link);
            link.size(g);

            if (rowW + link.w > w){
                linkY += g.textLeading;
                rowW = 0;
            }
            link.setPos(x + rowW, linkY);
            rowW += link.w + gap;
        }
    }

    @Override
    public void update() {
    }
}
