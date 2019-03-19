package gremsReliquary;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
<<<<<<< HEAD
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
=======
import com.megacrit.cardcrawl.helpers.CardHelper;
>>>>>>> feature/broken-mirror
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gremsReliquary.relics.*;
import gremsReliquary.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class GremsReliquary implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber
        /*,RelicGetSubscriber*/ {
    public static final Logger logger = LogManager.getLogger(GremsReliquary.class.getName());
    
    public static final boolean hasHalation;
    
    static {
        hasHalation = Loader.isModLoaded("Halation");
        if (hasHalation) {
            logger.info("Detected Halation");
        }
    }
    
    private static String modID;
    
    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Grem's Reliquary";
    private static final String AUTHOR = "Gremious";
    private static final String DESCRIPTION = "A mod that adds any nice relic ideas I have.";
    
<<<<<<< HEAD
    // Settings buttons
    public static Properties gremsReliquaryDefaultSettings = new Properties();
    public static final String PROP_ENABLE_NORMALS = "enableNormals";
    public static boolean enableNormals = false;
    
    public static final String PROP_ENABLE_CURSED = "enableCursed";
    public static boolean enableCursed = false;
=======
    // =============== INPUT TEXTURE LOCATION =================
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
>>>>>>> feature/broken-mirror
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "gremsReliquaryResources/images/Badge.png";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public GremsReliquary() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        setModID("gremsReliquary");
        
<<<<<<< HEAD
        gremsReliquaryDefaultSettings.setProperty(PROP_ENABLE_NORMALS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("gremsReliquary", "gremsReliquaryConfig", gremsReliquaryDefaultSettings);
            config.load();
            enableNormals = config.getBool(PROP_ENABLE_NORMALS);
            enableCursed = config.getBool(PROP_ENABLE_CURSED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        logger.info("Done subscribing");
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        if (ID.equals("theDefault")) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException("Go to your constructor in your class with SpireInitializer and change your mod ID from \"theDefault\""); // THIS ALSO DON'T EDIT
        } else if (ID.equals("theDefaultDev")) { // NO
            modID = "theDefault"; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        String packageName = GremsReliquary.class.getPackage().getName(); // STILL NOT EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS
        if (!modID.equals("theDefaultDev")) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException("Rename your gremsReliquary folder to match your mod ID! " + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException("Rename your gremsReliquaryResources folder to match your mod ID! " + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    // ====== YOU CAN EDIT AGAIN ======
    
=======
        logger.info("Done subscribing");
        
        
        logger.info("Done creating the color");
    }
    
>>>>>>> feature/broken-mirror
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Grem's Reliquary =========================");
        GremsReliquary defaultmod = new GremsReliquary();
        logger.info("========================= /Grem's Reliquary Initialized! Have fun!/ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
<<<<<<< HEAD
        
        
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("Enable the normal relics",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                enableNormals, settingsPanel, (label) -> {
        }, (button) -> {
            enableNormals = button.enabled;
            try {
                SpireConfig config = new SpireConfig("gremsReliquary", "gremsReliquaryConfig", gremsReliquaryDefaultSettings);
                config.setBool(PROP_ENABLE_NORMALS, enableNormals);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        ModLabeledToggleButton enableCursedButton = new ModLabeledToggleButton("Enable the cursed relics",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                enableCursed, settingsPanel, (label) -> {
        }, (button) -> {
            enableCursed = button.enabled;
            try {
                SpireConfig config = new SpireConfig("gremsReliquary", "gremsReliquaryConfig", gremsReliquaryDefaultSettings);
                config.setBool(PROP_ENABLE_CURSED, enableCursed);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton);
        settingsPanel.addUIElement(enableCursedButton);
=======
        settingsPanel.addUIElement(new ModLabel("Grem's Reliquary doesn't have any settings!", 400.0f, 700.0f, settingsPanel, (me) -> {
        }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
>>>>>>> feature/broken-mirror
        
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        // This adds a relic to the Shared pool. Every character can find this relic.
        //   BaseMod.addRelic(new NeowsTentacle(), RelicType.SHARED);
        // UnlockTracker.markRelicAsSeen(NeowsTentacle.ID);
        
        BaseMod.addRelic(new TimeIsMoney(), RelicType.SHARED);
<<<<<<< HEAD
        
        UnlockTracker.markRelicAsSeen(NeowsTentacle.ID);
=======
        BaseMod.addRelic(new BrokenMirror(), RelicType.SHARED);
        
>>>>>>> feature/broken-mirror
        UnlockTracker.markRelicAsSeen(TimeIsMoney.ID);
        UnlockTracker.markRelicAsSeen(BrokenMirror.ID);
        
        
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        pathCheck(); //Ignore this
    }
    // ================ /ADD CARDS/ ===================
    
    // ================ LOAD THE TEXT ===================
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");
        
        // CardStrings
<<<<<<< HEAD
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/GremsReliquary-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/GremsReliquary-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/GremsReliquary-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/GremsReliquary-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/GremsReliquary-Potion-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/GremsReliquary-Orb-Strings.json");
=======
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/GremsReliquary-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/GremsReliquary-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/GremsReliquary-Relic-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class, getModID() + "Resources/localization/eng/GremsReliquary-Orb-Strings.json");
>>>>>>> feature/broken-mirror
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/GremsReliquary-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================
    
<<<<<<< HEAD
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
    
    @Override
=======
  /*  @Override
>>>>>>> feature/broken-mirror
    public void receiveRelicGet(AbstractRelic obtainedRelic) {
        System.out.println("HEY A RELIC WAS OBTAINED!: " + obtainedRelic.relicId);
        if (obtainedRelic.relicId.equals(NeowsTentacle.ID)) {
            AbstractDungeon.effectList.add(new NeowTentacleEffect(obtainedRelic));
            // obtainedRelic.onTrigger();
            
        }
}*/
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        if (ID.equals("theDefault")) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException("Go to your constructor in your class with SpireInitializer and change your mod ID from \"theDefault\""); // THIS ALSO DON'T EDIT
        } else if (ID.equals("theDefaultDev")) { // NO
            modID = "theDefault"; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        String packageName = GremsReliquary.class.getPackage().getName(); // STILL NOT EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS
        if (!modID.equals("theDefaultDev")) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException("Rename your gremsReliquary folder to match your mod ID! " + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException("Rename your gremsReliquaryResources folder to match your mod ID! " + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    // ====== YOU CAN EDIT AGAIN ======
    
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
