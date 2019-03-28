package gremsReliquary.relics.normal;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import gremsReliquary.GremsReliquary;
import gremsReliquary.effects.utility.NeowTentacleEffect;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;

import static gremsReliquary.GremsReliquary.makeRelicOutlinePath;
import static gremsReliquary.GremsReliquary.makeRelicPath;

public class NeowsTentacle extends AbstractGremRelic {
    
    public static final String ID = GremsReliquary.makeID(NeowsTentacle.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NeowsTentacle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NeowsTentacle.png"));
    private boolean triggered = false;
    
    public NeowsTentacle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, RelicType.NORMAL, LandingSound.MAGICAL);
    }
    
    @Override
    public void update() {
        super.update();
        
        if (!triggered) {
            flash();
            AbstractDungeon.effectList.add(new NeowTentacleEffect(this));
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
