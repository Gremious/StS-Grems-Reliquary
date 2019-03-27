package gremsReliquary.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AbstractGremEffect extends AbstractGameEffect {
    
    @Override
    public void render(SpriteBatch spriteBatch) {
    
    }
    
    public void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F) {
            isDone = true;
            duration = 0.0F;
        }
    }
    
    @Override
    public void dispose() {
    
    }
}
