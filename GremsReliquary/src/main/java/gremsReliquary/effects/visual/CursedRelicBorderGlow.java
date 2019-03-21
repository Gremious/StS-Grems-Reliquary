package gremsReliquary.effects.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CursedRelicBorderGlow extends AbstractGameEffect {
    private AbstractRelic relic;
    private Texture img;
    private float scale;
    
    public CursedRelicBorderGlow(AbstractRelic relic, Texture img) {
        this.relic = relic;
        this.img = img;
        this.duration = 1.2f; // Settings.ACTION_DUR_XLONG; ?
        this.color = Color.PURPLE.cpy();
    }
    
    public void update() {
        this.scale = (1.0F + Interpolation.pow2Out.apply(0.03F, 0.11F, 1.0F - duration)) * relic.scale * Settings.scale;
        this.color.a = this.duration / 3.5F;
        
        this.duration -= Gdx.graphics.getDeltaTime(); //tickDuration();
        if (duration < 0.0F) {
            isDone = true;
            duration = 0.0F;
        }
    }
    
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        float offsetX = 0f;
        float rotation = 0f;
        
        sb.draw(img, relic.currentX - 64.0F + offsetX, relic.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, scale, scale, rotation, 0, 0, 128, 128, false, false);
    }
    
    public void dispose() {
    }
}
