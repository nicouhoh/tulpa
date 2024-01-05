import processing.core.PGraphics;

public class Scribe {

    int[] textBreakStart;
    int[] textBreakStop;
    int textBreakCount;

    int gravity = 1;

    public void text(PGraphics g, String str, int cursorPos, float writeX, float writeY, float writeW, float writeH){
        if (g.textFont == null) return;

        char[] input = stringToArray(str);

        int wordBreak = 0;
        float x = 0;
        int[] linebreaks = new int[input.length + 1];
        linebreaks[0] = -1;
        int index = 1;

        for (int i = 0; i < input.length; ++i){
            char currentChar = input[i];

            // newline
            if (currentChar == '\n'){
                x = 0;
                linebreaks[index++] = i;
                wordBreak = i;
            }

            // word break
            if (currentChar == ' '){
                wordBreak = i;
            }

            // wrap lines
            if (x + g.textWidth(currentChar) > writeW){
                linebreaks[index] = wordBreak;
                x = textWidth(g, input, linebreaks[index], i);
                index++;
                if (i != input.length - 1) continue;
            }

            // end of buffer
            if (i == input.length - 1){
                linebreaks[index] = i + 1;
            }

            x += g.textWidth(currentChar);
        }


        if (gravity == 2){
            int lineStart = linebreaks[index - 1];
            float y = writeY + writeH - g.textDescent();
            for (int i = index; i > 0; i--){
                writeLine(g, input, linebreaks[i - 1] + 1, linebreaks[i], cursorPos, writeX, y);
                y -= g.textLeading;
                lineStart = linebreaks[i];
            }
        }
        else {
            float y = writeY + g.textAscent();
            for (int i = 0; i < index; i++) {
                if (i >= linebreaks.length - 1) return;
                writeLine(g, input, linebreaks[i] + 1, linebreaks[i + 1], cursorPos, writeX, y);
                y += g.textLeading;
            }
        }

//        debugDrawTextBox(g, writeX, writeY, writeW, writeH);
    }

    public void writeLine(PGraphics g, char[] buffer, int start, int stop, int cursorPos, float x, float y){
        for (int i = start; i <= stop; ++i){
            if (i == cursorPos){
                drawCursor(g, x, y);
            }
            if (i == stop) continue;
            g.text(buffer[i], x, y);
//            debugCharBoxes(g, buffer[i], x, y);
            x += g.textWidth(buffer[i]);
        }
    }

    public char[] stringToArray(String str){
        // convert string to an array of chars and add a newline at the end for reasons
        char[] chars = new char[str.length()];
        str.getChars(0, str.length(), chars, 0);
        return chars;
    }

    public float textWidth(PGraphics g, char[] line, int start, int stop){
        float wide = 0;
        for (int i = start; i < stop; ++i){
            wide += g.textWidth(line[i]);
        }
        return wide;
    }

    public void setGravity(int mode){
        gravity = mode;
    }

    public void setGravity(String mode){
        switch(mode){
            case "top", "default" -> setGravity(0);
            case "bottom" -> setGravity(2);
        }
    }

    public void drawCursor(PGraphics g, float x, float y){
        g.stroke(255, 223, 0);
        g.strokeWeight(2);
        g.line(x, y - g.textAscent(), x, y);
    }

    public void debugDrawTextBox(PGraphics g, float x, float y, float w, float h){
        g.stroke(255, 0, 255);
        g.strokeWeight(1);
        g.noFill();
        g.rect(x, y, w, h);
    }

    public void debugCharBoxes(PGraphics g, char c, float x, float y){
        g.stroke(255, 255, 0);
        g.noFill();
        g.rect(x, y - g.textAscent(), g.textWidth(c), g.textAscent() + g.textDescent());
    }



}
