package mathmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mathmod.actions.WheatAction;
import mathmod.helpers.ModHelper;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;
import static mathmod.modcore.MathMod.ASSIGNED;

public class ChessboardWheat extends Math_CustomCard {
    public static final String ID = ModHelper.makePath(ChessboardWheat.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/ChessboardWheat.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int count = 0;

    public ChessboardWheat(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 1;
        this.magicNumber = this.baseMagicNumber = 3;
        this.SMG = this.baseSMG = 0;
        this.tags.add(ASSIGNED);
    }

    public void atTurnStart(){
        count = 0;
        this.exhaust = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot((AbstractGameAction)new WheatAction(this, magicNumber, count));
        count++;
    }

    public void applyPowers() {
        super.applyPowers();
        if(count == magicNumber - 1)
            this.exhaust = true;
        calculateValue();
        if(count != 0)
            this.rawDescription = CARD_STRINGS.DESCRIPTION + CARD_STRINGS.EXTENDED_DESCRIPTION[0] + CARD_STRINGS.EXTENDED_DESCRIPTION[1] + count + CARD_STRINGS.EXTENDED_DESCRIPTION[2];
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = CARD_STRINGS.DESCRIPTION + CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new ChessboardWheat();
    }
}
