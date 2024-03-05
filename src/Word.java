import processing.core.PGraphics;

public class Word {

    char[] chars;

    public Word(char[] input, int start, int stop){
        this.chars = new char[stop - start];
        if (stop - start > 0){
            System.arraycopy(input, start, chars, 0, stop - start);
        }
    }

    public float getWidth(PGraphics g){
        return g.textWidth(chars, 0, chars.length);
    }
}