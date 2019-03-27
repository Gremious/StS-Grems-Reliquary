package gremsReliquary.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BrokenRecordAction extends AbstractGameAction {
    AbstractCard card;
    UseCardAction useCardAction;
    
    public BrokenRecordAction(AbstractCard card, UseCardAction useCardAction) {
        this.card = card;
        this.useCardAction = useCardAction;
        
        duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (!card.purgeOnUse) {
                AbstractMonster m = null;
                if (useCardAction.target != null) {
                    m = (AbstractMonster) useCardAction.target;
                }
                
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                tmp.freeToPlayOnce = true;
                if (m != null) {
                    tmp.calculateCardDamage(m);
                }
                
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, card.energyOnUse));
            }
            tickDuration();
        }
        tickDuration();
    }
}
