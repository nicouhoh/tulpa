
import processing.core.PGraphics;
import processing.core.PImage;

import java.io.File;


public class Clipping{
  
  Library library;
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
  boolean hover;
  
  
  public Clipping(Library libraryIn, File file){
    library = libraryIn;
    id = library.getid();
    path = file.getAbsolutePath();
    img = tulpa.SOLE.loadImage(path);
  }
  
  public void update(PGraphics g, float latitude){
    if(ypos > -latitude - tulpa.SOLE.height/2 && ypos < -latitude + tulpa.SOLE.height * 1.5){
      display(g);
      displaySelect(g);
      mouseOver();
      clickSelect();
    }
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
  
  public void mouseOver(){
    if (tulpa.SOLE.mouseX >= xpos + airW && tulpa.SOLE.mouseX <= xpos + airW + displayW &&
        tulpa.SOLE.mouseY >= ypos + airH + tulpa.SOLE.field.latitude
        && tulpa.SOLE.mouseY <= ypos + airH + displayH + tulpa.SOLE.field.latitude){
          hover = true;
     }else{
       hover = false;
     }
  }
  
  public void clickSelect(){
    if (tulpa.SOLE.input.click && hover){
      selected = true;
    }else if (tulpa.SOLE.input.click && !hover){
      selected = false;
    }
  }
}
