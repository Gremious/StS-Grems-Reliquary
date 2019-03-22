package gremsReliquary.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremsReliquary.relics.cursed.CursedEgg;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "getRewardCards"
)
public class CursedEggPatch {
    protected static final Logger logger = LogManager.getLogger(CursedEggPatch.class.getName());
    
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"c"}
    )
    public static void Insert(AbstractCard c) {
        logger.info(CursedEggPatch.class.getSimpleName() + " triggered");
        if (AbstractDungeon.player.hasRelic(CursedEgg.ID)) {
            AbstractDungeon.player.getRelic(CursedEgg.ID).flash();
            c.upgrade();
        }
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "rarity");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            // return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }
}