package gremsReliquary.relics.cursed;

import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import gremsReliquary.GremsReliquary;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.miscRng;

public class CursedEgg extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(CursedEgg.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/CursedEgg.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/CursedEgg.png");
    public static int amount = 2;
    
    AbstractCreature p = AbstractDungeon.player;
    
    public CursedEgg() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, RelicType.CURSED, LandingSound.CLINK);
    }
    
    @Override
    public void onObtainCard(AbstractCard c) {
        flash();
    }
    
    @Override
    public void onEnterRestRoom() {
        flash();
        CardGroup unupgradedCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        
        AbstractCard strike = null;
        AbstractCard defend = null;
        
        int roll = miscRng.random(1);
        
        unupgradedCards.group.addAll(AbstractDungeon.player.masterDeck.group);
        unupgradedCards.group.removeIf(c -> !c.upgraded);
        
        
        while (strike == null || defend == null) {
            for (String s : AbstractDungeon.player.getStartingDeck()) {
                AbstractCard sc = CardLibrary.getCard(s);
                if (sc.hasTag(BaseModCardTags.BASIC_STRIKE)) {
                    strike = sc;
                }
                
                if (sc.hasTag(BaseModCardTags.BASIC_DEFEND)) {
                    defend = sc;
                }
            }
            
            if (strike == null || defend == null) {
                CardGroup basicCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                basicCards.group.addAll(CardLibrary.getAllCards());
                
                for (AbstractCard c : basicCards.group) {
                    if (c.rarity.equals(AbstractCard.CardRarity.BASIC) && c.hasTag(BaseModCardTags.BASIC_STRIKE)) {
                        strike = c;
                    }
                    if (c.rarity.equals(AbstractCard.CardRarity.BASIC) && c.hasTag(BaseModCardTags.BASIC_DEFEND)) {
                        strike = c;
                    }
                }
            }
        }
        
        
        AbstractCard randomUnupgraded = unupgradedCards.getRandomCard(miscRng);
        AbstractDungeon.player.masterDeck.removeCard(randomUnupgraded);
        
        if (roll == 0) {
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(strike, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
        } else {
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(defend, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
        }
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}