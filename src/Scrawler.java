import java.util.ArrayList;
import processing.core.PGraphics;

public abstract class Scrawler extends Monad implements Clickable {

    String blankText = "Type here";
    String bodyText = "";

    float textSize;
    float boxSize;

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

    public void drawFocus(PGraphics g){
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
        String dtext = "";
        if(bodyText.length() > 0){
            g.fill(bodyTextColor);
            dtext = bodyText;
        }else{
            g.fill(blankTextColor);
            dtext = blankText;
        }
        g.text(dtext, x, y, w, h);
    }

    public void drawCursor(PGraphics g){

    }

    public ArrayList<Tag> parseTags(){
        ArrayList<Tag> output = new ArrayList<Tag>();
        String[] words = bodyText.split(" ");
        for(String word : words){
           if (word.charAt(0) == '#'){
               output.add(new Tag(word));
           }
        }
        return output;
    }

    public void type(char key, int kc){
        if (key == '\b') { // BACKSPACE
            if (bodyText.length() < 1) return;
            bodyText = bodyText.substring(0, bodyText.length() - 1);
        }else{
            bodyText += key;
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

    public void commit(){}
}
