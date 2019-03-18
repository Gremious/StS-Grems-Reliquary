package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

public class AbstractGremRelic extends CustomRelic {
    
    public AbstractGremRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, "", tier, sfx);
    }
}
