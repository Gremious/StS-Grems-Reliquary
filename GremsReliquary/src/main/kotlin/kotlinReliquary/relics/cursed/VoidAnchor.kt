package kotlinReliquary.relics.cursed

import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class VoidAnchor : AbstractGremRelic(ID, IMG, OUTLINE, RelicTier.UNCOMMON, RelicType.CURSED, LandingSound.CLINK) {

    companion object {
        val ID = GremsReliquary.makeID(VoidAnchor::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/VoidAnchor.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/VoidAnchor.png")
        var amount = 15
    }

    override fun atBattleStart() {
        for (c in AbstractDungeon.player.drawPile.skills.group) {
            addExhaustAndEthereal(c)
        }
        for (c in AbstractDungeon.player.discardPile.skills.group) {
            addExhaustAndEthereal(c)
        }
        for (c in AbstractDungeon.player.exhaustPile.skills.group) {
            addExhaustAndEthereal(c)
        }
    }

    private fun addExhaustAndEthereal(c: AbstractCard) {
        c.exhaust = true
        c.isEthereal = true

        if (!c.rawDescription.contains(this.DESCRIPTIONS[2])) {
            c.rawDescription += this.DESCRIPTIONS[2]
        }

        if (!c.rawDescription.contains(this.DESCRIPTIONS[3])) {
            c.rawDescription += this.DESCRIPTIONS[3]
        }

    }

    override fun atTurnStart() {
        act(RelicAboveCreatureAction(AbstractDungeon.player, this))
        act(GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, amount))
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }
}