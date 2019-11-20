package src.engine.ai;

public class TileScore {
    private Integer posX;
    private Integer posY;
    private Integer score;

    public TileScore(Integer posX, Integer posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPosX() {
        return posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public Integer getScore() {
        return score;
    }
}
