public class ThumbnailClickish implements Clickish {

    @Override
    public void hot() {}

    @Override
    public void active() {}

    @Override
    public void click(Conductor conductor, Organelle o, int mod) {
        Thumbnail t = (Thumbnail)o;
        switch(mod){
            case 2, 4 -> conductor.toggleSelect(t);
            default -> conductor.selectClipping(t);
        }
    }
}
