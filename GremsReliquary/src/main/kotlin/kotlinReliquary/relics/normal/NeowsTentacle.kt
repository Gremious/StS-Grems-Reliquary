package kotlinReliquary.relics.normal

import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.neow.NeowEvent
import com.megacrit.cardcrawl.neow.NeowReward
import com.megacrit.cardcrawl.random.Random
import com.megacrit.cardcrawl.relics.AbstractRelic
import gremsReliquary.GremsReliquary
import gremsReliquary.GremsReliquary.*
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic
import org.apache.logging.log4j.LogManager

class NeowsTentacle : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractGremRelic.RelicType.NORMAL, AbstractRelic.LandingSound.MAGICAL) {
    private var activatedCheck: Boolean = false

    companion object {
        private val logger = LogManager.getLogger(NeowsTentacle::class.java.name)
        val ID = GremsReliquary.makeID(NeowsTentacle::class.java.simpleName)
        private val IMG = TextureLoader.getTexture(makeRelicPath("NeowsTentacle.png"))
        private val OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NeowsTentacle.png"))
        private var triggered: Boolean = false
        private val shouldUpdate: Boolean = false
        internal var roll: Int = 0
        private var neowReward: NeowReward? = null

        fun iLoveConcurrentModificationExceptions() {
            if (!triggered) {
                logger.info("pls")
                NeowEvent.rng = Random(Settings.seed)
                roll = AbstractDungeon.relicRng.random(3)
                neowReward = NeowReward(roll)
                if (debug) logger.info("The roll is: $roll")
                if (debug) logger.info("The neowReward is: " + neowReward!!.optionLabel)
                neowReward!!.activate()
                triggered = true
                AbstractDungeon.player.getRelic(ID).flash()
                (AbstractDungeon.player.getRelic(ID) as NeowsTentacle).setDescriptionAfterLoading(neowReward!!.optionLabel)
            }
        }
    }

    init {
        triggered = false
    }

    override fun update() {
        super.update()
        if (triggered) {
            try {
                logger.info("OOOAAEE")
                val activated = NeowReward::class.java.getDeclaredField("activated")
                activated.isAccessible = true
                activatedCheck = activated.getBoolean(neowReward)
                if (activatedCheck) {
                    logger.info("Neow Reward update - triggered")
                    neowReward!!.update()
                }
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }

    fun setDescriptionAfterLoading(full: String) {
        description = DESCRIPTIONS[1] + full
        tips.clear()
        tips.add(PowerTip(this.name, this.description))
        stopPulse()
        initializeTips()
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }
}
