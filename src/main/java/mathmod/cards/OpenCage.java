package mathmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mathmod.actions.UnPackAction;
import mathmod.helpers.ModHelper;

import java.util.ArrayList;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;
import static mathmod.modcore.MathMod.ASSIGNED;

public class OpenCage extends Math_CustomCard{
    public static final String ID = ModHelper.makePath(OpenCage.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/OpenCage.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    public ArrayList<AbstractCard> PackGroup;

    public OpenCage(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.SMG = this.baseSMG = 0;
        this.selfRetain = true;
        this.exhaust = true;
        this.PackGroup = new ArrayList<>();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new UnPackAction(this));
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    public AbstractCard makeCopy() {
        return new OpenCage();
    }
}
