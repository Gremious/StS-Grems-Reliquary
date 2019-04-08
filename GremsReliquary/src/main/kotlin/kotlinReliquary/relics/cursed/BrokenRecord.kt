package kotlinReliquary.relics.cursed

import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.CardLibrary
import com.megacrit.cardcrawl.relics.AbstractRelic
import gremsReliquary.GremsReliquary
import kotlinReliquary.actions.BrokenRecordAction
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic

class BrokenRecord : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractGremRelic.RelicType.CURSED, AbstractRelic.LandingSound.FLAT) {

    private val starterCards = CardGroup(CardGroup.CardGroupType.UNSPECIFIED)

    companion object {
        val ID = GremsReliquary.makeID(BrokenRecord::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/BrokenRecord.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/BrokenRecord.png")
        var amount = 2
        private var usedThisCombat = false
    }

    override fun atBattleStart() {
        usedThisCombat = false
    }

    override fun onUseCard(card: AbstractCard?, useCardAction: UseCardAction?) {
        if (!usedThisCombat) {
            flash()
            var isStarterCard = false
            for (c in AbstractDungeon.player.startingDeck) {
                starterCards.addToTop(CardLibrary.getCard(c))
            }

            for (c in starterCards.group) {
                if (c.cardID == card!!.cardID) {
                    isStarterCard = true
                }
            }
            useCardAction!!.exhaustCard = true
            if (!isStarterCard) {
                AbstractDungeon.actionManager.addToTop(BrokenRecordAction(card, useCardAction))
                usedThisCombat = true
            }
        }
    }

    // Description
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}