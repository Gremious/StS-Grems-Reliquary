package gremsReliquary.relics.normal;

import com.badlogic.gdx.graphics.Texture;
import gremsReliquary.GremsReliquary;
import gremsReliquary.util.TextureLoader;
import kotlinReliquary.relics.AbstractGremRelic;

public class Placeholder extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(Placeholder.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/Placeholder.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/Placeholder.png");
    
    public Placeholder() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, RelicType.NORMAL, LandingSound.CLINK);
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}