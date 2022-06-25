import java.util.ArrayList;
import processing.core.PGraphics;

public abstract class Scrawler extends Monad implements Clickable {

    String text;
    String blankText = "Type here";

    float textSize;

    int boxColor = 30;
    int blankTextColor = 100;
    int bodyTextColor = 235;

    boolean focused;

    @Override
    public void draw(PGraphics g){
        drawBox(g);
        drawText(g);
        drawFocus(g);
    }

    public void drawFocus(PGraphics g){ // TODO this will be unnecessary once we have a text cursor
        if(!focused) return;
        g.stroke(255);
        g.noFill();
        g.rect(x, y, w, h);
    }

    public void drawBox(PGraphics g){
        g.noStroke();
        g.fill(boxColor);
        g.rect(x,y,w,h);
    }

    public void drawText(PGraphics g){
        g.textSize(textSize);
        if(text.length() > 0){
            g.fill(bodyTextColor);
            g.text(text, x, y, w, h);
        }else{
            g.fill(blankTextColor);
            g.text(text, x, y, w, h);
        }
    }

    public ArrayList<Tag> parseTags(){
        ArrayList<Tag> output = new ArrayList<Tag>();
        String[] words = text.split(" ");
        for(String word : words){
           if (word.length() > 0 && word.charAt(0) == '#'){
               output.add(new Tag(word));
           }
        }
        return output;
    }

    public void type(char key){
        if (key == '\b') { // BACKSPACE
            if (text.length() < 1) return;
            text = text.substring(0, text.length() - 1);
        }else{
            text += key;
        }
    }

    @Override
    public void clicked(Operator operator, int mod, float clickX, float clickY, Callosum c){
        // put into typing mode and focus this text box
        System.out.println("Scrawler clicked");
        c.focusText(this);
    }

    public void setFocused(boolean bool){
        focused = bool;
    }

    public void commit(Callosum c){}
}
