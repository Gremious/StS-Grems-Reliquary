package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import gremsReliquary.effects.visual.CursedRelicSparklies;
import gremsReliquary.rewards.LinkedRewardItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbstractGremRelic extends CustomRelic {
    private static final Logger logger = LogManager.getLogger(AbstractGremRelic.class.getName());
    
    public static RelicType type;
    private Texture outline;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("gremsReliquary:RelicsUI");
    public static final String[] UI_STRINGS = uiStrings.TEXT;
    private ArrayList<CursedRelicBorderGlow> glowList = new ArrayList<>();
    private ArrayList<CursedRelicSparklies> sparkleList = new ArrayList<>();
    private float glowTimer = 0.0F;
    private float sparkleTimer = 0.0F;
    private int pulseCount = 0;
    
    private Field offsetXField;
    private Field rotationField;
    
    {
        try {
            offsetXField = AbstractRelic.class.getDeclaredField("offsetX");
            rotationField = AbstractRelic.class.getDeclaredField("rotation");
            
            offsetXField.setAccessible(true);
            rotationField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    
    public AbstractGremRelic(String id, Texture texture, Texture outline, RelicTier tier, RelicType type, LandingSound sfx) {
        super(id, texture, tier, sfx);
        this.type = type;
        cursedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
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
        if (type.equals(RelicType.CURSED)) {
            if (!Settings.hideRelics) {
                //   if (debug) logger.info("render Glow log is on");
                sb.setBlendFunction(770, 1);
                for (CursedRelicBorderGlow e : glowList) {
                    //       if (debug) logger.info("Triggered glow");
                    e.render(sb);
                }
                sb.setBlendFunction(770, 771);
            }
        }
    }
    
    private void updateGlow() {
        if (type.equals(RelicType.CURSED)) {
            glowTimer -= Gdx.graphics.getDeltaTime();
            
            float offsetX = 0.0f;
            float rotation = 0.0f;
            try {
                offsetX = offsetXField.getFloat(offsetXField);
                rotation = offsetXField.getFloat(offsetXField);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            
            if (glowTimer < 0.0F) {
                glowList.add(new CursedRelicBorderGlow(this, outline, offsetX, rotation));
                glowTimer = 2.5F;
            }
            
            /*
            if (glowTimer < 0.0F) {
                if (pulseCount < 3) {
                    glowList.add(new CursedRelicBorderGlow(this, outline, offsetX, rotation));
                    pulseCount++;
                    glowTimer = 0.7F;
                } else {
                    glowList.add(new CursedRelicBorderGlow(this, outline, offsetX, rotation));
                    pulseCount = 0;
                    glowTimer = 1.5F;
                }
            }
            */
            
            for (Iterator<CursedRelicBorderGlow> i = glowList.iterator(); i.hasNext(); ) {
                CursedRelicBorderGlow e = i.next();
                //                if (debug) logger.info("gonna update " + e.hashCode());
                e.update();
                if (e.isDone) {
                    //                    if (debug) logger.info(e.hashCode() + " is totally done we gonna remove");
                    e.dispose();
                    i.remove();
                }
            }
        }
    }
    
    private void renderSparkles(SpriteBatch sb) {
        if (!Settings.hideRelics) {
            //   if (debug) logger.info("render sparkle log is on");
            sb.setBlendFunction(770, 1);
            for (AbstractGameEffect e : sparkleList) {
                //        if (debug) logger.info("Triggered sparkle");
                e.render(sb);
            }
            sb.setBlendFunction(770, 771);
        }
    }
    
    private void updateSparkle() {
        if (type.equals(RelicType.CURSED)) {
            sparkleTimer -= Gdx.graphics.getDeltaTime();
            float offsetX = 0.0f;
            float rotation = 0.0f;
            
            try {
                offsetX = offsetXField.getFloat(offsetXField);
                rotation = offsetXField.getFloat(offsetXField);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            
            if (sparkleTimer < 0.0F) {
                sparkleList.add(new CursedRelicSparklies(this, offsetX, rotation));
                sparkleTimer = 2.0F;
            }
            
            for (Iterator<CursedRelicSparklies> i = sparkleList.iterator(); i.hasNext(); ) {
                CursedRelicSparklies e = (CursedRelicSparklies) i.next();
                e.update();
                if (e.isDone) {
                    e.dispose();
                    i.remove();
                }
            }
        }
    }
    
    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        updateGlow();
        renderGlow(sb);
        //        updateSparkle();
        //        renderSparkles(sb);
        super.renderInTopPanel(sb);
    }
    
    @Override
    public void render(SpriteBatch sb) {
        updateGlow();
        renderGlow(sb);
        //        renderSparkles(sb);
        //        updateSparkle();
        super.render(sb);
    }
    
    @Override
    public void render(SpriteBatch sb, boolean renderAmount, Color outlineColor) {
        updateGlow();
        renderGlow(sb);
        //        renderSparkles(sb);
        //        updateSparkle();
        super.render(sb, renderAmount, outlineColor);
    }
    
    @Override
    public void renderWithoutAmount(SpriteBatch sb, Color c) {
        updateGlow();
        renderGlow(sb);
        //        renderSparkles(sb);
        //        updateSparkle();
        super.renderWithoutAmount(sb, c);
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
