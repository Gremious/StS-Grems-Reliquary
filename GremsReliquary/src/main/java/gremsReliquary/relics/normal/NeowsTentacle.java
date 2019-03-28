package gremsReliquary.relics.normal;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import gremsReliquary.GremsReliquary;
import gremsReliquary.effects.utility.NeowTentacleEffect;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static gremsReliquary.GremsReliquary.makeRelicOutlinePath;
import static gremsReliquary.GremsReliquary.makeRelicPath;

public class NeowsTentacle extends AbstractGremRelic {
    private static final Logger logger = LogManager.getLogger(NeowsTentacle.class.getName());
    public static final String ID = GremsReliquary.makeID(NeowsTentacle.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NeowsTentacle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NeowsTentacle.png"));
    private boolean triggered = false;
    int roll;
    
    public NeowsTentacle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, RelicType.NORMAL, LandingSound.MAGICAL);
    }
    
    @Override
    public void update() {
        super.update();
        
        if (!triggered) {
            flash();
            logger.info("pls");
            AbstractDungeon.effectsQueue.add(0, new NeowTentacleEffect(this));
            logger.info("pls2 - added to effects queue");
            
            /*  This is for adding it as a non-effect but CME on obtain common relic which makes sense
            NeowEvent.rng = new Random(Settings.seed);
            roll = AbstractDungeon.relicRng.random(3);
            NeowReward neowReward = new NeowReward(roll);
            if (debug) logger.info("The roll is: " + roll);
            if (debug) logger.info("The neowReward is: " + neowReward.optionLabel);
            this.setDescriptionAfterLoading2(neowReward.optionLabel);
            neowReward.activate();
            */
            
            triggered = true;
        }
    }
    
    public void setDescriptionAfterLoading(String positive, String negative) {
        description = DESCRIPTIONS[1] + positive + " NL " + negative + "]";
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        stopPulse();
        initializeTips();
    }
    
    public void setDescriptionAfterLoading2(String full) {
        description = DESCRIPTIONS[1] + " NL " + full;
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
