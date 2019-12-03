package src.entities.fixed.generic;

import src.entities.fixed.specific.TileContentType;
import src.entities.space.specific.TileSprite;

/**
 * Created by Vincent on 01/11/2019.
 */
public abstract class TileContent {

    private TileSprite sprite;

    public abstract TileContentType getContentType();

    public TileSprite getSprite() {
        return sprite;
    }

    public void setSprite(TileSprite sprite) {
        this.sprite = sprite;
    }
}
