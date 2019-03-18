package gremsReliquary.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gremsReliquary.GremsReliquary;

public class BrokenMirror extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(BrokenMirror.class.getSimpleName());
    public static final String IMG = GremsReliquary.getModID() + "Resources/images/relics/placeholder_relic.png";
    public static final String OUTLINE = GremsReliquary.getModID() + "Resources/images/relics/outline/placeholder_relic.png";
    public static int amount = 2;
    AbstractCreature p = AbstractDungeon.player;
    
    public BrokenMirror() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);
    }
    
    @Override
    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (card.type == AbstractCard.CardType.ATTACK
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            flash();
            AbstractMonster rm = AbstractDungeon.getRandomMonster(null);
            act(new DamageRandomEnemyAction(new DamageInfo(p, 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}