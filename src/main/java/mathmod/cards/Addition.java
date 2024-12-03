package mathmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mathmod.helpers.ModHelper;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;

public class Addition extends Math_CustomCard{
    public static final String ID = ModHelper.makePath(Addition.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/Addition.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardColor COLOR = MATH_CARD;
    private static final AbstractCard.CardRarity RARITY = CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;

    public Addition(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 0;
        this.SMG = this.baseSMG = 0;
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c instanceof Math_CustomCard){
            this.baseBlock += ((Math_CustomCard) c).baseSMG;
            this.block = this.baseBlock;
            initializeDescription();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new GainBlockAction(p, p, this.block));
        this.baseBlock = 0;
        this.block = this.baseBlock;
        initializeDescription();
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Addition();
    }
}
