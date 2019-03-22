package gremsReliquary.effects.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import gremsReliquary.relics.normal.Placeholder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlaceholderRelicEffect extends AbstractGameEffect {
    private static final Logger logger = LogManager.getLogger(PlaceholderRelicEffect.class.getName());
    private int l;
    private AbstractRelic relicInstance;
    private static float waitDuration = 2.0f;
    
    public PlaceholderRelicEffect(AbstractRelic relicInstance) {
        this.relicInstance = relicInstance;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update() {
        if (waitDuration > 0) {
            tickWaitDuration();
        } else {
            if (duration == Settings.ACTION_DUR_FAST) {
                logger.info("Update start: " + l);
                if (AbstractDungeon.player.hasRelic(Placeholder.ID)
                        && !AbstractDungeon.isScreenUp
                        && AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()
                        && AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.INCOMPLETE) {
                    logger.info(l++);
                    AbstractDungeon.player.getRelic(Placeholder.ID).flash();
                    logger.info(l++);
                    CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN");
                    logger.info(l++);
                    AbstractDungeon.player.loseRelic(Placeholder.ID);
                    logger.info(l++);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relicInstance.makeCopy());
                    logger.info(l++);
                    logger.info("It's done.");
                    isDone = true;
                }
                logger.info("Tick Tock");
            }
        }
    }
    
    public void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }
    
    public void tickWaitDuration() {
        waitDuration -= Gdx.graphics.getDeltaTime();
    }
    
    @Override
    public void render(SpriteBatch spriteBatch) {
    
    }
    
    @Override
    public void dispose() {
    
    }
}
