package kotlinReliquary.powers

import basemod.interfaces.CloneablePowerInterface
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader

class LoseEnergyNextTurnPower(owner: AbstractCreature, var source: AbstractCreature, amount: Int) : AbstractPower(), CloneablePowerInterface {

    companion object {
        val POWER_ID = GremsReliquary.makeID(LoseEnergyNextTurnPower::class.java.simpleName)
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS

        private val tex32 = TextureLoader.getTexture("gremsReliquaryResources/images/powers/32/LoseEnergyNextTurnPower.png")
        private val tex84 = TextureLoader.getTexture("gremsReliquaryResources/images/powers/84/LoseEnergyNextTurnPower.png")
    }

    init {
        name = NAME
        ID = POWER_ID

        this.amount = amount
        this.owner = owner
        this.updateDescription()

        this.region48 = TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32)
        this.region128 = TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84)
        this.canGoNegative = true
    }

    override fun atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(LoseEnergyAction(amount))
    }

    override fun makeCopy(): AbstractPower {
        return LoseEnergyNextTurnPower(owner, source, amount)
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2]
    }
}
