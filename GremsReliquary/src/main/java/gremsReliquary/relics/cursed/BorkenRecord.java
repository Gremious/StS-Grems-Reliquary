package gremsReliquary.relics.cursed;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import gremsReliquary.GremsReliquary;
import gremsReliquary.actions.BrokenRecordAction;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;

public class BorkenRecord extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(BorkenRecord.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/BorkenRecord.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/BorkenRecord.png");
    public static int amount = 2;
    private static boolean usedThisCombat = false;
    AbstractCreature p = AbstractDungeon.player;
    private static CardGroup starterCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    
    static {
        for (String c : AbstractDungeon.player.getStartingDeck()) {
            starterCards.addToTop(CardLibrary.getCard(c));
        }
    }
    
    public BorkenRecord() {
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
            
            for (AbstractCard c : starterCards.group) {
                if (c.cardID.equals(card.cardID)) {
                    isStarterCard = true;
                }
            }
            card.exhaust = true;
            if (isStarterCard) {
                AbstractDungeon.actionManager.addToTop(new BrokenRecordAction(card, useCardAction));
                usedThisCombat = true;
            }
        }
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}