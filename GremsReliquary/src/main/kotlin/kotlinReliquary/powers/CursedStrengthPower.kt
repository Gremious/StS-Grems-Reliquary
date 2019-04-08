package kotlinReliquary.powers

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader

class CursedStrengthPower(owner: AbstractCreature, var source: AbstractCreature, amount: Int) : AbstractPower() {
    companion object {
        val POWER_ID = GremsReliquary.makeID(CursedStrengthPower::class.java.simpleName)
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS

        private val tex32 = TextureLoader.getTexture("gremsReliquaryResources/images/powers/32/CursedStrengthDexPower.png")
        private val tex84 = TextureLoader.getTexture("gremsReliquaryResources/images/powers/84/CursedStrengthDexPower.png")
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

    override fun atDamageGive(damage: Float, type: DamageInfo.DamageType?): Float {
        return if (type == DamageInfo.DamageType.NORMAL) damage + this.amount.toFloat() else damage
    }

    override fun modifyBlock(blockAmount: Float): Float {
        val bAmount = blockAmount + this.amount.toFloat()
        return if ((bAmount) < 0.0f) 0.0f else blockAmount
    }

    override fun onRemove() {
        if (amount > 1) {
            // Add a copy, only one will be removed
            owner.powers.add(0, this)
            // Cancel the removal text effect
            AbstractDungeon.effectList.removeAt(AbstractDungeon.effectList.size - 1)
        }
    }

    override fun updateDescription() {

        if (amount > 0) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2]
            type = AbstractPower.PowerType.BUFF
        } else {
            val tmp = -amount
            description = DESCRIPTIONS[1] + tmp + DESCRIPTIONS[2]
            type = AbstractPower.PowerType.DEBUFF
        }

        description += DESCRIPTIONS[3]
    }
}
