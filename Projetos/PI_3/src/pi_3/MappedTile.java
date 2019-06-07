package pi_3;

import java.util.ArrayList;
import java.util.List;

public class MappedTile {
    public int id, tileId, x, y;
    private int h, g, f;
    private boolean bloqueado, visitado;
    private MappedTile pai;
    protected static final int MOVEMENT_COST = 10;
    public  List<MappedTile> vizinhos = new ArrayList();

    public MappedTile(int tileId, int x, int y) {
        this.tileId = tileId;
        this.x = x;
        this.y = y;
    }
    
    public MappedTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MappedTile getPai() {
        return pai;
    }

    public void setPai(MappedTile pai) {
        this.pai = pai;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public List<MappedTile> getVizinhos() {
        return vizinhos;
    }

    public int getTileId() {
        return tileId;
    }
    
    public int getId() {
        return id;
    }

    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getH() {
        return h;
    }

    public void setG(MappedTile parent)
	{
		g = (parent.getG() + MOVEMENT_COST);
	}

    public void setG(int g) {
        this.g = g;
    }

    public float getF() {
        return g + h;
    }

    public void setF(int f) {
        this.f = f;
    }
    
    public int calculateG(MappedTile parent)
	{
		return (parent.getG() + MOVEMENT_COST);
	}
    
    public void setH(MappedTile goal)
	{
		h = (Math.abs(getX() - goal.getX()) + Math.abs(getY() - goal.getY())) * MOVEMENT_COST;
	}
    
    public MappedTile getParent()
	{
		return pai;
	}
    
    public void setParent(MappedTile parent)
    {
            this.pai = parent;
    }
    
    public int getG()
	{
		return g;
	}
    
    @Override
    public boolean equals(Object o)
    {
            if (o == null)
                    return false;
            if (!(o instanceof MappedTile))
                    return false;
            if (o == this)
                    return true;

            MappedTile n = (MappedTile) o;
            if (n.getX() == x && n.getY() == y && n.getBloqueado() == bloqueado)
                    return true;
            return false;
    }
}