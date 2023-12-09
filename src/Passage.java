import java.lang.StringBuffer;

public class Passage {

    StringBuffer text = new StringBuffer();

    public Passage(){}

    public Passage(String string){
        this.text.append(string);
    }

    public void addCharacter(char c){
        text.append(c);
    }

}
