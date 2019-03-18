package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AbstractGremRelic extends CustomRelic {
    
    public AbstractGremRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, "", tier, sfx);
    }
    
    public static void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
}
