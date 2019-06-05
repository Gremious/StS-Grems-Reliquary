package kotlinReliquary.relics.cursed

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.rooms.AbstractRoom
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic


class UnbalancedScales : AbstractGremRelic(ID, IMG, OUTLINE, RelicTier.UNCOMMON, AbstractGremRelic.RelicType.CURSED, LandingSound.CLINK), ClickableRelic {
     var p: AbstractCreature? = AbstractDungeon.player

    companion object {
        val ID = GremsReliquary.makeID(UnbalancedScales::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/UnbalancedScales.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/UnbalancedScales.png")
        var amount = 2
        private var usedThisCombat = false
    }

    init {
        p = AbstractDungeon.player
    }

    override fun atBattleStart() {
        beginLongPulse()
        usedThisCombat = false
        AbstractDungeon.actionManager.addToTop(RelicAboveCreatureAction(AbstractDungeon.player, this))
        flash()
        act(ApplyPowerAction(p, p, StrengthPower(p, 5), 5))
        act(ApplyPowerAction(p, p, DexterityPower(p, -5), -5))
    }

    override fun onRightClick() {
        if (!usedThisCombat && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToTop(RelicAboveCreatureAction(AbstractDungeon.player, this))
            stopPulse()
            act(ApplyPowerAction(p, p, StrengthPower(p, -10), -10))
            act(ApplyPowerAction(p, p, DexterityPower(p, 10), 10))
            usedThisCombat = true
        }
    }

    override fun onVictory() {
        stopPulse()
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }
}