package gremsReliquary.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import gremsReliquary.rewards.LinkedRewardItem;

import java.util.ArrayList;
import java.util.List;

public class AbstractGremRelic extends CustomRelic {
    
    public static RelicType type;
    
    public AbstractGremRelic(String id, Texture texture, Texture outline, RelicTier tier, RelicType type, LandingSound sfx) {
        super(id, texture, tier, sfx);
        this.type = type;
        cursedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
    
    public static void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    public static void curseTrigger() {
        if (type == RelicType.CURSED) {
            List<RewardItem> relicRewards = new ArrayList<>();
            for (RewardItem reward : AbstractDungeon.getCurrRoom().rewards) {
                if (reward.type == RewardItem.RewardType.RELIC && reward.relicLink == null) {
                    relicRewards.add(reward);
                }
            }
            
            for (RewardItem reward : relicRewards) {
                RelicTier tier = reward.relic.tier;
                if (tier != RelicTier.SPECIAL && tier != RelicTier.DEPRECATED && tier != RelicTier.STARTER) {
                    AbstractRelic newRelic = AbstractDungeon.returnRandomRelic(tier);
                    if (newRelic instanceof AbstractGremRelic) {
                        do {
                            newRelic = AbstractDungeon.returnRandomRelic(tier);
                        }
                        while (!((AbstractGremRelic) newRelic).type.equals(RelicType.CURSED) || newRelic.tier != tier);
                    }
                    
                    if (newRelic != null) {
                        RewardItem replaceReward = new LinkedRewardItem(reward);
                        RewardItem newReward = new LinkedRewardItem(replaceReward, newRelic);
                        int indexOf = AbstractDungeon.getCurrRoom().rewards.indexOf(reward);
                        // Insert after existing reward
                        AbstractDungeon.getCurrRoom().rewards.add(indexOf + 1, newReward);
                        // Replace original
                        AbstractDungeon.getCurrRoom().rewards.set(indexOf, replaceReward);
                    }
                }
            }
        }
    }
    
    private void cursedDescription() {
        if (type == RelicType.CURSED) {
            this.description = "Cursed. NL " + description;
        }
    }
    
    public enum RelicType {
        CURSED,
        NORMAL;
        
        RelicType() {
        }
    }
}
