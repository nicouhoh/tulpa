import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Line {

    char[] chars;

    public Line(){}

    public Line(char[] chars){
        this.chars = chars;
    }

    public Line(char[] input, int start, int stop){
        this.chars = new char[stop - start];
        if (stop - start > 0){
            System.arraycopy(input, start, chars, 0, stop - start);
        }
    }

    public ArrayList<Word> getWords(){
        ArrayList<Word> result = new ArrayList<Word>();
        int wordStart = 0;
        for (int i = 0; i < chars.length; i++){
            if (chars[i] == ' '){
                result.add(new Word(chars, wordStart, i));
                wordStart = i + 1;
            }
            else if (i == chars.length - 1){
                result.add(new Word(chars, wordStart, i + 1));
            }
        }
        return result;
    }

    public float getWidth(PGraphics g){
        return g.textWidth(chars, 0, chars.length);
    }
}