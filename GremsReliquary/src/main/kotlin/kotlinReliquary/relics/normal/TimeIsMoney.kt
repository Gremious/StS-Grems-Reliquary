package kotlinReliquary.relics.normal

import com.megacrit.cardcrawl.core.CardCrawlGame.sound
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.vfx.GainPennyEffect
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class TimeIsMoney : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.COMMON, AbstractGremRelic.RelicType.NORMAL, AbstractRelic.LandingSound.CLINK) {

    companion object {
        val ID = GremsReliquary.makeID(TimeIsMoney::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/TimeIsMoney.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/TimeIsMoney.png")
        var amount = 2
    }

    override fun atTurnStart() {
        val source = AbstractDungeon.player
        flash()
        sound.play("GOLD_JINGLE")
        AbstractDungeon.player.gainGold(amount)
        for (i in 0 until amount) {
            AbstractDungeon.effectList.add(GainPennyEffect(source, this.currentX, this.currentY, source.drawX, source.drawY, true))
        }
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }
}