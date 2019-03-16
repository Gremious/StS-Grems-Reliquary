package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import gremsReliquary.GremsReliquary;

import static com.megacrit.cardcrawl.core.CardCrawlGame.sound;

public class TimeIsMoney extends CustomRelic {
    public static final String ID = GremsReliquary.makeID(TimeIsMoney.class.getSimpleName());
    public static final String IMG = GremsReliquary.getModID() + "Resources/images/relics/placeholder_relic.png";
    public static final String OUTLINE = GremsReliquary.getModID() + "Resources/images/relics/outline/placeholder_relic.png";
    public static int amount = 1;

    private boolean usedThisTurn = false;

    public TimeIsMoney() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.CLINK);
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