package gremsReliquary.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremsReliquary.GremsReliquary;
import gremsReliquary.util.TextureLoader;

public class CursedStrengthPower extends AbstractPower {
    public AbstractCreature source;
    
    public static final String POWER_ID = GremsReliquary.makeID(CursedStrengthPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    private static final Texture tex32 = TextureLoader.getTexture("gremsReliquaryResources/images/powers/32/CursedStrengthDexPower.png");
    private static final Texture tex84 = TextureLoader.getTexture("gremsReliquaryResources/images/powers/84/CursedStrengthDexPower.png");
  
    public CursedStrengthPower(AbstractCreature owner, AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        
        if (this.amount <= -999) {
            this.amount = -999;
        }
        
        this.updateDescription();
    
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.canGoNegative = true;
    }
    
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage + (float) this.amount : damage;
    }
    
    @Override
    public float modifyBlock(float blockAmount) {
        return (blockAmount += (float)this.amount) < 0.0F ? 0.0F : blockAmount;
    }
    
    @Override
    public void onRemove() {
        if (amount > 1) {
            // Add a copy, only one will be removed
            owner.powers.add(0, this);
            // Cancel the removal text effect
            AbstractDungeon.effectList.remove(AbstractDungeon.effectList.size() - 1);
        }
    }
    
    @Override
    public void updateDescription() {
        
        if (amount > 0) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
            type = PowerType.BUFF;
        } else {
            int tmp = -amount;
            description = DESCRIPTIONS[1] + tmp + DESCRIPTIONS[2];
            type = PowerType.DEBUFF;
        }
        
        description += DESCRIPTIONS[3];
    }
}
