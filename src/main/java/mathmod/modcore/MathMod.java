package mathmod.modcore;

import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.localization.*;
import mathmod.cards.*;
import mathmod.characters.Mathematician;
import mathmod.relics.Calculator;
import mathmod.relics.Dices;
import mathmod.relics.RuleBook;
import mathmod.variables.SecondMagicNumber;

import java.nio.charset.StandardCharsets;

import static com.megacrit.cardcrawl.core.Settings.language;
import static mathmod.characters.Mathematician.Enums.MATHEMATICIAN;
import static mathmod.characters.Mathematician.Enums.MATH_CARD;

@SpireInitializer
public class MathMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber {

    @SpireEnum
    public static AbstractCard.CardTags ASSIGNED;

    public MathMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(MATH_CARD, MATH_COLOR, MATH_COLOR, MATH_COLOR, MATH_COLOR, MATH_COLOR, MATH_COLOR, MATH_COLOR,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENERGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,SMALL_ORB);
    }
    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "MathResources/img/char/Character_Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "MathResources/img/char/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "MathResources/img/512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "MathResources/img/512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "MathResources/img/512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "MathResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "MathResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "MathResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "MathResources/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "MathResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENERGY_ORB = "MathResources/img/char/cost_orb.png";
    public static final Color MATH_COLOR = new Color(192.0F / 255.0F, 192.0F / 255.0F, 192.0F / 255.0F, 1.0F);

    public static void initialize() {
        new MathMod();
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable((DynamicVariable)new SecondMagicNumber());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Multiplication());
        BaseMod.addCard(new Addition());
        BaseMod.addCard(new Subtraction());
        BaseMod.addCard(new Settlement());
        BaseMod.addCard(new EqualsSign());
        BaseMod.addCard(new CommutativeLaw());
        BaseMod.addCard(new DistributiveLaw());
        BaseMod.addCard(new BinIronWave());
        BaseMod.addCard(new Hours());
        BaseMod.addCard(new UnbearableWeight());
        BaseMod.addCard(new Homework());
        BaseMod.addCard(new AddedDefend());
        BaseMod.addCard(new DeratedStrike());
        BaseMod.addCard(new Increase());
        BaseMod.addCard(new UltimateAnswer());
        BaseMod.addCard(new MakeZero());
        BaseMod.addCard(new TwentyFour());
        BaseMod.addCard(new FourColorTheorem());
        BaseMod.addCard(new ChessboardWheat());
        BaseMod.addCard(new ShadyDeal());
        BaseMod.addCard(new ChickenRabbit());
        BaseMod.addCard(new OpenCage());

        BaseMod.addCard(new DiceRule());
    }

    @Override
    public void receiveEditRelics(){
        BaseMod.addRelicToCustomPool(new Calculator(), MATH_CARD);
        BaseMod.addRelicToCustomPool(new RuleBook(), MATH_CARD);
        BaseMod.addRelicToCustomPool(new Dices(), MATH_CARD);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "ENG";
        if (language == GameLanguage.ZHS) {
            lang = "ZHS";
        }
        String json = Gdx.files.internal("MathResources/localization/" + lang + "/keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords)
                BaseMod.addKeyword(keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
        }
    }

    @Override
    public void receiveEditCharacters() {
        // 向basemod注册人物
        BaseMod.addCharacter(new Mathematician(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, MATHEMATICIAN);
    }

    public void receiveEditStrings() {
        String lang;
        if (Settings.language == GameLanguage.ZHS) {
            lang = "ZHS";
        } else {
            lang = "ENG";
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "MathResources/localization/" + lang + "/cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "MathResources/localization/" + lang + "/characters.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "MathResources/localization/" + lang + "/relics.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "MathResources/localization/" + lang + "/powers.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "MathResources/localization/" + lang + "/ui.json");
    }
}
