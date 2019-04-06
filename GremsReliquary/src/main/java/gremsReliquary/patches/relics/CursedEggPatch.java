package gremsReliquary.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StoreRelic;
import gremsReliquary.relics.cursed.CursedEgg;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CursedEggPatch  {
    protected static final Logger logger = LogManager.getLogger(CursedEggPatch.class.getName());
    
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getRewardCards"
    )
    public static class getRewardCardsPatch {
        @SpireInsertPatch(
                locator = getRewardCardsLocator.class,
                localvars = {"c"}
        )
        public static void Insert(@ByRef AbstractCard[] c) {
            logger.info(getRewardCardsPatch.class.getSimpleName() + " triggered");
            if (AbstractDungeon.player.hasRelic(CursedEgg.ID)) {
                c[0].upgrade();
            }
        }
        
        private static class getRewardCardsLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "rarity");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                // return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }
    
    @SpirePatch(
            clz = TheLibrary.class,
            method = "buttonEffect"
    )
    public static class TheLibraryPatch {
        @SpireInsertPatch(
                locator = buttonEffectLocator.class,
                localvars = {"card"}
        )
        public static void Insert(TheLibrary __instance, int buttonPressed, @ByRef AbstractCard[] card) {
            logger.info(TheLibraryPatch.class.getSimpleName() + " triggered");
            if (AbstractDungeon.player.hasRelic(CursedEgg.ID)) {
                card[0].upgrade();
            }
        }
        
        private static class buttonEffectLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "addToBottom");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                // return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }
    
    @SpirePatch(
            clz = GremlinMatchGame.class,
            method = "initializeCards"
    )
    public static class GremlinMatchGamePatch {
        @SpireInsertPatch(
                locator = initializeCardsLocator.class,
                localvars = {"c"}
        )
        public static void Insert(GremlinMatchGame __instance, @ByRef AbstractCard[] c) {
            logger.info(GremlinMatchGamePatch.class.getSimpleName() + " triggered");
            if (AbstractDungeon.player.hasRelic(CursedEgg.ID)) {
                c[0].upgrade();
            }
        }
        
        private static class initializeCardsLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                // return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }
    
    @SpirePatch(
            clz = RewardItem.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    AbstractCard.CardColor.class
            }
    )
    public static class RewardItemPatch {
        @SpireInsertPatch(
                locator = RewardItemLocator.class,
                localvars = {"c"}
        )
        public static void Insert(RewardItem __instance, AbstractCard.CardColor colorType, @ByRef AbstractCard[] c) {
            logger.info(RewardItemPatch.class.getSimpleName() + " triggered");
            if (AbstractDungeon.player.hasRelic(CursedEgg.ID)) {
                c[0].upgrade();
            }
        }
        
        private static class RewardItemLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                // return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }
    
    @SpirePatch(
            clz = ShopScreen.class,
            method = "initCards"
    )
    public static class ShopScreenInitCardsPatch {
        
        @SpireInsertPatch(
                locator = RewardItemLocator1.class,
                localvars = {"c"}
        )
        public static void Insert1(ShopScreen __instance, @ByRef AbstractCard[] c) {
            logger.info(ShopScreenInitCardsPatch.class.getSimpleName() + " triggered");
            if (AbstractDungeon.player.hasRelic(CursedEgg.ID)) {
                c[0].upgrade();
            }
        }
        
        private static class RewardItemLocator1 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");
                //return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
        
        @SpireInsertPatch(
                locator = RewardItemLocator2.class,
                localvars = {"c"}
        )
        public static void Insert2(ShopScreen __instance, @ByRef AbstractCard[] c) {
            logger.info(ShopScreenInitCardsPatch.class.getSimpleName() + " triggered");
            if (AbstractDungeon.player.hasRelic(CursedEgg.ID)) {
                c[0].upgrade();
            }
        }
        
        private static class RewardItemLocator2 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");
                //return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[3]};
            }
        }
    }
    
    @SpirePatch(
            clz = ShopScreen.class,
            method = "update"
    )
    public static class ShopScreenUpdatePatch {
        
        @SpireInsertPatch(
                locator = RewardItemLocator1.class,
                localvars = {"c"}
        )
        public static void Insert1(ShopScreen __instance, @ByRef AbstractCard[] c) {
            logger.info(ShopScreenUpdatePatch.class.getSimpleName() + " triggered 1");
            if (AbstractDungeon.player.hasRelic(CursedEgg.ID)) {
                c[0].upgrade();
            }
        }
        
        private static class RewardItemLocator1 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");
                //return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
        
        @SpireInsertPatch(
                locator = RewardItemLocator2.class,
                localvars = {"c"}
        )
        public static void Insert2(ShopScreen __instance, @ByRef AbstractCard[] c) {
            logger.info(ShopScreenUpdatePatch.class.getSimpleName() + " triggered 2");
            if (AbstractDungeon.player.hasRelic(CursedEgg.ID)) {
                c[0].upgrade();
            }
        }
        
        private static class RewardItemLocator2 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");
                //return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[5]};
            }
        }
    }
    
    @SpirePatch(
            clz = StoreRelic.class,
            method = "update"
    )
    public static class StoreRelicPatch {
        @SpireInsertPatch(
                locator = RewardItemLocator.class,
                localvars = {"relic", "shopScreen"}
        )
        public static void Insert(StoreRelic __instance, float rugY, AbstractRelic relic, ShopScreen shopScreen) {
            logger.info(RewardItemPatch.class.getSimpleName() + " triggered");
            
            if (relic.relicId.equals(CursedEgg.ID)) {
                shopScreen.applyUpgrades(AbstractCard.CardType.SKILL);
                shopScreen.applyUpgrades(AbstractCard.CardType.ATTACK);
                shopScreen.applyUpgrades(AbstractCard.CardType.POWER);
            }
        }
        
        private static class RewardItemLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractRelic.class, "relicId");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                // return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }
}