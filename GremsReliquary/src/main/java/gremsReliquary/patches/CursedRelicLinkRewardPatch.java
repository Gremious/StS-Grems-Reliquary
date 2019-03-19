package gremsReliquary.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gremsReliquary.relics.AbstractGremRelic;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "update"
)
public class CursedRelicLinkRewardPatch {
    protected static final Logger logger = LogManager.getLogger(CursedRelicLinkRewardPatch.class.getName());
    
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {}
    )
    public static void Insert(AbstractRoom __instance) {
        logger.info(CursedRelicLinkRewardPatch.class.getSimpleName() + " triggered");
        for (RewardItem r : AbstractDungeon.getCurrRoom().rewards) {
            logger.info("Pritning out the rewards for this room: " + r.type + ": " + r);
            if (r.type == RewardItem.RewardType.RELIC) {
                logger.info("We got a relic reward.");
                AbstractRelic relic = r.relic;
                logger.info("That relic is: " + relic.name + " ID: " + relic.relicId);
                if (r.relic instanceof AbstractGremRelic) {
                    ((AbstractGremRelic) relic).curseTrigger();
                    AbstractDungeon.combatRewardScreen.setupItemReward();
                }
            }
        }
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            // Insert here to be after the game is saved
            // Avoids weird save/load issues
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "loading_post_combat");
            int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{found[found.length - 1]};
        }
    }
}