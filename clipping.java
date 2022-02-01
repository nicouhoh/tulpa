class Clipping{
  
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
  
  
  Clipping(Library libraryIn, File file){
    library = libraryIn;
    id = library.getid();
    path = file.getAbsolutePath();
    img = loadImage(path);
  }
  
  void update(float latitude){
    if(ypos > -latitude - height/2 && ypos < -latitude + height * 1.5){
      display();
      displaySelect();
      mouseOver();
      clickSelect();
    }
  }
  
  void display(){
      image(img, xpos + airW, ypos + airH , displayW, displayH);
    } //<>//
    
  
  void displaySelect(){
    if(selected){
      stroke(color(255));
      noFill();
      rect(xpos +airW, ypos + airH, displayW, displayH);
    }
  }
  
  void setPos(float x, float y){
    xpos = x;
    ypos = y;
  }
  
  void setSize(float clipW, float clipH){
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
  
  void mouseOver(){
    if (mouseX >= xpos + airW && mouseX <= xpos + airW + displayW &&
        mouseY >= ypos + airH + field.latitude && mouseY <= ypos + airH + displayH + field.latitude){
          hover = true;
     }else{
       hover = false;
     }
  }
  
  void clickSelect(){
    if (input.click && hover){
      selected = true;
    }else if (input.click && !hover){
      selected = false;
    }
  }
}