package gremsReliquary.patches.interfaces;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import gremsReliquary.interfaces.BetterBetterOnUsePotionRelic;
import javassist.CtBehavior;

public class betterBetterOnUsePotionRelicPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class FairyPotion {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"p"}
        )
        public static void Insert(AbstractPlayer __instance, DamageInfo info, AbstractPotion potion) {
            Do(potion, __instance);
        }
    }
    
    
 
    @SpirePatch(
            clz = PotionPopUp.class,
            method = "updateTargetMode"
    )
    public static class NormalPotionsOnMonsters {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"potion", "hoveredMonster"}
        )
        public static void Insert(PotionPopUp __instance, AbstractPotion potion, AbstractMonster hoveredMonster) {
            Do(potion, hoveredMonster);
        }
    }
    
    @SpirePatch(
            clz = PotionPopUp.class,
            method = "updateInput"
    )
    public static class NormalPotionsOnPlayer {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"potion"}
        )
        public static void Insert(PotionPopUp __instance, AbstractPotion potion) {
            Do(potion, null);
        }
    }
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(TopPanel.class, "destroyPotion");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
    private static void Do(AbstractPotion potion, AbstractCreature target) {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof BetterBetterOnUsePotionRelic) {
                ((BetterBetterOnUsePotionRelic) relic).betterBetterOnUsePotion(potion, target);
            }
        }
    }
}