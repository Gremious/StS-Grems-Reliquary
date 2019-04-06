package gremsReliquary.relics.normal;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.random.Random;
import gremsReliquary.GremsReliquary;
import gremsReliquary.util.TextureLoader;
import kotlinReliquary.relics.AbstractGremRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

import static gremsReliquary.GremsReliquary.*;

public class NeowsTentacle extends AbstractGremRelic {
    private static final Logger logger = LogManager.getLogger(NeowsTentacle.class.getName());
    public static final String ID = GremsReliquary.makeID(NeowsTentacle.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NeowsTentacle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NeowsTentacle.png"));
    private static boolean triggered;
    private static boolean shouldUpdate;
    private boolean activatedCheck;
    static int roll;
    private static NeowReward neowReward;
    
    public NeowsTentacle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, RelicType.NORMAL, LandingSound.MAGICAL);
        triggered = false;
    }
    
    @Override
    public void update() {
        super.update();
        if (triggered) {
            try {
                logger.info("OOOAAEE");
                Field activated = NeowReward.class.getDeclaredField("activated");
                activated.setAccessible(true);
                activatedCheck = activated.getBoolean(neowReward);
                if (activatedCheck) {
                    logger.info("Neow Reward update - triggered");
                    neowReward.update();
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void iLoveConcurrentModificationExceptions() {
        if (!triggered) {
            logger.info("pls");
            NeowEvent.rng = new Random(Settings.seed);
            roll = AbstractDungeon.relicRng.random(3);
            neowReward = new NeowReward(roll);
            if (debug) logger.info("The roll is: " + roll);
            if (debug) logger.info("The neowReward is: " + neowReward.optionLabel);
            neowReward.activate();
            triggered = true;
            AbstractDungeon.player.getRelic(NeowsTentacle.ID).flash();
            ((NeowsTentacle) AbstractDungeon.player.getRelic(NeowsTentacle.ID)).setDescriptionAfterLoading(neowReward.optionLabel);
        }
    }
    
    public void setDescriptionAfterLoading(String full) {
        description = DESCRIPTIONS[1] + full;
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        stopPulse();
        initializeTips();
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
