package gremsReliquary.relics.cursed;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import gremsReliquary.GremsReliquary;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;

public class Curseberry extends AbstractGremRelic {
    public static final String ID = GremsReliquary.makeID(Curseberry.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/Curseberry.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/Curseberry.png");
    public static int amount = 15;
    public static int loseAmount = 6;
    
    AbstractCreature p = AbstractDungeon.player;
    
    public Curseberry() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, RelicType.CURSED, LandingSound.FLAT);
    }
    
    @Override
    public void onEquip() {
        flash();
        p.increaseMaxHp(amount, true);
    }
    
    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof MonsterRoomElite) {
            flash();
            p.decreaseMaxHealth(loseAmount);
        }
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + loseAmount + DESCRIPTIONS[2];
    }
}