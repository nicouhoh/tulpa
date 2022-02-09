
import processing.core.PGraphics;
import processing.core.PImage;

import java.io.File;


public class Clipping{

  PImage img;
  String path;
  String id;
  
  float xpos;
  float ypos;
  float displayW;
  float displayH;
  float airW;
  float airH;
  
  boolean selected;
  boolean onscreen;
  
  public Clipping(File file, String idIn){
    id = idIn;
    path = file.getAbsolutePath();
    img = tulpa.SOLE.loadImage(path);
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
    } //<>//
    
  
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

  public void setSizeSardine(float clipH){
    float w = img.width;
    w = img.width / (img.height / clipH);
    displayW = w;
    displayH = clipH;
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
