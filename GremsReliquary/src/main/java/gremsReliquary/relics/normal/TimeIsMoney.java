package gremsReliquary.relics.normal;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import gremsReliquary.GremsReliquary;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;

import static com.megacrit.cardcrawl.core.CardCrawlGame.sound;

public class TimeIsMoney extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(TimeIsMoney.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/BrokenMirror.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/BrokenMirror.png");
    public static int amount = 2;
    
    private boolean usedThisTurn = false;
    
    public TimeIsMoney() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, GremRelicType.NORMAL, LandingSound.CLINK);
    }
    
    public void atTurnStart() {
        AbstractCreature source = AbstractDungeon.player;
        flash();
        sound.play("GOLD_JINGLE");
        AbstractDungeon.player.gainGold(amount);
        for (int i = 0; i < amount; ++i) {
            AbstractDungeon.effectList.add(new GainPennyEffect(source, this.currentX, this.currentY, source.drawX, source.drawY, true));
        }
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}