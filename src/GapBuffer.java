import java.util.Arrays;

public class GapBuffer {

    // TODO Can we also think about lines and possibly even words in this data structure?


    char[] buffer;
    int gapStart;
    int gapEnd;

    public GapBuffer(int capacity){
        buffer = new char[capacity];
        gapStart = 0;
        gapEnd = capacity - 1;
    }

    public GapBuffer(String string){
        int capacity = string.length() + 20;
        buffer = new char[capacity];
        System.arraycopy(string.toCharArray(), 0, buffer, 0, string.length());
        gapStart = string.length();
        gapEnd = buffer.length - 1;
    }

    public void insert(char c){
        if (gapStart == gapEnd) resizeBuffer(5);
        buffer[gapStart] = c;
        gapStart++;
    }

    public void delete(){
        if (gapStart > 0){
            gapStart--;
            buffer[gapStart] = '\0';
        }
    }

    public void cursorLeft(int position){
        while (position < gapStart && gapStart > 0) {
            cursorLeft();
        }
    }

    public void cursorLeft(){
        if (gapStart > 0) {
            gapStart--;
            gapEnd--;
            buffer[gapEnd + 1] = buffer[gapStart];
            buffer[gapStart] = '\0';
        }
    }

    public void cursorRight(int position){
        while (position > gapStart && gapEnd < buffer.length - 1) {
            cursorRight();
        }
    }

    public void cursorRight() {
        if (gapEnd < buffer.length - 1) {
            gapStart++;
            gapEnd++;
            buffer[gapStart - 1] = buffer[gapEnd];
            buffer[gapEnd] = '\0';
        }
    }

    public void moveCursor(int position){
        if (position < gapStart) cursorLeft(position);
        if (position > gapStart) cursorRight(position);
    }

    public void resizeBuffer(int newGap){
        char[] newBuffer = new char[buffer.length + newGap];
        System.arraycopy(buffer, 0, newBuffer, 0, gapStart);
        if (gapEnd < buffer.length - 1) {
            System.arraycopy(buffer, gapEnd + 1, newBuffer, gapEnd + newGap + 1, buffer.length - gapEnd - 1);
        }
        gapEnd += newGap;
        buffer = newBuffer;
    }

    public String debugString(){
        String string = new String(buffer);
        return string;
    }

    @Override
    public String toString(){
        char[] chars = removeGap();
        return new String(chars);
    }

    public char[] removeGap(){
        int gapSize = (gapEnd - gapStart) + 1;
        char[] chars = new char[buffer.length - gapSize];
        System.arraycopy(buffer, 0, chars, 0, gapStart);
        if (gapEnd < buffer.length - 1) {
            System.arraycopy(buffer, gapEnd + 1, chars, gapStart, buffer.length - gapEnd - 1);
        }
        return chars;
    }

    // these are a temporary hack until I figure out better text
    public char[] removeGapAndAddCursor(){
        int gapSize = (gapEnd - gapStart) + 1;
        char[] chars = new char[buffer.length - gapSize + 1];
        System.arraycopy(buffer, 0, chars, 0, gapStart);
        chars[gapStart] = '|';
        if (gapEnd < buffer.length - 1) {
            System.arraycopy(buffer, gapEnd + 1, chars, gapStart + 1, buffer.length - gapEnd - 1);
        }
        return chars;
    }

    public String toStringWithCursor(){
        char[] chars = removeGapAndAddCursor();
        return new String(chars);
    }

    public String[] toWords(){
        return toString().trim().split(" +|\n+");
    }

    public boolean isEmpty(){
        if (gapEnd - gapStart >= buffer.length - 1) return true;
        return false;
    }

    public void clear() {
        buffer = new char[50];
        gapStart = 0;
        gapEnd = 49;
    }

//    public String toString(){
//        int gapSize = gapEnd - gapStart;
//        char[] chars = new char[buffer.length - gapSize];
//        System.arraycopy(buffer, 0, chars, 0, gapStart);
//        chars[gapStart] = '_';
//        if (gapEnd < buffer.length - 1) {
//            System.arraycopy(buffer, gapEnd + 1, chars, gapStart + 1, buffer.length - 1);
//        }
//        return new String(chars);
//    }
}
