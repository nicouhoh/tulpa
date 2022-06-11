import java.util.ArrayList;

import jogamp.opengl.glu.nurbs.CArrayOfArcs;
import processing.core.PGraphics;

public class Scrawler extends Monad{

    String blankText = "Type here";
    String bodyText = "";

    int boxColor = 30;
    int blankTextColor = 100;
    int bodyTextColor = 235;

    public Scrawler(Monad parent, float x, float y, float w, float h){
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Scrawler(Monad parent, float x, float y, float w){
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = 30;
    }

    public Scrawler(Monad parent, float x, float y, float w, float h, String bt){
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.blankText = bt;
    }

    @Override
    public void draw(PGraphics g){
        drawBox(g);
        drawText(g);
    }

    @Override
    public void update(){
        setBounds(parent.x, parent.y + parent.h - h, parent.w, h);
    }

    public void drawBox(PGraphics g){
        g.noStroke();
        g.fill(boxColor);
        g.rect(x,y,w,h);
    }

    public void drawText(PGraphics g){
        g.textSize(h);
        String dtext = "";
        if(bodyText.length() > 0){
            g.fill(bodyTextColor);
            dtext = bodyText;
        }else{
            g.fill(blankTextColor);
            dtext = blankText;
        }
        g.text(dtext, x, y + h - h/4);
    }

    public void drawCursor(PGraphics g){

    }

    public ArrayList<Tag> parseTags(){
        ArrayList<Tag> output = new ArrayList<Tag>();
        String s = bodyText;
        boolean buildingTag = false;
        int startTag = 0;

        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(!buildingTag){
                if (c == '#' && (i == 0 || s.charAt(i-1) == ' ')){
                    startTag = i;
                    buildingTag = true;
                }
            }
            if(buildingTag){
                if(c == ' ') {
                    String newS = s.substring(startTag, i);
                    buildingTag = false;
                    output.add(new Tag(newS));
                } else if (i == s.length() - 1){
                    String newS = s.substring(startTag);
                    buildingTag = false;
                    output.add(new Tag(newS));
                }
            }
        }
        return output;
    }

    public void type(char key, int kc){
        if (key == '\b') { // BACKSPACE
            bodyText = bodyText.substring(0, bodyText.length() - 1);
        }else{
            bodyText += key;
        }
    }
}
