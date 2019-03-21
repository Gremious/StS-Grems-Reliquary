package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import gremsReliquary.effects.visual.CursedRelicBorderGlow;
import gremsReliquary.rewards.LinkedRewardItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbstractGremRelic extends CustomRelic {
    
    public static RelicType type;
    private Texture outline;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("gremsReliquary:RelicsUI");
    public static final String[] UI_STRINGS = uiStrings.TEXT;
    private ArrayList<CursedRelicBorderGlow> glowList = new ArrayList<>();
    private float glowTimer = 0.0F;
    public boolean isGlowing = false;
    
    public AbstractGremRelic(String id, Texture texture, Texture outline, RelicTier tier, RelicType type, LandingSound sfx) {
        super(id, texture, tier, sfx);
        this.type = type;
        cursedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        this.outline = outline;
    }
    
    public static void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    public static void curseTrigger() {
        if (type == RelicType.CURSED) {
            List<RewardItem> relicRewards = new ArrayList<>();
            for (RewardItem reward : AbstractDungeon.getCurrRoom().rewards) {
                if (reward.type == RewardItem.RewardType.RELIC && reward.relicLink == null) {
                    relicRewards.add(reward);
                }
            }
            
            for (RewardItem reward : relicRewards) {
                RelicTier tier = reward.relic.tier;
                if (tier != RelicTier.SPECIAL && tier != RelicTier.DEPRECATED && tier != RelicTier.STARTER) {
                    AbstractRelic newRelic = AbstractDungeon.returnRandomRelic(tier);
                    if (newRelic instanceof AbstractGremRelic) {
                        do {
                            newRelic = AbstractDungeon.returnRandomRelic(tier);
                        }
                        while (!((AbstractGremRelic) newRelic).type.equals(RelicType.CURSED) || newRelic.tier != tier);
                    }
                    
                    if (newRelic != null) {
                        RewardItem replaceReward = new LinkedRewardItem(reward);
                        RewardItem newReward = new LinkedRewardItem(replaceReward, newRelic);
                        int indexOf = AbstractDungeon.getCurrRoom().rewards.indexOf(reward);
                        // Insert after existing reward
                        AbstractDungeon.getCurrRoom().rewards.add(indexOf + 1, newReward);
                        // Replace original
                        AbstractDungeon.getCurrRoom().rewards.set(indexOf, replaceReward);
                    }
                }
            }
        }
    }
    
    //==
    
    private void renderGlow(SpriteBatch sb) {
        if (!Settings.hideRelics) {
            sb.setBlendFunction(770, 1);
            for (AbstractGameEffect e : glowList) {
                e.render(sb);
            }
            sb.setBlendFunction(770, 771);
        }
    }
    
    private void updateGlow() {
        if (isGlowing) {
            glowTimer -= Gdx.graphics.getDeltaTime();
            if (glowTimer < 0.0F) {
                glowList.add(new CursedRelicBorderGlow(this, outline));
                glowTimer = 0.15F;
            }
        }
        
        for (Iterator<CursedRelicBorderGlow> i = glowList.iterator(); i.hasNext(); ) {
            CursedRelicBorderGlow e = (CursedRelicBorderGlow) i.next();
            e.update();
            if (e.isDone) {
                e.dispose();
                i.remove();
            }
        }
    }
    
    public void beginGlowing() {
        this.isGlowing = true;
    }
    
    public void stopGlowing() {
        this.isGlowing = false;
        for (CursedRelicBorderGlow e : glowList) {
            e.duration /= 5.0F;
        }
    }
    
    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        super.renderInTopPanel(sb);
        updateGlow();
        renderGlow(sb);
    }
    
    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        updateGlow();
        renderGlow(sb);
    }
    
    //==
    
    private void cursedDescription() {
        if (type == RelicType.CURSED) {
            this.description = UI_STRINGS[0] + description;
        }
    }
    
    public enum RelicType {
        CURSED,
        NORMAL;
        
        RelicType() {
        }
    }
}
