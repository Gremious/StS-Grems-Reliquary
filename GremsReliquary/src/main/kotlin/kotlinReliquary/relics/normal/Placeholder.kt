package kotlinReliquary.relics.normal

import com.megacrit.cardcrawl.relics.AbstractRelic
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class Placeholder : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.RARE, AbstractGremRelic.RelicType.NORMAL, AbstractRelic.LandingSound.CLINK) {

    companion object {
        val ID = GremsReliquary.makeID(Placeholder::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/Placeholder.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/Placeholder.png")
    }


    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}