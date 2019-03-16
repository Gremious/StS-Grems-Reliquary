package gremsReliquary.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import gremsReliquary.relics.NeowsTentacle;

public class NeowTentacleEffect extends AbstractGameEffect {
    public NeowTentacleEffect() {
    }

    @Override
    public void update() {
        System.out.println("NeowTentacleEffect update started");
        if (AbstractDungeon.player.hasRelic(NeowsTentacle.ID)) {
            AbstractDungeon.player.getRelic(NeowsTentacle.ID).onTrigger();
            isDone = true;
        }
        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
