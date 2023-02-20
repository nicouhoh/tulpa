public class ClippingText extends Text{
    public ClippingText(String bodyText, String hint) {
        super(bodyText, hint);
    }

    @Override
    public void collectTags(Library lib){
        lib.tagClipping(lib.selected.get(0), lib.parseTags(this));
    }
}