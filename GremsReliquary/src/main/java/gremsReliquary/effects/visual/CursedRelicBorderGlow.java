package gremsReliquary.effects.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremsReliquary.effects.AbstractGremEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static gremsReliquary.GremsReliquary.debug;

public class CursedRelicBorderGlow extends AbstractGremEffect {
    private static final Logger logger = LogManager.getLogger(CursedRelicBorderGlow.class.getName());
    
    private AbstractRelic relic;
    private Texture img;
    private float MAX_DURATION = 2.0f;
    float offsetX = 0f;
    float rotation = 0f;
    
    public CursedRelicBorderGlow(AbstractRelic relic, Texture img, float offsetX, float rotation) {
        this.relic = relic;
        this.img = img;
        this.duration = 2.0f;
        this.color = Color.PURPLE.cpy();
        
        this.offsetX = offsetX;
        this.rotation = rotation;
    }
    
    @Override
    public void update() {
        //   if (debug) logger.info(CursedRelicBorderGlow.class.getSimpleName() + " Update log started");
        //  if (debug) logger.info("Duration is: " + duration);
        scale = (Interpolation.pow2Out.apply(1.0f, 1.15F, (MAX_DURATION - duration) / MAX_DURATION)) * relic.scale * Settings.scale;
        
        color.a = duration / 1.5F;
        
        tickDuration();
    }
    
    @Override
    public void render(SpriteBatch sb) {
        if (debug) logger.info(CursedRelicBorderGlow.class.getSimpleName() + " render log started");
        sb.setColor(color);
        
        sb.draw(img, relic.currentX - 64.0F + offsetX, relic.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, scale, scale, rotation, 0, 0, 128, 128, false, false);
    }
}
