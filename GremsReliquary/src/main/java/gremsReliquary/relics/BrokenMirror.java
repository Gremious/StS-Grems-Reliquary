package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import gremsReliquary.GremsReliquary;

public class BrokenMirror extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(BrokenMirror.class.getSimpleName());
    public static final String IMG = GremsReliquary.getModID() + "Resources/images/relics/placeholder_relic.png";
    public static final String OUTLINE = GremsReliquary.getModID() + "Resources/images/relics/outline/placeholder_relic.png";
    public static int amount = 2;
    
    private boolean usedThisTurn = false;
    
    public BrokenMirror() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);
    }
    
    public void onEquip() {
    
    }
    
    public void atBattleStart() {
    
    }
    
    public void atTurnStart() {
        
    }
    
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
    
    }
    
    public void onPlayerEndTurn() {
    
    }
    
    public void onVictory() {
    
    }
    
    public void onUnequip() {
    
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}