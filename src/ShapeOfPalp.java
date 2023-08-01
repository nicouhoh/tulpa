public class ShapeOfPalp implements ShapeShifter {

    @Override
    public void update(Organelle organelle) {
        organelle.x = 0;
        organelle.y = 0;
        organelle.w = tulpa.SOLE.w;
        organelle.h = tulpa.SOLE.h;
    }
}
