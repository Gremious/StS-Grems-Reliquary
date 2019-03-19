package gremsReliquary.effects.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import gremsReliquary.relics.normal.NeowsTentacle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class NeowTentacleEffect extends AbstractGameEffect {
    private static final Logger logger = LogManager.getLogger(NeowTentacleEffect.class.getName());
    private ArrayList<NeowReward> rewards;
    int roll = AbstractDungeon.relicRng.random(3);
    private AbstractRelic relicInstance;
    private boolean openedGridScreen;

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
        this.rewards = new ArrayList<>();

        NeowEvent.rng = new Random(Settings.seed);
        rewards.add(new NeowReward(0));
        rewards.add(new NeowReward(1));
        rewards.add(new NeowReward(2));
        rewards.add(new NeowReward(3));

        logger.info("Your Rewards Are: ");
        for (NeowReward r : rewards) {
            logger.info(r.type + ": " + r.optionLabel);
        }
        logger.info("roll is: " + roll);
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            logger.info("NeowTentacleEffect update started");
            logger.info("openedGridScreen" + openedGridScreen);
            if (!openedGridScreen) {
                logger.info("activating reward: " + rewards.get(roll).optionLabel);
                rewards.get(roll).activate();
                openedGridScreen = true;
            } else if (!AbstractDungeon.isScreenUp) {
                logger.info("Screen is down");
                tickDuration();
            }
            logger.info("This should spam while screen is up");
        } else {
            logger.info("No longer starting duration: " + (duration == Settings.ACTION_DUR_FAST));

            rewards.get(roll).update();
            relicInstance.flash();
            ((NeowsTentacle) relicInstance).setDescriptionAfterLoading(rewards.get(roll).optionLabel);
            relicInstance.stopPulse();
            NeowEvent.rng = null;
            tickDuration();
        }
        tickDuration();
    }

    /*
     *//* @Override
     public void update() {
         if (this.duration == Settings.ACTION_DUR_FAST) {
             System.out.println("NeowTentacleEffect update started");
             System.out.println("roll is: " + roll);
             System.out.println("lowRoll is: " + lowRoll);


             if (!simulatedCheck) {
                 int index = 0;
                 if (hasHalation) {
                     for (NeowReward r : rewards) {
                         if (r.type.equals(SimulatedSpirePatch.SIMULATED_SPIRE)) {
                             index = rewards.indexOf(r);
                             simulated = true;
                         }
                     }
                 }

                 if (simulated) {
                     rewards.remove(index);
                 }
                 simulatedCheck = true;
             }

             if (this.openedGridScreen && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                 tickDuration();
             } else if (!openedGridScreen) {

                 if (simulated) {
                     rewards.get(lowRoll).activate();
                 } else {
                     rewards.get(roll).activate();
                 }
                 this.openedGridScreen = true;
             } else {
                 for (NeowReward r : rewards) {
                     r.update();
                 }
                 System.out.println("Generated reward: ");
                 if (simulated) {
                     System.out.println(rewards.get(lowRoll).optionLabel);
                 } else {
                     System.out.println(rewards.get(roll).optionLabel);
                 }


                 relicInstance.flash();
                 if (simulated) {
                     ((NeowsTentacle) relicInstance).setDescriptionAfterLoading(rewards.get(lowRoll));
                 } else {
                     ((NeowsTentacle) relicInstance).setDescriptionAfterLoading(rewards.get(roll));
                 }

                 relicInstance.stopPulse();
                 NeowEvent.rng = null;
                 tickDuration();
             }
             tickDuration();
         }
     }
 */
    public void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
