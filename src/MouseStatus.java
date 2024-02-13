public class MouseStatus {

    private Organelle hotItem;
    private Organelle activeItem;
    private Organelle preventUnclick; // use in special cases to prevent a buttonPress afer mouseDown. see ctrl/cmd click on Thumbnails
    private Dropzone hoveredZone;
    private Squeak lastSqueak;

    public Organelle getHotItem() {
        return hotItem;
    }

    public void setHotItem(Organelle organelle){
        if (hotItem != null && hotItem != organelle){
            clearHotItem();
        }
        hotItem = organelle;
        if (hotItem != null) hotItem.setHot(true);
    }

    public void clearHotItem(){
        if (hotItem == null) return;
        hotItem.setHot(false);
        hotItem = null;
    }

    public Organelle getActiveItem(){
        return activeItem;
    }

    public void setActiveItem(Organelle organelle){
        if (activeItem != null && activeItem != organelle){
            clearActiveItem();
        }
        activeItem = organelle;
        activeItem.setHot(true);
    }

    public void clearActiveItem(){
        if (activeItem == null) return;
        activeItem.setActive(false);
        activeItem = null;
    }

    public Organelle getPreventUnclick(){
        return preventUnclick;
    }

    public void setPreventUnclick(Organelle organelle){
        preventUnclick = organelle;
    }

    public void clearPreventUnclick(){
        preventUnclick = null;
    }

    public Dropzone getHoveredZone(){
        return hoveredZone;
    }

    public void setHoveredZone(Dropzone zone){
        clearHoveredZone();

        hoveredZone = zone;
        if (zone != null){
            hoveredZone.setHovered(true);
            hoveredZone.onHovered();
        }
    }

    public void clearHoveredZone(){
        if (hoveredZone != null) hoveredZone.setHovered(false);
        hoveredZone = null;
    }

    public Squeak getLastSqueak(){
        return lastSqueak;
    }

    public void setLastSqueak(Squeak squeak){
        this.lastSqueak = squeak;
    }
}
