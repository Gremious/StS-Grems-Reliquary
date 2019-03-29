package gremsReliquary.relics.cursed;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremsReliquary.GremsReliquary;
import gremsReliquary.powers.CursedStrengthPower;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;

public class SternwardSword extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(SternwardSword.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/placeholder_relic.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/placeholder_relic.png");
    public static int amount = 2;
    
    AbstractCreature p = AbstractDungeon.player;
    
    public SternwardSword() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, RelicType.CURSED, LandingSound.CLINK);
    }
    
    @Override
    public void onEquip() {
        
    }
    
    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, this));
        act(new ApplyPowerAction(p, p, new CursedStrengthPower(p, p, -3), -3));
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}