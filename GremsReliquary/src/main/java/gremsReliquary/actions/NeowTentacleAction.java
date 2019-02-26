package gremsReliquary.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremsReliquary.relics.NeowsTentacle;

public class NeowTentacleAction extends AbstractGameAction {
    public NeowTentacleAction() {
        System.out.println("NeowTentacleAction is at least a thing");
    }

    @Override
    public void update() {
        System.out.println("NeowTentacleAction update started");
        if (AbstractDungeon.player.hasRelic(NeowsTentacle.ID)) {
            AbstractDungeon.player.getRelic(NeowsTentacle.ID).onTrigger();
            isDone = true;
        }
        isDone = true;
    }
}
