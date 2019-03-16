package gremsReliquary.Templates;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.neow.NeowReward;
import gremsReliquary.GremsReliquary;
import gremsReliquary.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static gremsReliquary.GremsReliquary.makeRelicOutlinePath;
import static gremsReliquary.GremsReliquary.makeRelicPath;

public class RelicTemplate extends CustomRelic {
    protected static final Logger logger = LogManager.getLogger(RelicTemplate.class.getName());

    public static final String ID = GremsReliquary.makeID(gremsReliquary.relics.NeowsTentacle.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NeowsTentacle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NeowsTentacle.png"));

    public RelicTemplate() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        beginLongPulse();
    }

    public void setDescriptionAfterLoading(NeowReward reward) {
        description = DESCRIPTIONS[1] + reward.optionLabel;
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