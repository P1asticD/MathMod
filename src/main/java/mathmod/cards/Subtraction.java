package mathmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mathmod.helpers.ModHelper;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;

public class Subtraction extends Math_CustomCard {
    public static final String ID = ModHelper.makePath(Subtraction.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/Subtraction.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    private static final AbstractCard.CardColor COLOR = MATH_CARD;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;

    public Subtraction(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 15;
        this.SMG = this.baseSMG = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(10);
        }
    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c instanceof Math_CustomCard){
            this.baseDamage -= ((Math_CustomCard) c).baseSMG;
            if (this.baseDamage <= 0){
                this.baseDamage = 0;
                if(AbstractDungeon.player.hand.contains(this))
                    addToTop((AbstractGameAction)new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
                if(AbstractDungeon.player.drawPile.contains(this))
                    addToTop((AbstractGameAction)new ExhaustSpecificCardAction(this, AbstractDungeon.player.drawPile));
                if(AbstractDungeon.player.discardPile.contains(this))
                    addToTop((AbstractGameAction)new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
            }
            this.damage = this.baseDamage;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Subtraction();
    }
}
