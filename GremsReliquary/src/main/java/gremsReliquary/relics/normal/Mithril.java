/*
package gremsReliquary.relics.normal;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Orichalcum;
import gremsReliquary.GremsReliquary;
import gremsReliquary.patches.OrichalcumMythrilSynergyPatch;
import gremsReliquary.util.TextureLoader;
import kotlinReliquary.relics.AbstractGremRelic;

public class Mithril extends AbstractGremRelic implements OnLoseTempHpRelic, CustomSavable<Boolean> {
    public static final String ID = GremsReliquary.makeID(Mithril.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/Mithril.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/Mithril.png");
    
    AbstractCreature p = AbstractDungeon.player;
    public static boolean spireFieldSave;
    private static final int TEMP_HP = 4;
    private static final int ORI_THORNS = 3;
    public boolean trigger = false;
    
    public Mithril() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, RelicType.NORMAL, LandingSound.CLINK);
        
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(DESCRIPTIONS[2], DESCRIPTIONS[3] + new Orichalcum().name + DESCRIPTIONS[4] + ORI_THORNS + DESCRIPTIONS[5]));
    }
    
    @Override
    public void onPlayerEndTurn() {
        if (TempHPField.tempHp.get(p) <= 0 || trigger) {
            trigger = false;
            this.flash();
            this.stopPulse();
            AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(p, p, TEMP_HP));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
    
    @Override
    public void atTurnStart() {
        trigger = false;
        if (TempHPField.tempHp.get(p) <= 0) {
            beginLongPulse();
        }
    }
    
    @Override
    public void onTrigger() {
    
    }
    
    @Override
    public void onVictory() {
        this.stopPulse();
    }
    
    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int damageAmount) {
        if (TempHPField.tempHp.get(p) >= 0) {
            stopPulse();
        }
        
        return damageAmount;
    }
    
    @SuppressWarnings("unused")
    public void changeOri() {
        if (AbstractDungeon.player.hasRelic(Orichalcum.ID)) {
            AbstractRelic o = AbstractDungeon.player.getRelic(Orichalcum.ID);
            CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.1F);
            o.flavorText = DESCRIPTIONS[6];
            o.tips.clear();
            o.description = DESCRIPTIONS[7] + ORI_THORNS + DESCRIPTIONS[8];
            o.tips.add(new PowerTip(DESCRIPTIONS[9], DESCRIPTIONS[7] + ORI_THORNS + DESCRIPTIONS[8]));
            
            OrichalcumMythrilSynergyPatch.OriBrokenField.broken.set(o, true);
        }
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + TEMP_HP + DESCRIPTIONS[1];
    }
    
    @Override
    public Boolean onSave() {
        spireFieldSave = OrichalcumMythrilSynergyPatch.OriBrokenField.broken.get(AbstractDungeon.player.getRelic(Orichalcum.ID));
        return spireFieldSave;
    }
    
    @Override
    public void onLoad(Boolean brokenField) {
        
        OrichalcumMythrilSynergyPatch.OriBrokenField.broken.set(AbstractDungeon.player.getRelic(Mithril.ID), brokenField);
    }
}*/
