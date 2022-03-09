package model.obstacle;

public class Particle {
    private String type;
    private int width;
    private int height;
    private double posx;
    private double posy;

    public Particle(String particletype, int width, int height, double posx, double posy) {
        this.type = particletype;
        this.width = width;
        this.height = height;
        this.posx = posx;
        this.posy = posy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getPosx() {
        return posx;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public double getPosy() {
        return posy;
    }

    public void setPosy(double d) {
        this.posy = d;
    }    
}
