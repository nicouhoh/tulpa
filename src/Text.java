public class Text {
    String bodyText;
    String hint;

    public Text(String bodyText, String hint){
        this.bodyText = bodyText;
        this.hint = hint;
    }

    public String[] getWords(){
        return bodyText.split(" ");
    }
}