package gremsReliquary.effects.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static gremsReliquary.GremsReliquary.debug;

public class CursedRelicBorderGlow extends AbstractGameEffect {
    private static final Logger logger = LogManager.getLogger(CursedRelicBorderGlow.class.getName());
    
    private AbstractRelic relic;
    private Texture img;
    private float scale;
    float offsetX = 0f;
    float rotation = 0f;
    
    public CursedRelicBorderGlow(AbstractRelic relic, Texture img, float offsetX, float rotation) {
        this.relic = relic;
        this.img = img;
        this.duration = 1.2f;
        this.color = Color.PURPLE.cpy();
    
        this.offsetX = offsetX;
        this.rotation = rotation;
    }
    
    @Override
    public void update() {
        if (debug) logger.info(CursedRelicBorderGlow.class.getSimpleName() + " Update log started");
        if (debug) logger.info("Duration is: " + duration);
        scale = (Interpolation.pow2Out.apply(1.0f, 1.2F, 1.0F - duration)) * relic.scale * Settings.scale;
        
        color.a = duration / 3.5F;
        
        this.duration -= Gdx.graphics.getDeltaTime(); //tickDuration();
        if (duration < 0.0F) {
            isDone = true;
            duration = 0.0F;
        }
    }
    
    @Override
    public void render(SpriteBatch sb) {
        if (debug) logger.info(CursedRelicBorderGlow.class.getSimpleName() + " render log started");
        sb.setColor(color);
        
        sb.draw(img, relic.currentX - 64.0F + offsetX, relic.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, scale, scale, rotation, 0, 0, 128, 128, false, false);
    }
    
    @Override
    public void dispose() {
    }
}
