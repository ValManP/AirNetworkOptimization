package impl.algorithms.genetics.entities;

public class NetworkAllele {
    private boolean isUsed;
    private int position;

    public NetworkAllele(boolean isUsed, int position) {
        this.isUsed = isUsed;
        this.position = position;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
