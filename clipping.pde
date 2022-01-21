class Clipping{
  
  PImage img;
  float xpos;
  float ypos;
  float displayW;
  float displayH;
  float airW;
  float airH;
  
  Clipping(String imagePath){
    println(imagePath);
    img = loadImage(imagePath);
  }
  
  void display(float latitude){
    if(ypos > -latitude - height/2 && ypos < -latitude + height * 1.5){
      image(img, xpos + airW, ypos + airH , displayW, displayH);
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
      println("1");
      h = img.height / (img.width / clipW);
      airH = (clipH - h) / 2;
    } else {
      h = clipH;
      println("2");
      w = img.width / (img.height / clipH);
      airW = (clipW - w) / 2;
    }
    displayW = w;
    displayH = h;
  }
}
