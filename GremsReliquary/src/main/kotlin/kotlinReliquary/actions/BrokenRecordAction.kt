package kotlinReliquary.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class BrokenRecordAction(var card: AbstractCard, var useCardAction: UseCardAction) : AbstractGameAction() {

    init {
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (!card.purgeOnUse) {
                var m: AbstractMonster? = null
                if (useCardAction.target != null) {
                    m = useCardAction.target as AbstractMonster
                }

                val tmp = card.makeSameInstanceOf()
                AbstractDungeon.player.limbo.addToBottom(tmp)
                tmp.current_x = card.current_x
                tmp.current_y = card.current_y
                tmp.target_x = Settings.WIDTH.toFloat() / 2.0f - 300.0f * Settings.scale
                tmp.target_y = Settings.HEIGHT.toFloat() / 2.0f
                tmp.freeToPlayOnce = true
                if (m != null) {
                    tmp.calculateCardDamage(m)
                }

                tmp.purgeOnUse = true
                AbstractDungeon.actionManager.cardQueue.add(CardQueueItem(tmp, m, card.energyOnUse))
            }
            tickDuration()
        }
        tickDuration()
    }
}
