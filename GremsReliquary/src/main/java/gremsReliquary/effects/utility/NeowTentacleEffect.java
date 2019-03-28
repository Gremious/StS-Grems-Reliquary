package gremsReliquary.effects.utility;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremsReliquary.effects.AbstractGremEffect;
import gremsReliquary.relics.normal.NeowsTentacle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static gremsReliquary.GremsReliquary.debug;

public class NeowTentacleEffect extends AbstractGremEffect {
    private static final Logger logger = LogManager.getLogger(NeowTentacleEffect.class.getName());
    int roll = AbstractDungeon.relicRng.random(3);
    private AbstractRelic relicInstance;
/*
    private boolean simulatedCheck;
    boolean simulated = false;
    int lowRoll = AbstractDungeon.relicRng.random(2);
*/
    
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Neow Event");
    public static final String[] TEXT = characterStrings.TEXT;
    
    public NeowTentacleEffect(AbstractRelic relicInstance) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.relicInstance = relicInstance;
        NeowEvent.rng = new Random(Settings.seed);
    }
    
    public void update() {
        NeowReward neowReward = new NeowReward(roll);
        if (debug) logger.info("The roll is: " + roll);
        if (debug) logger.info("The neowReward is: " + neowReward.optionLabel);
        ((NeowsTentacle) relicInstance).setDescriptionAfterLoading2(neowReward.optionLabel);
        neowReward.activate();
        
        /* lmao ignore all of this
        try {
            Method getDrawbacks = NeowReward.class.getDeclaredMethod("getRewardDrawbackOptions");
            Method getRewards = NeowReward.class.getDeclaredMethod("getRewardOptions", int.class);
            
            getDrawbacks.setAccessible(true);
            getRewards.setAccessible(true);
            
            drawbacks = ((ArrayList<NeowReward.NeowRewardDrawbackDef>) getDrawbacks.invoke(neowReward));
            rewards = ((ArrayList<NeowReward.NeowRewardDef>) getRewards.invoke(neowReward, roll));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        
        if (debug)
            rewards.forEach(NeowRewardDef -> logger.info("drawbacks: " + NeowRewardDef.type + " - " + NeowRewardDef.desc));
        if (debug)
            drawbacks.forEach(neowRewardDrawbackDef -> logger.info("drawbacks: " + neowRewardDrawbackDef.type + " - " + neowRewardDrawbackDef.desc));
        
        int rewardsRandom = AbstractDungeon.miscRng.random(drawbacks.size());
        int drawbacksRandom = AbstractDungeon.miscRng.random(drawbacks.size());
        
        if (debug)
            logger.info("The reward roll is: " + rewardsRandom + " so your reward is: " + rewards.get(rewardsRandom).desc);
        if (debug)
            logger.info("The drawbacks roll is: " + drawbacksRandom + " so your drawback is: " + drawbacks.get(drawbacksRandom).desc);
        
        ((NeowsTentacle) relicInstance).setDescriptionAfterLoading(rewards.get(rewardsRandom).desc, drawbacks.get(drawbacksRandom).desc);
        
        */
        
        isDone = true;
    }
}
