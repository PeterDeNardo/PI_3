package pi_3;

import java.util.ArrayList;
import java.util.List;

public class MappedTile {
    public int id, tileId, x, y;
    private float h, g, f;
    private boolean bloqueado, visitado;
    private MappedTile pai;
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

    public int getId() {
        return id;
    }

    public boolean estaBloqueado() {
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

    public void setH(float h) {
        this.h = h;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }
}