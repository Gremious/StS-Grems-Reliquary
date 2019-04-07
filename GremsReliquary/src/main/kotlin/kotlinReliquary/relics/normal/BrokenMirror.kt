package kotlinReliquary.relics.normal

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rooms.AbstractRoom
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class BrokenMirror : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractGremRelic.RelicType.NORMAL, AbstractRelic.LandingSound.CLINK) {
    companion object {
        val ID = GremsReliquary.makeID(BrokenMirror::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/BrokenMirror.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/BrokenMirror.png")
        var damageAmount = 2
    }

    override fun onUseCard(card: AbstractCard, useCardAction: UseCardAction?) {
        if (card.type == AbstractCard.CardType.ATTACK
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash()
            act(DamageRandomEnemyAction(DamageInfo(p, damageAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT))
        }
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + damageAmount + DESCRIPTIONS[1]
    }
}