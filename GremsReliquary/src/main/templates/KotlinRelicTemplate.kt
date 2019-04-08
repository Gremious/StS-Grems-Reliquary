package gremsReliquary.templates;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class KotlinRelicTemplate : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractGremRelic.RelicType.NORMAL, AbstractRelic.LandingSound.CLINK) {

    companion object {
        val ID = GremsReliquary.makeID(KotlinRelicTemplate::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/placeholder_relic.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/placeholder_relic.png")
        var amount = 0
    }

    override fun onEquip() {

    }

    override fun atBattleStart() {

    }

    override fun atTurnStart() {
        AbstractDungeon.actionManager.addToTop(RelicAboveCreatureAction(AbstractDungeon.player, this))
        flash()
    }

    override fun onUseCard(card: AbstractCard?, useCardAction: UseCardAction?) {

    }

    override fun onPlayerEndTurn() {

    }

    override fun onVictory() {

    }

    override fun onUnequip() {

    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }
}