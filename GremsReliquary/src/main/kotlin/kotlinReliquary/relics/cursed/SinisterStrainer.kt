package kotlinReliquary.relics.cursed;

import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.relics.AbstractRelic
import gremsReliquary.GremsReliquary
import gremsReliquary.interfaces.BetterBetterOnUsePotionRelic
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class SinisterStrainer : AbstractGremRelic(
        ID,
        IMG,
        OUTLINE,
        AbstractRelic.RelicTier.UNCOMMON,
        AbstractGremRelic.RelicType.CURSED,
        AbstractRelic.LandingSound.CLINK),
        BetterBetterOnUsePotionRelic {

    companion object {
        val ID = GremsReliquary.makeID(SinisterStrainer::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/placeholder_relic.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/placeholder_relic.png")
        var hpDamage = 4
    }

    override fun betterBetterOnUsePotion(potion: AbstractPotion, target: AbstractCreature?) {
        flash()
        val secondPot: AbstractPotion = potion
        secondPot.use(target)
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + hpDamage + DESCRIPTIONS[1]
    }
}