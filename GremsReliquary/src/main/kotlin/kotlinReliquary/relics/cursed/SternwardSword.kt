package kotlinReliquary.relics.cursed

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import gremsReliquary.GremsReliquary
import gremsReliquary.powers.CursedStrengthPower
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class SternwardSword : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.RARE, AbstractGremRelic.RelicType.CURSED, AbstractRelic.LandingSound.CLINK) {
    companion object {
        val ID = GremsReliquary.makeID(SternwardSword::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/SternwardSword.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/SternwardSword.png")
        var amount = 2
    }

    override fun onEquip() {

    }

    override fun atBattleStart() {
        AbstractDungeon.actionManager.addToTop(RelicAboveCreatureAction(p, this))
        act(ApplyPowerAction(p, p, CursedStrengthPower(p, p, -3), -3))
    }

    // Description
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }
}