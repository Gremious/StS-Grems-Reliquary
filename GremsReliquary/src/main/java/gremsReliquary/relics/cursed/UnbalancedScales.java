package gremsReliquary.relics.cursed;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gremsReliquary.GremsReliquary;
import gremsReliquary.relics.AbstractGremRelic;
import gremsReliquary.util.TextureLoader;

public class UnbalancedScales extends AbstractGremRelic implements ClickableRelic {
    public static final String ID = GremsReliquary.makeID(UnbalancedScales.class.getSimpleName());
    public static final Texture IMG = TextureLoader.getTexture("gremsReliquaryResources/images/relics/UnbalancedScales.png");
    public static final Texture OUTLINE = TextureLoader.getTexture("gremsReliquaryResources/images/relics/outline/UnbalancedScales.png");
    public static int amount = 2;
    
    AbstractCreature p = AbstractDungeon.player;
    private static boolean usedThisCombat = false;
    
    public UnbalancedScales() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, RelicType.CURSED, LandingSound.CLINK);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
    
    @Override
    public void atBattleStart() {
        beginLongPulse();
        usedThisCombat = false;
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        flash();
        act(new ApplyPowerAction(p, p, new StrengthPower(p, 5), 5));
        act(new ApplyPowerAction(p, p, new DexterityPower(p, -5), -5));
    }
    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public void onVictory() {
        stopPulse();
    }
    
    @Override
    public void onRightClick() {
        if (!usedThisCombat && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            stopPulse();
            act(new ApplyPowerAction(p, p, new StrengthPower(p, -10), -10));
            act(new ApplyPowerAction(p, p, new DexterityPower(p, 10), 10));
            usedThisCombat = true;
        }
    }
}