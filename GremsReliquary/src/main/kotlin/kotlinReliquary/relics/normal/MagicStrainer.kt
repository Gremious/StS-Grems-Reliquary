package kotlinReliquary.relics.normal;

import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.vfx.GainPennyEffect
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class MagicStrainer : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractGremRelic.RelicType.NORMAL, AbstractRelic.LandingSound.CLINK) {

    companion object {
        val ID = GremsReliquary.makeID(MagicStrainer::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/placeholder_relic.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/placeholder_relic.png")
        var amount = 6
    }

    override fun onUsePotion() {
        flash()
        val source = AbstractDungeon.player
        CardCrawlGame.sound.play("GOLD_JINGLE")
        AbstractDungeon.player.gainGold(amount)
        for (i in 0 until amount) {
            AbstractDungeon.effectList.add(GainPennyEffect(source, this.currentX, this.currentY, source.drawX, source.drawY, true))
        }
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }
}