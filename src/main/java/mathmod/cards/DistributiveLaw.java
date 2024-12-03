package mathmod.cards;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import mathmod.helpers.ModHelper;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;
import static mathmod.modcore.MathMod.ASSIGNED;

public class DistributiveLaw extends Math_CustomCard {
    public static final String ID = ModHelper.makePath(DistributiveLaw.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/DistributiveLaw.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DistributiveLaw(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 12;
        this.SMG = this.baseSMG = 0;
        this.tags.add(ASSIGNED);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int bits = this.magicNumber;
        if(this.baseSMG >= 5){
            int dmgSum = 0;
            for (AbstractMonster aM : (AbstractDungeon.getMonsters()).monsters) {
                if (!aM.isDeadOrEscaped() && isAttacking(aM) && !aM.hasPower("stslib:Stunned")) {
                    int dmg = ((Integer) ReflectionHacks.getPrivate(aM, AbstractMonster.class, "intentDmg")).intValue();
                    if (((Boolean)ReflectionHacks.getPrivate(aM, AbstractMonster.class, "isMultiDmg")).booleanValue())
                        dmg *= ((Integer)ReflectionHacks.getPrivate(aM, AbstractMonster.class, "intentMultiAmt")).intValue();
                    dmgSum += Math.max(0, dmg);
                }
            }
            dmgSum = Math.max(0, dmgSum - p.currentBlock);
            this.block = Math.min(dmgSum, bits);
        }
        else{
            this.block = AbstractDungeon.cardRandomRng.random(1, bits);
        }
        int Vig = bits - this.block;
        if (this.block > 0)
            addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, this.block));
        if (Vig > 0)
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new VigorPower((AbstractCreature)p, Vig)));
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(4);
        }
    }

    private boolean isAttacking(AbstractMonster m) {
        return (m.getIntentBaseDmg() >= 0);
    }

    public void applyPowers() {
        super.applyPowers();
        calculateValue();
        this.rawDescription = CARD_STRINGS.DESCRIPTION + CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new DistributiveLaw();
    }
}
