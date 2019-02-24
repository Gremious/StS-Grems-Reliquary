package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.neow.NeowReward;
import gremsReliquary.GremsReliquary;
import gremsReliquary.util.TextureLoader;

import java.util.ArrayList;

import static gremsReliquary.GremsReliquary.makeRelicOutlinePath;
import static gremsReliquary.GremsReliquary.makeRelicPath;

public class NeowsTentacle extends CustomRelic {
    private ArrayList<NeowReward> rewards = new ArrayList<>();
    private int roll;

    public static final String ID = GremsReliquary.makeID("NeowsTentacle");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NeowsTentacle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NeowsTentacle.png"));

    public NeowsTentacle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        roll = AbstractDungeon.relicRng.random(3);
        if (rewards.size() == 0) {
            rewards.add(new NeowReward(0));
            rewards.add(new NeowReward(1));
            rewards.add(new NeowReward(2));
            rewards.add(new NeowReward(3));
        }
        flash();
        rewards.get(roll).activate();
        setDescriptionAfterLoading();
    }

    private void setDescriptionAfterLoading() {
        description = DESCRIPTIONS[1] + rewards.get(roll).optionLabel;
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
