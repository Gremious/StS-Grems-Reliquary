package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.random.Random;
import gremsReliquary.GremsReliquary;
import gremsReliquary.util.TextureLoader;

import static gremsReliquary.GremsReliquary.makeRelicOutlinePath;
import static gremsReliquary.GremsReliquary.makeRelicPath;

public class NeowsTentacle extends CustomRelic implements ClickableRelic {
    private static NeowReward reward;

    public static final String ID = GremsReliquary.makeID("NeowsTentacle");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NeowsTentacle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NeowsTentacle.png"));

    public NeowsTentacle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        beginLongPulse();
    }

    @Override
    public void onTrigger() {
        NeowEvent.rng = new Random(Settings.seed);
        int roll = AbstractDungeon.relicRng.random(3);
        System.out.println("roll is: " + roll);

        reward = new NeowReward(roll);

        System.out.println("Generated reward: ");
        System.out.println(reward.optionLabel);

        flash();
        reward.activate();
        setDescriptionAfterLoading();
        stopPulse();
    }

    public void setDescriptionAfterLoading() {
        description = DESCRIPTIONS[1] + reward.optionLabel;
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {

    }
}
