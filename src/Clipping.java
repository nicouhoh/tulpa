import processing.core.PGraphics;
import processing.core.PImage;

import java.io.File;


public class Clipping{

  String id;

  PImage img;
  String imgPath;

  String bodyText;
  
  float xpos;
  float ypos;
  float displayW;
  float displayH;
  float airW;
  float airH;
  
  boolean selected;
  boolean onscreen;
  boolean zoom;
  
  public Clipping(File file, String idIn){
    id = idIn;
    imgPath = file.getAbsolutePath();
    img = tulpa.SOLE.loadImage(imgPath);
  }
  
  public void update(PGraphics g, float latitude){
    if(ypos > -latitude - tulpa.SOLE.height/2 && ypos < -latitude + tulpa.SOLE.height * 1.5){
      onscreen = true;
      display(g);
      displaySelect(g);
    }
    else onscreen = false;

  }
  
  public void display(PGraphics g){
    g.image(img, xpos + airW, ypos + airH , displayW, displayH);
  }

  public void display(PGraphics g, int x, int y, int w, int h){
    g.image(img, x, y, w, h);
  }

  public void zoomDisplay(PGraphics g, float fieldW, float fieldH){
    g.image(img, fieldW / 2 - img.width / 2, fieldH / 2 - img.height / 2, img.width, img.height);
  }

  public void displaySelect(PGraphics g){
    if(selected){
      g.stroke(tulpa.SOLE.color(255));
      g.noFill();
      g.rect(xpos +airW, ypos + airH, displayW, displayH);
    }
  }
  
  public void setPos(float x, float y){
    xpos = x;
    ypos = y;
  }
  
  public void setSize(float clipW, float clipH){
    float w = img.width;
    float h = img.height;
    if (img.width >= img.height){
      w = clipW;
      h = img.height / (img.width / clipW);
      airH = (clipH - h) / 2;
    } else {
      h = clipH;
      w = img.width / (img.height / clipH);
      airW = (clipW - w) / 2;
    }
    displayW = w;
    displayH = h;
  }
  
  public boolean clicked(){
    if (tulpa.SOLE.mouseX >= xpos + airW && tulpa.SOLE.mouseX <= xpos + airW + displayW &&
        tulpa.SOLE.mouseY >= ypos + airH + tulpa.SOLE.field.latitude
        && tulpa.SOLE.mouseY <= ypos + airH + displayH + tulpa.SOLE.field.latitude){
          return true;
     }else{
       return false;
     }
  }
}
