package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowReward;
import gremsReliquary.GremsReliquary;
import gremsReliquary.util.TextureLoader;

import java.util.ArrayList;

import static gremsReliquary.GremsReliquary.makeRelicOutlinePath;
import static gremsReliquary.GremsReliquary.makeRelicPath;

public class NeowsTentacle extends CustomRelic {

    private ArrayList<NeowReward> rewards = new ArrayList<>();
    private static int roll = AbstractDungeon.relicRng.random(4);

    // ID, images, text.
    public static final String ID = GremsReliquary.makeID("NeowsTentacle");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public NeowsTentacle() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }


    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        flash();
        this.rewards.add(new NeowReward(0));
        this.rewards.add(new NeowReward(1));
        this.rewards.add(new NeowReward(2));
        this.rewards.add(new NeowReward(3));

        rewards.get(roll).activate();


    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
