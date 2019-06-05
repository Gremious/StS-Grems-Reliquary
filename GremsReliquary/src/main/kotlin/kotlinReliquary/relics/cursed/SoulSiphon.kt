package kotlinReliquary.relics.cursed

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.rooms.AbstractRoom
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.powers.LoseEnergyNextTurnPower
import kotlinReliquary.relics.AbstractGremRelic

class SoulSiphon : AbstractGremRelic(ID, IMG, OUTLINE, RelicTier.COMMON, RelicType.CURSED, LandingSound.CLINK), ClickableRelic {


    companion object {
        val ID = GremsReliquary.makeID(SoulSiphon::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/SoulSiphon.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/SoulSiphon.png")
        var amount = 1
        var usedThisTurn = false
    }

    override fun atTurnStart() {
        usedThisTurn = false
        beginLongPulse()
    }

    override fun onRightClick() {
        if (!isObtained || usedThisTurn) {
            return
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            usedThisTurn = true
            stopPulse()
            act(RelicAboveCreatureAction(AbstractDungeon.player, this))
            act(GainEnergyAction(amount))
            act(ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, LoseEnergyNextTurnPower(AbstractDungeon.player, AbstractDungeon.player, amount), amount))
        }
    }

    override fun onVictory() {
        stopPulse()
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }
}