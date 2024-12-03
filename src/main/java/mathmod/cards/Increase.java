package mathmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mathmod.actions.IncreaseDrawAction;
import mathmod.helpers.ModHelper;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;
import static mathmod.modcore.MathMod.ASSIGNED;

public class Increase extends Math_CustomCard {
    public static final String ID = ModHelper.makePath(Increase.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/Increase.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Increase(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.SMG = this.baseSMG = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseDrawAction(false));
    }

    public void applyPowers() {
        super.applyPowers();
        calculateValue();
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        initializeDescription();
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    public AbstractCard makeCopy() {
        return new Increase();
    }
}
