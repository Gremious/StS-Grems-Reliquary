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
        
        isDone = true;
    }
}
