package gremsReliquary.relics.cursed;

import basemod.BaseMod;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gremsReliquary.GremsReliquary;
import gremsReliquary.patches.relics.BottledPlaceholderField;
import gremsReliquary.util.TextureLoader;
import kotlinReliquary.relics.AbstractGremRelic;

import java.util.Iterator;
import java.util.function.Predicate;

public class CursedBottle extends AbstractGremRelic implements CustomBottleRelic, CustomSavable<Integer> {
    
    // BasemodWiki Says: "When you need to store a value on a card or relic between runs that isn't a relic's counter value
    // or a card's misc value, you use a custom savable to save and load it between runs."
    
    private static AbstractCard card;  // The field value we wish to save in this case is the card that's going to be in our bottle.
    private boolean cardSelected = true; // A boolean to indicate whether or not we selected a card for bottling.
    // (It's set to false on Equip)
    
    // ID, images, text.
    public static final String ID = GremsReliquary.makeID(CursedBottle.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/CursedBottle.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/CursedBottle.png");
    
    public CursedBottle() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, RelicType.CURSED, LandingSound.CLINK);
    }
    
    // Now, for making Bottled cards we need a small patch - our own custom SpireField
    // I've included that already in patches.kotlin.relics.BottledPlaceholderField
    // The basemod wiki I linked above has comments about onSave and onLoad
    
    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottledPlaceholderField.inCursedBottle::get;
    }
    
    @Override
    public Integer onSave() {
        if (card != null) {
            return AbstractDungeon.player.masterDeck.group.indexOf(card);
        } else {
            return -1;
        }
    }
    
    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                BottledPlaceholderField.inCursedBottle.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }
    
    @Override
    public void onEquip() { // 1. When we acquire the relic
        cardSelected = false; // 2. Tell the relic that we haven't bottled the card yet
        if (AbstractDungeon.isScreenUp) { // 3. If the map is open - hide it.
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        // 4. Set the room to INCOMPLETE - don't allow us to use the map, etc.
        CardGroup group = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck); // 5. Get a card group of all currently unbottled cards
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[3] + name + DESCRIPTIONS[2], false, false, false, false);
        // 6. Open the grid selection screen with the cards from the CardGroup we specified above. The description reads "Select a card to bottle for" + (relic name) + "."
    }
    
    @Override
    public void onUnequip() { // 1. On unequip
        if (card != null) { // If the bottled card exists (prevents the game from crashing if we removed the bottled card from our deck for example.)
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card); // 2. Get the card
            if (cardInDeck != null) {
                BottledPlaceholderField.inCursedBottle.set(cardInDeck, false); // In our SpireField - set the card to no longer be bottled. (Unbottle it)
            }
        }
    }
    
    @Override
    public void update() {
        super.update(); //Do all of the original update() method in AbstractRelic
        
        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            // If the card hasn't been bottled yet and we have cards selected in the gridSelectScreen (from onEquip)
            cardSelected = true; //Set the cardSelected boolean to be true - we're about to bottle the card.
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0); // The custom Savable "card" is going to equal
            // The card from the selection screen (it's only 1, so it's at index 0)
            BottledPlaceholderField.inCursedBottle.set(card, true); // Use our custom spire field to set that card to be bottled.
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.INCOMPLETE) {
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE; // The room phase can now be set to complete (From INCOMPLETE in onEquip)
            AbstractDungeon.gridSelectScreen.selectedCards.clear(); // Always clear your grid screen after using it.
            setDescriptionAfterLoading(); // Set the description to reflect the bottled card (the method is at the bottom of this file)
        }
    }
    
    @Override
    public void atTurnStart() {
        boolean fullHandDialog = false;
        for (Iterator<AbstractCard> itDraw = AbstractDungeon.player.drawPile.group.iterator(); itDraw.hasNext(); ) {
            AbstractCard card = itDraw.next();
            if (BottledPlaceholderField.inCursedBottle.get(card)) {
                this.flash();
                itDraw.remove();
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    if (AutoplayField.autoplay.get(card)) {
                        AbstractDungeon.actionManager.addToBottom(new AutoplayCardAction(card, AbstractDungeon.player.hand));
                    }
                    card.triggerWhenDrawn();
                    AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        r.onCardDraw(card);
                    }
                } else {
                    if (!fullHandDialog) {
                        AbstractDungeon.player.createHandIsFullDialog();
                        fullHandDialog = true;
                    }
                    AbstractDungeon.player.drawPile.moveToDiscardPile(card);
                }
            }
        }
        
        for (Iterator<AbstractCard> itDis = AbstractDungeon.player.discardPile.group.iterator(); itDis.hasNext(); ) {
            AbstractCard card = itDis.next();
            if (BottledPlaceholderField.inCursedBottle.get(card)) {
                this.flash();
                itDis.remove();
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    if (AutoplayField.autoplay.get(card)) {
                        AbstractDungeon.actionManager.addToBottom(new AutoplayCardAction(card, AbstractDungeon.player.hand));
                    }
                    card.triggerWhenDrawn();
                    AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        r.onCardDraw(card);
                    }
                } else {
                    if (!fullHandDialog) {
                        AbstractDungeon.player.createHandIsFullDialog();
                        fullHandDialog = true;
                    }
                    AbstractDungeon.player.drawPile.moveToDiscardPile(card);
                }
            }
        }
        
        act(new MakeTempCardInDrawPileAction(new Decay(), 1, true, true));
    }
    
    // Change description after relic is already loaded to reflect the bottled card.
    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[1] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    // Standard description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
