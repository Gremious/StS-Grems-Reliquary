package kotlinReliquary.relics.cursed

import com.evacipated.cardcrawl.mod.stslib.relics.SuperRareRelic
import com.megacrit.cardcrawl.relics.AbstractRelic
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class DiabolicDiabola : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.COMMON, AbstractGremRelic.RelicType.CURSED, AbstractRelic.LandingSound.MAGICAL), SuperRareRelic {
    companion object {
        val ID = GremsReliquary.makeID(DiabolicDiabola::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/DiabolicDiabola.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/DiabolicDiabola.png")
        var amount = 2
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }
}
