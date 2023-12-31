import processing.core.PGraphics;

import java.util.Arrays;

public class Scribe {


    int[] textBreakStart;
    int[] textBreakStop;
    int textBreakCount;

    public void text(PGraphics g, String str, int cursorPos, float writeX, float writeY, float writeW, float writeH) {
        if (g.textFont == null) return;

        // convert string to an array of chars
        char[] chars = new char[str.length() + 1];
        str.getChars(0, str.length(), chars, 0);
        chars[str.length()] = '\n';

        int lineStart = 0;
        int lineStop = 0;
        int wordBreak = 0;
        float x = writeX;
        float y = writeY + g.textAscent();

        for (int i = 0; i < chars.length; ++i) {

            char currentChar = chars[i];

            if (i == cursorPos){
                g.stroke(255, 223, 0);
                g.strokeWeight(2);
                g.line(x, y - g.textAscent(), x, y);
            }
            // newline
            if (currentChar == '\n') {
                lineStop = i;
                x = writeX;
                writeLine(g, chars, lineStart, lineStop, x, y);
                y += g.textLeading;
                lineStart = i + 1;
                wordBreak = i;
                continue;
            }

            // word break
            if (currentChar == ' '){
                wordBreak = i;
            }

            // wrap lines
            if (x + g.textWidth(currentChar) > writeX + writeW){
                lineStop = wordBreak;
                x = writeX;
                writeLine(g, chars, lineStart, lineStop, x, y);
                y += g.textLeading;
                lineStart = wordBreak + 1;
                x += textWidth(g, chars, lineStart, i + 1);
                continue;
            }

            x += g.textWidth(currentChar);
        }
//        debugDrawTextBox(g, writeX, writeY, writeW, writeH);
    }

    public void writeLine(PGraphics g, char[] buffer, int start, int stop, float x, float y){
        for (int i = start; i < stop; ++i){
            g.text(buffer[i], x, y);
//            debugCharBoxes(g, buffer[i], x, y);
            x += g.textWidth(buffer[i]);
        }
    }

    public float textWidth(PGraphics g, char[] line, int start, int stop){
        float wide = 0;
        for (int i = start; i < stop; ++i){
            wide += g.textWidth(line[i]);
        }
        return wide;
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
