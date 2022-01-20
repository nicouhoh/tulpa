class Clipping{
  
  PImage img;
  int xpos;
  int ypos;
  int displayW;
  int displayH;
  int airW;
  int airH;
  
  Clipping(String imagePath){
    println(imagePath);
    img = loadImage(imagePath);
  }
  
  void display(float latitude){
    if(ypos > -latitude - height/2 && ypos < -latitude + height * 1.5){
      image(img, xpos + airW, ypos + airH , displayW, displayH);
    }
  }
  
  void setPos(int x, int y){
    xpos = x;
    ypos = y;
  }
  
  void setSize(int clipW, int clipH){
    int w = img.width;
    int h = img.height;
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
}
