package gremsReliquary.effects.utility;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import kotlinReliquary.relics.normal.Placeholder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static gremsReliquary.GremsReliquary.debug;

public class PlaceholderRelicEffect extends AbstractGameEffect {
    private static final Logger logger = LogManager.getLogger(PlaceholderRelicEffect.class.getName());
    private int l;
    private AbstractRelic relicInstance;
    private static float waitDuration = 1.2f;
    
    public PlaceholderRelicEffect(AbstractRelic relicInstance) {
        this.relicInstance = relicInstance;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update() {
        if (!(relicInstance instanceof Placeholder)) {
            if (waitDuration > 0) {
                if (relicInstance instanceof CustomSavable
                        || relicInstance.relicId.equals(BottledFlame.ID)
                        || relicInstance.relicId.equals(BottledLightning.ID)
                        || relicInstance.relicId.equals(BottledTornado.ID)) {
                    tickWaitDuration();
                } else {
                    waitDuration = -0.1f;
                }
            } else {
                if (duration == Settings.ACTION_DUR_FAST) {
                    if (debug) logger.info("Update start: " + l);
                    if (AbstractDungeon.player.hasRelic(Placeholder.Companion.getID())
                            && !AbstractDungeon.isScreenUp
                            && AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()
                            && AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.INCOMPLETE) {
                        if (debug) logger.info(l++);
                        AbstractDungeon.player.getRelic(Placeholder.Companion.getID()).flash();
                        if (debug) logger.info(l++);
                        CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN");
                        if (debug) logger.info(l++);
                        AbstractDungeon.player.loseRelic(Placeholder.Companion.getID());
                        if (debug) logger.info(l++);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relicInstance.makeCopy());
                        if (debug) logger.info(l++);
                        if (debug) logger.info("It's done.");
                        isDone = true;
                    }
                    if (debug) logger.info("Tick Tock");
                }
            }
        } else {
            isDone = true;
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
