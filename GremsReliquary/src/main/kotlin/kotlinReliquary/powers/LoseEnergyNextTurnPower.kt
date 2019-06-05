package kotlinReliquary.powers

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader

class LoseEnergyNextTurnPower(amount: Int) : AbstractPower() {
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

        this.owner = owner
        this.amount = amount
        if (this.amount >= 999) {
            this.amount = 999
        }

        if (this.amount <= -999) {
            this.amount = -999
        }

        this.updateDescription()

        this.region48 = TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32)
        this.region128 = TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84)
        this.canGoNegative = true
    }

    override fun atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(LoseEnergyAction(amount))
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2]
    }
}
