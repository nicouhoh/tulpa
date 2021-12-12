class Clipping{
  
  PImage img;
  int xpos;
  int ypos;
  
  Clipping(String imagePath){
    print(imagePath);
    img = loadImage(imagePath);
  }
  
  void display(int clipW, int clipH){
    int w = img.width;
    int h = img.height;
    int airW = 0;
    int airH = 0;
    if (img.width >= img.height){      
      w = clipW;
      h = img.height / (img.width / clipW);  
      airH = (clipH - h) / 2;
    } else {
      h = clipH;
      w = img.width / (img.height / clipH);
      airW = (clipW - w) / 2;
    }
    image(img, xpos + airW, ypos + airH , w, h);
  }
  
  void setPos(int x, int y){
    xpos = x;
    ypos = y;
  }
}
