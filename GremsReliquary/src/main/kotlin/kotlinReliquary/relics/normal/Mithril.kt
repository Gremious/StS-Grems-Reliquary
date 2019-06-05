package kotlinReliquary.relics.normal

import basemod.abstracts.CustomSavable
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.relics.Orichalcum
import gremsReliquary.GremsReliquary
import gremsReliquary.patches.OrichalcumMythrilSynergyPatch
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class Mithril : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractGremRelic.RelicType.NORMAL, AbstractRelic.LandingSound.CLINK),
        OnLoseTempHpRelic,
        CustomSavable<Boolean> {

    var trigger = false

    companion object {
        val ID = GremsReliquary.makeID(Mithril::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/Mithril.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/Mithril.png")
        private val TEMP_HP = 4
        private val ORI_THORNS = 3
        var spireFieldSave: Boolean = false
        val p: AbstractCreature = AbstractDungeon.player
    }

    init {
        tips.clear()
        tips.add(PowerTip(name, description))
        tips.add(PowerTip(DESCRIPTIONS[2], DESCRIPTIONS[3] + Orichalcum().name + DESCRIPTIONS[4] + ORI_THORNS + DESCRIPTIONS[5]))
        this.initializeTips()
    }

    override fun onPlayerEndTurn() {
        if (TempHPField.tempHp.get(p) <= 0 || trigger) {
            trigger = false
            this.flash()
            this.stopPulse()
            AbstractDungeon.actionManager.addToTop(AddTemporaryHPAction(p, p, TEMP_HP))
            AbstractDungeon.actionManager.addToTop(RelicAboveCreatureAction(AbstractDungeon.player, this))
        }
    }

    override fun atTurnStart() {
        trigger = false
        if (TempHPField.tempHp.get(p) <= 0) {
            beginLongPulse()
        }
    }

    override fun onVictory() {
        this.stopPulse()
    }

    override fun onLoseTempHp(damageInfo: DamageInfo, damageAmount: Int): Int {
        if (TempHPField.tempHp.get(p) >= 0) {
            stopPulse()
        }
        return damageAmount
    }

    @Suppress("unused")
    fun changeOri() {
        if (AbstractDungeon.player.hasRelic(Orichalcum.ID)) {
            val o = AbstractDungeon.player.getRelic(Orichalcum.ID)
            CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.1f)
            o.flavorText = DESCRIPTIONS[6]
            o.tips.clear()
            o.description = DESCRIPTIONS[7] + ORI_THORNS + DESCRIPTIONS[8]
            o.tips.add(PowerTip(DESCRIPTIONS[9], DESCRIPTIONS[7] + ORI_THORNS + DESCRIPTIONS[8]))

            OrichalcumMythrilSynergyPatch.OriBrokenField.broken.set(o, true)
        }
    }

    override fun onSave(): Boolean? {
        spireFieldSave = OrichalcumMythrilSynergyPatch.OriBrokenField.broken.get(AbstractDungeon.player.getRelic(Orichalcum.ID))
        return spireFieldSave
    }

    override fun onLoad(brokenField: Boolean?) {
        OrichalcumMythrilSynergyPatch.OriBrokenField.broken.set(AbstractDungeon.player.getRelic(ID), brokenField)
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + TEMP_HP + DESCRIPTIONS[1]
    }

}