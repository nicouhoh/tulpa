public class ThumbnailDroppish implements Droppish{
    @Override
    public void eatWorm(Conductor conductor, Organelle organelle) {

    }

    @Override
    public void openMouth(Conductor conductor, MouseInput mouseInput, Organelle organelle, Organelle held) {
        if (!(conductor.mouseInput.heldItem instanceof Thumbnail)) return;
        Thumbnail t = (Thumbnail)organelle;
        t.drawDebug(conductor.g);
    }
}
