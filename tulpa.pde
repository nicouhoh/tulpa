int pillow = 5;
String path;
Wranglist wranglist;

void setup(){
  size(400, 400);
  surface.setResizable(true);
  path = sketchPath();
  wranglist = new Wranglist();
  wranglist.FussMenagerie();
}

void draw(){
    wranglist.PresentMenagerie();
  }
