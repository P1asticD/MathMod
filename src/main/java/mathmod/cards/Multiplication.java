package mathmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mathmod.helpers.ModHelper;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;

public class Multiplication extends Math_CustomCard{
    public static final String ID = ModHelper.makePath(Multiplication.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/Multiplication.png";
    private static final int COST = 3;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    private static final AbstractCard.CardColor COLOR = MATH_CARD;
    private static final AbstractCard.CardRarity RARITY = CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;

    public Multiplication(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 0;
        this.SMG = this.baseSMG = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int index = AbstractDungeon.player.hand.group.indexOf(this);
        if(index != 0 && index != AbstractDungeon.player.hand.size() - 1){
            if (AbstractDungeon.player.hand.group.get(index - 1) instanceof Math_CustomCard && AbstractDungeon.player.hand.group.get(index + 1) instanceof Math_CustomCard) {
                this.baseDamage = ((Math_CustomCard) AbstractDungeon.player.hand.group.get(index - 1)).baseSMG * ((Math_CustomCard) AbstractDungeon.player.hand.group.get(index + 1)).baseSMG;
                this.damage = this.baseDamage;
            }
            else
                this.damage = this.baseDamage = 0;
        }
        else
            this.damage = this.baseDamage = 0;
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    public void applyPowers() {
        super.applyPowers();
        int index = AbstractDungeon.player.hand.group.indexOf(this);
        if(index != 0 && index != AbstractDungeon.player.hand.size() - 1){
            if (AbstractDungeon.player.hand.group.get(index - 1) instanceof Math_CustomCard && AbstractDungeon.player.hand.group.get(index + 1) instanceof Math_CustomCard) {
                this.baseDamage = ((Math_CustomCard) AbstractDungeon.player.hand.group.get(index - 1)).baseSMG * ((Math_CustomCard) AbstractDungeon.player.hand.group.get(index + 1)).baseSMG;
                this.damage = this.baseDamage;
            }
            else
                this.damage = this.baseDamage = 0;
        }
        else
            this.damage = this.baseDamage = 0;
        this.rawDescription = CARD_STRINGS.DESCRIPTION + CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.baseDamage = 0;
        this.damage = this.baseDamage;
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int index = AbstractDungeon.player.hand.group.indexOf(this);
        if(index != 0 && index != AbstractDungeon.player.hand.size() - 1){
            if (AbstractDungeon.player.hand.group.get(index - 1) instanceof Math_CustomCard && AbstractDungeon.player.hand.group.get(index + 1) instanceof Math_CustomCard) {
                this.baseDamage = ((Math_CustomCard) AbstractDungeon.player.hand.group.get(index - 1)).baseSMG * ((Math_CustomCard) AbstractDungeon.player.hand.group.get(index + 1)).baseSMG;
                this.damage = this.baseDamage;
            }
            else
                this.damage = this.baseDamage = 0;
        }
        else
            this.damage = this.baseDamage = 0;
        this.rawDescription = CARD_STRINGS.DESCRIPTION + CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    public AbstractCard makeCopy() {
        return new Multiplication();
    }
}
