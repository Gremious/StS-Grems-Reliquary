package gremsReliquary.relics.cursed;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import gremsReliquary.GremsReliquary;
import gremsReliquary.actions.BrokenRecordAction;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;

public class BrokenRecord extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(BrokenRecord.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/BrokenRecord.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/BrokenRecord.png");
    public static int amount = 2;
    private static boolean usedThisCombat = false;
    AbstractPlayer p = AbstractDungeon.player;
    private CardGroup starterCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    
    public BrokenRecord() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, RelicType.CURSED, LandingSound.CLINK);
    }
    
    @Override
    public void atBattleStart() {
        usedThisCombat = false;
    }
    
    @Override
    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (!usedThisCombat) {
            flash();
            boolean isStarterCard = false;
            for (String c : AbstractDungeon.player.getStartingDeck()) {
                starterCards.addToTop(CardLibrary.getCard(c));
            }
            
            for (AbstractCard c : starterCards.group) {
                if (c.cardID.equals(card.cardID)) {
                    isStarterCard = true;
                }
            }
            useCardAction.exhaustCard = true;
            if (!isStarterCard) {
                AbstractDungeon.actionManager.addToTop(new BrokenRecordAction(card, useCardAction));
                usedThisCombat = true;
            }
        }
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}