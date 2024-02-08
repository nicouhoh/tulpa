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
        if (gapIsClosed()) growBuffer(5);
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
        if (!gapIsAtEnd()) {
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

    public void growBuffer(int newGap){
        buffer = copyToLargerBuffer(newGap);
        gapEnd += newGap;
    }

    private char[] copyToLargerBuffer(int newGap) {
        char[] newBuffer = new char[buffer.length + newGap];
        copyBufferBeforeGap(newBuffer);
        if (!gapIsAtEnd()) copyBufferAfterGap(newBuffer, newGap);
        return newBuffer;
    }

    private void copyBufferBeforeGap(char[] newBuffer){
        System.arraycopy(buffer, 0, newBuffer, 0, gapStart);
    }

    private void copyBufferAfterGap(char[] newBuffer, int newGap){
        System.arraycopy(buffer, gapEnd + 1, newBuffer, gapEnd + newGap + 1, buffer.length - gapEnd - 1);
    }

    public String debugString(){
        String string = new String(buffer);
        return string;
    }

    public boolean gapIsAtEnd(){
        return gapEnd >= buffer.length - 1;
    }

    public boolean gapIsClosed(){
        return gapStart == gapEnd;
    }


    @Override
    public String toString(){
        char[] chars = removeGap();
        return new String(chars);
    }

    public char[] removeGap(){
        char[] chars = new char[buffer.length - gapSize()];
        copyBufferBeforeGap(chars);
        if (!gapIsAtEnd()) {
            System.arraycopy(buffer, gapEnd + 1, chars, gapStart, buffer.length - gapEnd - 1);
        }
        return chars;
    }

    public int gapSize(){
        return gapEnd - gapStart + 1;
    }

    public String[] toWords(){
        return toString().trim().split(" +|\n+");
    }

    public boolean isEmpty(){
        return gapEnd - gapStart >= buffer.length - 1;
    }

    public void clear() {
        buffer = new char[50];
        gapStart = 0;
        gapEnd = 49;
    }
}
