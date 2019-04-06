package relics

import basemod.abstracts.CustomRelic
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rewards.RewardItem
import gremsReliquary.effects.visual.CursedRelicBorderGlow
import gremsReliquary.effects.visual.CursedRelicSparklies
import gremsReliquary.rewards.LinkedRewardItem
import org.apache.logging.log4j.LogManager
import java.lang.reflect.Field
import java.util.*

class AbstractGremRelic(id: String,
                        texture: Texture,
                        private val outline: Texture,
                        tier: AbstractRelic.RelicTier,
                        private val type: RelicType,
                        sfx: AbstractRelic.LandingSound) : CustomRelic(id, texture, tier, sfx) {


    private val glowList = ArrayList<CursedRelicBorderGlow>()
    private val sparkleList = ArrayList<CursedRelicSparklies>()
    private var glowTimer = 0.0f
    private var sparkleTimer = 0.0f
    private val pulseCount = 0
    private var offsetXField: Field? = null
    private var rotationField: Field? = null

    init {
        try {
            offsetXField = AbstractRelic::class.java.getDeclaredField("offsetX")
            rotationField = AbstractRelic::class.java.getDeclaredField("rotation")

            offsetXField!!.isAccessible = true
            rotationField!!.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        fun curseTrigger() {
            if (type == RelicType.CURSED) {
                val relicRewards = ArrayList<RewardItem>()
                for (reward in AbstractDungeon.getCurrRoom().rewards) {
                    if (reward.type == RewardItem.RewardType.RELIC && reward.relicLink == null) {
                        relicRewards.add(reward)
                    }
                }

                for (reward in relicRewards) {
                    val tier = reward.relic.tier
                    if (tier != AbstractRelic.RelicTier.SPECIAL && tier != AbstractRelic.RelicTier.DEPRECATED && tier != AbstractRelic.RelicTier.STARTER) {
                        var newRelic: AbstractRelic? = AbstractDungeon.returnRandomRelic(tier)
                        if (newRelic is AbstractGremRelic) {
                            do {
                                newRelic = AbstractDungeon.returnRandomRelic(tier)
                            } while ((newRelic as AbstractGremRelic).type != RelicType.CURSED || newRelic.tier != tier)
                        }

                        if (newRelic != null) {
                            val replaceReward = LinkedRewardItem(reward)
                            val newReward = LinkedRewardItem(replaceReward, newRelic)
                            val indexOf = AbstractDungeon.getCurrRoom().rewards.indexOf(reward)
                            // Insert after existing reward
                            AbstractDungeon.getCurrRoom().rewards.add(indexOf + 1, newReward)
                            // Replace original
                            AbstractDungeon.getCurrRoom().rewards[indexOf] = replaceReward
                        }
                    }
                }
            }
        }
    }


    //==
    companion object {

        private val logger = LogManager.getLogger(AbstractGremRelic::class.java.name)

        val uiStrings = CardCrawlGame.languagePack.getUIString("gremsReliquary:RelicsUI")
        val UI_STRINGS = uiStrings.TEXT

        fun act(action: AbstractGameAction) {
            AbstractDungeon.actionManager.addToBottom(action)
        }
    }

    enum class RelicType private constructor() {
        CURSED,
        NORMAL
    }

    init {
        cursedDescription()
        tips.clear()
        tips.add(PowerTip(name, description))
        initializeTips()
    }

    private fun renderGlow(sb: SpriteBatch) {
        if (type == RelicType.CURSED) {
            if (!Settings.hideRelics) {
                //   if (debug) logger.info("render Glow log is on");
                sb.setBlendFunction(770, 1)
                for (e in glowList) {
                    //       if (debug) logger.info("Triggered glow");
                    e.render(sb)
                }
                sb.setBlendFunction(770, 771)
            }
        }
    }

    private fun updateGlow() {
        if (type == RelicType.CURSED) {
            glowTimer -= Gdx.graphics.deltaTime

            var offsetX = 0.0f
            var rotation = 0.0f
            try {
                offsetX = offsetXField!!.getFloat(offsetXField)
                rotation = offsetXField!!.getFloat(offsetXField)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            if (glowTimer < 0.0f) {
                glowList.add(CursedRelicBorderGlow(this, outline, offsetX, rotation))
                glowTimer = 2.5f
            }

            /*
            if (glowTimer < 0.0F) {
                if (pulseCount < 3) {
                    glowList.add(new CursedRelicBorderGlow(this, outline, offsetX, rotation));
                    pulseCount++;
                    glowTimer = 0.7F;
                } else {
                    glowList.add(new CursedRelicBorderGlow(this, outline, offsetX, rotation));
                    pulseCount = 0;
                    glowTimer = 1.5F;
                }
            }
            */

            val i = glowList.iterator()
            while (i.hasNext()) {
                val e = i.next()
                //                if (debug) logger.info("gonna update " + e.hashCode());
                e.update()
                if (e.isDone) {
                    //                    if (debug) logger.info(e.hashCode() + " is totally done we gonna remove");
                    e.dispose()
                    i.remove()
                }
            }
        }
    }

    private fun renderSparkles(sb: SpriteBatch) {
        if (!Settings.hideRelics) {
            //   if (debug) logger.info("render sparkle log is on");
            sb.setBlendFunction(770, 1)
            for (e in sparkleList) {
                //        if (debug) logger.info("Triggered sparkle");
                e.render(sb)
            }
            sb.setBlendFunction(770, 771)
        }
    }

    private fun updateSparkle() {
        if (type == RelicType.CURSED) {
            sparkleTimer -= Gdx.graphics.deltaTime
            var offsetX = 0.0f
            var rotation = 0.0f

            try {
                offsetX = offsetXField!!.getFloat(offsetXField)
                rotation = offsetXField!!.getFloat(offsetXField)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            if (sparkleTimer < 0.0f) {
                sparkleList.add(CursedRelicSparklies(this, offsetX, rotation))
                sparkleTimer = 2.0f
            }

            val i = sparkleList.iterator()
            while (i.hasNext()) {
                val e = i.next()
                e.update()
                if (e.isDone) {
                    e.dispose()
                    i.remove()
                }
            }
        }
    }

    override fun renderInTopPanel(sb: SpriteBatch) {
        updateGlow()
        renderGlow(sb)
        //        updateSparkle();
        //        renderSparkles(sb);
        super.renderInTopPanel(sb)
    }

    override fun render(sb: SpriteBatch) {
        updateGlow()
        renderGlow(sb)
        //        renderSparkles(sb);
        //        updateSparkle();
        super.render(sb)
    }

    override fun render(sb: SpriteBatch, renderAmount: Boolean, outlineColor: Color) {
        updateGlow()
        renderGlow(sb)
        //        renderSparkles(sb);
        //        updateSparkle();
        super.render(sb, renderAmount, outlineColor)
    }

    override fun renderWithoutAmount(sb: SpriteBatch, c: Color) {
        updateGlow()
        renderGlow(sb)
        //        renderSparkles(sb);
        //        updateSparkle();
        super.renderWithoutAmount(sb, c)
    }

    //==

    private fun cursedDescription() {
        if (type == RelicType.CURSED) {
            this.description = UI_STRINGS[0] + description
        }
    }



}
