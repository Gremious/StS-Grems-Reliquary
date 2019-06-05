package kotlinReliquary.relics.cursed

import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rooms.AbstractRoom
import com.megacrit.cardcrawl.rooms.MonsterRoomElite
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic


class Curseberry : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.COMMON, AbstractGremRelic.RelicType.CURSED, AbstractRelic.LandingSound.FLAT) {
    companion object {
        val ID = GremsReliquary.makeID(Curseberry::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/Curseberry.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/Curseberry.png")
        var amount = 15
        var loseAmount = 6
        val p: AbstractCreature = AbstractDungeon.player
    }

    override fun onEquip() {
        flash()
        p.increaseMaxHp(amount, true)
    }

    override fun onEnterRoom(room: AbstractRoom?) {
        if (room is MonsterRoomElite) {
            flash()
            p.decreaseMaxHealth(loseAmount)
        }
    }

    // Description
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + loseAmount + DESCRIPTIONS[2]
    }

}