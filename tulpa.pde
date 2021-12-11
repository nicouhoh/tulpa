Wranglist wranglist;

void setup(){
  surface.setSize(500, 500);
  surface.setResizable(true);
  wranglist = new Wranglist();
}

void draw(){
  wranglist.library[19].display();
}
