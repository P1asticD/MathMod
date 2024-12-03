package mathmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import mathmod.actions.AssignValueSpecialCardAction;
import mathmod.helpers.ModHelper;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;
import static mathmod.modcore.MathMod.ASSIGNED;

public class UnbearableWeight extends Math_CustomCard {
    public static final String ID = ModHelper.makePath(UnbearableWeight.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/UnbearableWeight.png";
    private static final int COST = 15;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public UnbearableWeight(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 32;
        this.SMG = this.baseSMG = 0;
        this.tags.add(ASSIGNED);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        addToBot((AbstractGameAction)new WaitAction(0.8F));
        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    public void applyPowers() {
        super.applyPowers();
        calculateValue();
        this.rawDescription = CARD_STRINGS.DESCRIPTION + CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(10);
        }
    }

    public void triggerWhenDrawn() {
        if(AbstractDungeon.player.hasPower("MathMod:DiceRulePower"))
            addToTop(new AssignValueSpecialCardAction(1, 5, this, true));
        else
            addToTop(new AssignValueSpecialCardAction(0, 9, this, true));

    }

    public AbstractCard makeCopy() {
        return new UnbearableWeight();
    }
}
