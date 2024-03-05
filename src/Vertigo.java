public class Vertigo extends Virgo {

    public Vertigo(Cell bounds, Organelle ... organelles){
        super(bounds, organelles);
    }

    @Override
    public void resizeChildren() {
        Cell main = getBounds();
        main = main.shrink(padding.x, padding.y);
        for (Organelle child : children){
            child.resize(main.divideTop(child.h));
            main.divideTop(gap.y);
        }
    }
}