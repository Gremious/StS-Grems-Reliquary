package gremsReliquary.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.Orichalcum;
import gremsReliquary.relics.normal.Mithril;
import javassist.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrichalcumMythrilSynergyPatch {
    protected static final Logger logger = LogManager.getLogger(OrichalcumMythrilSynergyPatch.class.getName());
    
    @SpirePatch(
            clz = Orichalcum.class,
            method = "onPlayerEndTurn"
    )
    public static class onPlayerEndTurnPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(Orichalcum __instance) {
            logger.info(onPlayerEndTurnPatch.class.getSimpleName() + " triggered");
            if (AbstractDungeon.player.hasRelic(Mithril.ID) || OriBrokenField.broken.get(__instance)) {
                return SpireReturn.Return(null);
            }
            
            return SpireReturn.Continue();
        }
        
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "actionManager");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                // return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
            }
        }
    }
    
    @SpirePatch(
            clz = Orichalcum.class,
            method = "atTurnStart"
    )
    public static class atTurnStartPatch {
        public static void Postfix(Orichalcum __instance) {
            if (AbstractDungeon.player.hasRelic(Mithril.ID)) {
                AbstractDungeon.player.getRelic(Orichalcum.ID).stopPulse();
                __instance.stopPulse();
            }
        }
    }
 /*   @SpirePatch(
            clz = Orichalcum.class,
            method = "getUpdatedDescription"
    )
    public static class getUpdatedDescriptionPatch {
        @SpirePrefixPatch
        public static SpireReturn<String> Prefix(Orichalcum __instance) {
            logger.info(onPlayerEndTurnPatch.class.getSimpleName() + " triggered");
            
            if (CardCrawlGame.isInARun()) {
                if (AbstractDungeon.player.hasRelic(Mithril.ID)) {
                    __instance.flavorText = ((Mithril) AbstractDungeon.player.getRelic(Mithril.ID)).getOriFlavor();
                    
                    return SpireReturn.Return(((Mithril) AbstractDungeon.player.getRelic(Mithril.ID)).getOriDescription());
                }
            }
            return SpireReturn.Continue();
        }
    }*/
    
    @SpirePatch(
            clz = Orichalcum.class,
            method = SpirePatch.CLASS
    )
    public static class OriBrokenField {
        public static SpireField<Boolean> broken = new SpireField<>(() -> false);
    }
    
    @SpirePatch(
            clz = Orichalcum.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class OrichalcumOnBattleStartPatch {
        public static void Raw(CtBehavior ctMethodToPatch) throws CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            ClassPool pool = ctClass.getClassPool();
            CtMethod method = CtNewMethod.make(
                    "public void atBattleStart() {"
                            + AbstractPlayer.class.getName() + " p = " + AbstractDungeon.class.getName() + ".player; "
                            + AbstractDungeon.class.getName() + ".actionManager.addToBottom(new " + ApplyPowerAction.class.getName() + "(p, p, new " + ThornsPower.class.getName() + "(p, 3), 3)); "
                            + AbstractDungeon.class.getName() + ".actionManager.addToTop(new " + RelicAboveCreatureAction.class.getName() + "(p, this)); "
                            + " this.stopPulse(); "
                            + " if (p.hasRelic(" + Mithril.class.getName() + ".ID)) {((" + Mithril.class.getName() + ") p.getRelic(" + Mithril.class.getName() + ".ID)).changeOri(); }}",
                    ctClass
            );
            logger.info("I'm curious. Simple: " + AbstractPlayer.class.getSimpleName());
            logger.info("I'm curious. Canonical: " + AbstractPlayer.class.getCanonicalName());
            logger.info("I'm curious. Type: " + AbstractPlayer.class.getTypeName());
            logger.info("I'm curious. Name: " + AbstractPlayer.class.getName());
            ctClass.addMethod(method);
        }
    }
    /*
    CtMethod method = CtNewMethod.make(
            "public void atBattleStart() {"
                    + AbstractPlayer.class.getName() + " p = " + AbstractDungeon.class.getName() + ".player; "
                    + AbstractDungeon.class.getName() + ".actionManager.addToBottom(new " + ApplyPowerAction.class.getName() + "(p, p, new " + ThornsPower.class.getName() + "(p, 3), 3)); "
                    + AbstractDungeon.class.getName() + ".actionManager.addToTop(new " + RelicAboveCreatureAction.class.getName() + "(p, new " + Orichalcum.class.getName() + "())); "
                    + " p.getRelic(" + Orichalcum.class.getName() + ".ID).getUpdatedDescription();} "
                    + " p.getRelic(" + Orichalcum.class.getName() + ".ID).stopPulse();   } "
                    + " if (p.hasRelic(" + Mithril.class.getName() + ".ID)) { p.getRelic(" + Mithril.class.getName() + ".ID).changeOri(); "
                    + OriBrokenField.class.getName() + ".broken.set(this, true)}",
            ctClass
    );*/
}