package kotlinReliquary.relics.cursed

import basemod.helpers.BaseModCardTags
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.miscRng
import com.megacrit.cardcrawl.helpers.CardLibrary
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect
import gremsReliquary.GremsReliquary
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic


class CursedEgg : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractGremRelic.RelicType.CURSED, AbstractRelic.LandingSound.CLINK) {
    companion object {
        val ID = GremsReliquary.makeID(CursedEgg::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/CursedEgg.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/CursedEgg.png")
        var amount = 2
    }

    override fun onObtainCard(c: AbstractCard?) {
        flash()
    }

    override fun onEnterRestRoom() {
        flash()
        val unupgradedCards = CardGroup(CardGroup.CardGroupType.UNSPECIFIED)

        var strike: AbstractCard? = null
        var defend: AbstractCard? = null

        val roll = miscRng.random(1)

        unupgradedCards.group.addAll(AbstractDungeon.player.masterDeck.group)
        unupgradedCards.group.removeIf { c -> !c.upgraded }


        while (strike == null || defend == null) {
            for (s in AbstractDungeon.player.startingDeck) {
                val sc = CardLibrary.getCard(s)
                if (sc.hasTag(BaseModCardTags.BASIC_STRIKE)) {
                    strike = sc
                }

                if (sc.hasTag(BaseModCardTags.BASIC_DEFEND)) {
                    defend = sc
                }
            }

            if (strike == null || defend == null) {
                val basicCards = CardGroup(CardGroup.CardGroupType.UNSPECIFIED)
                basicCards.group.addAll(CardLibrary.getAllCards())

                for (c in basicCards.group) {
                    if (c.rarity == AbstractCard.CardRarity.BASIC && c.hasTag(BaseModCardTags.BASIC_STRIKE)) {
                        strike = c
                    }
                    if (c.rarity == AbstractCard.CardRarity.BASIC && c.hasTag(BaseModCardTags.BASIC_DEFEND)) {
                        strike = c
                    }
                }
            }
        }


        val randomUnupgraded = unupgradedCards.getRandomCard(miscRng)
        AbstractDungeon.player.masterDeck.removeCard(randomUnupgraded)

        if (roll == 0) {
            AbstractDungeon.effectList.add(ShowCardAndObtainEffect(strike, (Settings.WIDTH / 2).toFloat(), (Settings.HEIGHT / 2).toFloat()))
        } else {
            AbstractDungeon.effectList.add(ShowCardAndObtainEffect(defend, (Settings.WIDTH / 2).toFloat(), (Settings.HEIGHT / 2).toFloat()))
        }
    }

    // Description
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }
}