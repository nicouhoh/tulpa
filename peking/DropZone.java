public class DropZone implements Droppish{

    Organelle organelle;
    float x, y, h, w;


    public DropZone(Organelle organelle){
        this.organelle = organelle;
    }

    @Override
    public void eatWorm(Conductor conductor, Organelle organelle) {

    }

    @Override
    public void openMouth(Conductor conductor, MouseInput mouseInput, Organelle organelle, Organelle held) {

    }
}
