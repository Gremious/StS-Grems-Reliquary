package gremsReliquary.relics.cursed;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.SuperRareRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremsReliquary.GremsReliquary;
import gremsReliquary.util.TextureLoader;
import kotlinReliquary.relics.AbstractGremRelic;

public class DiabolicDiabola extends AbstractGremRelic implements SuperRareRelic {
    public static final String ID = GremsReliquary.makeID(DiabolicDiabola.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/DiabolicDiabola.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/DiabolicDiabola.png");
    public static int amount = 2;
    
    AbstractCreature p = AbstractDungeon.player;
    
    public DiabolicDiabola() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, RelicType.CURSED, LandingSound.MAGICAL);
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}