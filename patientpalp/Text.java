import processing.core.PConstants;

public class Text {
    String bodyText;
    String hint;

    public Text(String bodyText, String hint){
        this.bodyText = bodyText;
        this.hint = hint;
    }

    public String[] getWords(){
        return bodyText.split("\\s");
    }

    public void type(char c, Library lib){
        switch(c){
            case PConstants.BACKSPACE -> { if (bodyText.length() > 0) bodyText = bodyText.substring(0, bodyText.length() - 1); }
            case ' ' -> {
                collectTags(lib);
                bodyText += c;
            }
            default -> bodyText += c;
        }
    }

    public void unfocus(){}

    public void commit(){}

    public void collectTags(Library lib){}
}