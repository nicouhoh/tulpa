import processing.core.PApplet;
import processing.core.PGraphics;

public class MiniText extends Monad {

    String helpText;
    String nascentText = "";
    String userText = "";

    int boxColor = 30;
    int helpTextColor;
    int userTextColor;

    public MiniText(Spegel parent, float x, float y, String helpText){
       this.parent = parent;
       this.x = x;
       this.y = y;
       this.w = 100;
       this.h = 30;
       this.helpText = helpText;
       this.helpTextColor = 155;
       this.userTextColor = 235;
    }

    public void update(){
        this.x = parent.x;
        this.y = parent.y + parent.h - h;
    }

    public void draw(PGraphics g){
        drawBox(g);
        drawText(g);
    }

    public void drawBox(PGraphics g){
        g.noStroke();
        g.fill(boxColor);
        g.rect(x, y, w, h);
    }

    public void type(char key){
        if(key == '\b') { // 8 means BACKSPACE
            nascentText = nascentText.substring(0, nascentText.length()-1);
        }else{
            nascentText += key;
        }
    }

    public void commit(){
        userText = nascentText;
        nascentText = "";
    }

    public void drawText(PGraphics g){
        g.textSize(h);
        String bext = "";
        if(nascentText.length() > 0){
            g.fill(userTextColor);
            bext = nascentText;
        }else if (userText.length() > 0){
            g.fill(userTextColor);
            bext = userText;
        } else {
            g.fill(helpTextColor);
            bext = helpText;
        }
        g.text(bext, x, y);
    }
}
