package mathmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mathmod.helpers.ModHelper;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;
import static mathmod.modcore.MathMod.ASSIGNED;

public class BinIronWave extends Math_CustomCard {
    public static final String ID = ModHelper.makePath(BinIronWave.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/BinIronWave.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public BinIronWave(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 0;
        this.block = this.baseBlock = 0;
        this.SMG = this.baseSMG = 0;
        this.magicNumber = this.baseMagicNumber = 4;
        this.tags.add(ASSIGNED);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.block > 0) {
            addToBot((AbstractGameAction) new GainBlockAction((AbstractCreature) p, (AbstractCreature) p, this.block));
            addToBot((AbstractGameAction) new WaitAction(0.1F));
        }
        if (p != null && m != null)
            addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    public void applyPowers() {
        super.applyPowers();
        String bin = Integer.toBinaryString(baseSMG);
        int num0 = 0, num1 = 0;
        for(int i = 0; i < bin.length(); i++){
            if(bin.charAt(i) == '0') num0++;
            else num1++;
        }
        this.damage = this.baseDamage = this.baseMagicNumber * num1;
        this.block = this.baseBlock = this.baseMagicNumber * num0;
        this.rawDescription = CARD_STRINGS.DESCRIPTION + ":" + bin + CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        calculateValue();
        initializeDescription();
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new BinIronWave();
    }
}

