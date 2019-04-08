package kotlinReliquary.relics.cursed

import basemod.BaseMod
import basemod.abstracts.CustomBottleRelic
import basemod.abstracts.CustomSavable
import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.curses.Decay
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rooms.AbstractRoom
import gremsReliquary.GremsReliquary
import gremsReliquary.patches.relics.BottledPlaceholderField
import gremsReliquary.util.TextureLoader
import kotlinReliquary.relics.AbstractGremRelic
import java.util.function.Predicate

class CursedBottle : AbstractGremRelic(ID, IMG, OUTLINE, AbstractRelic.RelicTier.RARE, AbstractGremRelic.RelicType.CURSED, AbstractRelic.LandingSound.CLINK), CustomBottleRelic, CustomSavable<Int> {
    private var cardSelected = true

    companion object {
        private var card: AbstractCard? = null
        val ID = GremsReliquary.makeID(CursedBottle::class.java.simpleName)
        val IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/CursedBottle.png")
        val OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/CursedBottle.png")
    }

    override fun isOnCard(): Predicate<AbstractCard> {
        return Predicate { BottledPlaceholderField.inCursedBottle.get(it) }
    }

    override fun onSave(): Int? {
        return if (card != null) {
            AbstractDungeon.player.masterDeck.group.indexOf(card)
        } else {
            -1
        }
    }

    override fun onLoad(cardIndex: Int?) {
        if (cardIndex == null) {
            return
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size) {
            card = AbstractDungeon.player.masterDeck.group[cardIndex]
            if (card != null) {
                BottledPlaceholderField.inCursedBottle.set(card, true)
                setDescriptionAfterLoading()
            }
        }
    }

    override fun onEquip() { // 1. When we acquire the relic
        cardSelected = false // 2. Tell the relic that we haven't bottled the card yet
        if (AbstractDungeon.isScreenUp) { // 3. If the map is open - hide it.
            AbstractDungeon.dynamicBanner.hide()
            AbstractDungeon.overlayMenu.cancelButton.hide()
            AbstractDungeon.previousScreen = AbstractDungeon.screen
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE
        // 4. Set the room to INCOMPLETE - don't allow us to use the map, etc.
        val group = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck) // 5. Get a card group of all currently unbottled cards
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[3] + name + DESCRIPTIONS[2], false, false, false, false)
        // 6. Open the grid selection screen with the cards from the CardGroup we specified above. The description reads "Select a card to bottle for" + (relic name) + "."
    }

    override fun onUnequip() { // 1. On unequip
        if (card != null) { // If the bottled card exists (prevents the game from crashing if we removed the bottled card from our deck for example.)
            val cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card) // 2. Get the card
            if (cardInDeck != null) {
                BottledPlaceholderField.inCursedBottle.set(cardInDeck, false) // In our SpireField - set the card to no longer be bottled. (Unbottle it)
            }
        }
    }

    override fun update() {
        super.update() //Do all of the original update() method in AbstractRelic

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            // If the card hasn't been bottled yet and we have cards selected in the gridSelectScreen (from onEquip)
            cardSelected = true //Set the cardSelected boolean to be true - we're about to bottle the card.
            card = AbstractDungeon.gridSelectScreen.selectedCards[0] // The custom Savable "card" is going to equal
            // The card from the selection screen (it's only 1, so it's at index 0)
            BottledPlaceholderField.inCursedBottle.set(card, true) // Use our custom spire field to set that card to be bottled.
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.INCOMPLETE) {
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE // The room phase can now be set to complete (From INCOMPLETE in onEquip)
            AbstractDungeon.gridSelectScreen.selectedCards.clear() // Always clear your grid screen after using it.
            setDescriptionAfterLoading() // Set the description to reflect the bottled card (the method is at the bottom of this file)
        }
    }

    override fun atTurnStart() {
        var fullHandDialog = false
        val itDraw = AbstractDungeon.player.drawPile.group.iterator()
        while (itDraw.hasNext()) {
            val card = itDraw.next()
            if (BottledPlaceholderField.inCursedBottle.get(card)) {
                this.flash()
                itDraw.remove()
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    if (AutoplayField.autoplay.get(card)) {
                        AbstractDungeon.actionManager.addToBottom(AutoplayCardAction(card, AbstractDungeon.player.hand))
                    }
                    card.triggerWhenDrawn()
                    AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile)
                    for (r in AbstractDungeon.player.relics) {
                        r.onCardDraw(card)
                    }
                } else {
                    if (!fullHandDialog) {
                        AbstractDungeon.player.createHandIsFullDialog()
                        fullHandDialog = true
                    }
                    AbstractDungeon.player.drawPile.moveToDiscardPile(card)
                }
            }
        }

        val itDis = AbstractDungeon.player.discardPile.group.iterator()
        while (itDis.hasNext()) {
            val card = itDis.next()
            if (BottledPlaceholderField.inCursedBottle.get(card)) {
                this.flash()
                itDis.remove()
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    if (AutoplayField.autoplay.get(card)) {
                        AbstractDungeon.actionManager.addToBottom(AutoplayCardAction(card, AbstractDungeon.player.hand))
                    }
                    card.triggerWhenDrawn()
                    AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile)
                    for (r in AbstractDungeon.player.relics) {
                        r.onCardDraw(card)
                    }
                } else {
                    if (!fullHandDialog) {
                        AbstractDungeon.player.createHandIsFullDialog()
                        fullHandDialog = true
                    }
                    AbstractDungeon.player.drawPile.moveToDiscardPile(card)
                }
            }
        }

        act(MakeTempCardInDrawPileAction(Decay(), 1, true, true))
    }

    // Change description after relic is already loaded to reflect the bottled card.
    fun setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[1] + FontHelper.colorString(card!!.name, "y") + DESCRIPTIONS[2]
        this.tips.clear()
        this.tips.add(PowerTip(this.name, this.description))
        this.initializeTips()
    }

    // Standard description
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }
}
