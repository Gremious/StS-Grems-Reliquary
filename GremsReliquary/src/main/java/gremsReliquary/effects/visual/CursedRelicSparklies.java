package gremsReliquary.effects.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremsReliquary.effects.AbstractGremEffect;
import gremsReliquary.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static gremsReliquary.GremsReliquary.debug;

public class CursedRelicSparklies extends AbstractGremEffect {
    private static final Logger logger = LogManager.getLogger(CursedRelicSparklies.class.getName());
    
    private AbstractRelic relic;
    private Texture img;
    private float MAX_DURATION = 1.0f;
    float offsetX = 0f;
    float rotation = 0f;
    
    public CursedRelicSparklies(AbstractRelic relic, float offsetX, float rotation) {
        this.relic = relic;
       // this.img = TextureLoader.getTexture("gremsReliquaryResources/images/particles/Single.png");
        this.img = TextureLoader.getTexture("gremsReliquaryResources/images/particles/Single.png");
        this.duration = 1.0f;
        
        Random rand = new Random();
        this.color = rand.nextInt(2) == 0 ? Color.PURPLE.cpy() : Color.VIOLET.cpy();
        
        this.offsetX = offsetX;
        this.rotation = rotation;
    }
    
    @Override
    public void update() {
        if (debug) logger.info(CursedRelicSparklies.class.getSimpleName() + " Update log started");
        //scale = duration;
        scale = 0.3f * Settings.scale;
        color.a = (Interpolation.pow2Out.apply(1.0f, 0.0F, (MAX_DURATION - duration) / MAX_DURATION));
        
        tickDuration();
    }
    
    @Override
    public void render(SpriteBatch sb) {
        if (debug) logger.info(CursedRelicSparklies.class.getSimpleName() + " render log started");
        
        sb.setColor(color);
        sb.draw(img, relic.currentX - 64.0F + offsetX, relic.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, scale, scale, rotation, 0, 0, 128, 128, false, false);
    }
}
