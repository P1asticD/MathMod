package mathmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mathmod.helpers.ModHelper;
import mathmod.powers.TwentyFourPower;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;

public class TwentyFour extends Math_CustomCard{
    public static final String ID = ModHelper.makePath(TwentyFour.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/TwentyFour.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TwentyFour(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 24;
        this.SMG = this.baseSMG = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new TwentyFourPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(1);
        }
    }

    public AbstractCard makeCopy() {
        return new TwentyFour();
    }
}
