package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.neow.NeowReward;
import gremsReliquary.GremsReliquary;
import gremsReliquary.util.TextureLoader;

import static gremsReliquary.GremsReliquary.makeRelicOutlinePath;
import static gremsReliquary.GremsReliquary.makeRelicPath;

public class NeowsTentacle extends AbstractGremRelic {


    public static final String ID = GremsReliquary.makeID(NeowsTentacle.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NeowsTentacle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NeowsTentacle.png"));

    public NeowsTentacle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        beginLongPulse();
    }

    public void setDescriptionAfterLoading(String optionLabel) {
        description = DESCRIPTIONS[1] + optionLabel;
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
