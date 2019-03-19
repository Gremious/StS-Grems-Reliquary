package gremsReliquary.relics.cursed;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremsReliquary.GremsReliquary;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;

public class CursedBottle extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(CursedBottle.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/placeholder_relic.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/placeholder_relic.png");
    public static int amount = 2;
    
    AbstractCreature p = AbstractDungeon.player;
    
    public CursedBottle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, RelicType.CURSED, LandingSound.CLINK);
    }
    
    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        flash();
        // add the bottled card to your hand
    }
    
    @Override
    public void onPlayerEndTurn() {
        // shuffle a random ot specific curse into your discard pile
    }
    
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}