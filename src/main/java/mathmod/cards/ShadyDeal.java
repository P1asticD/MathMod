package mathmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import mathmod.helpers.ModHelper;
import mathmod.powers.BetPower;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;

public class ShadyDeal extends Math_CustomCard {
    public static final String ID = ModHelper.makePath(ShadyDeal.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/ShadyDeal.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public ShadyDeal(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.SMG = this.baseSMG = 0;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        p.loseGold(10);
        int count = 0;
        boolean MinionExist = false;
        for (AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if (!mo.isDeadOrEscaped())
                count++;
            if (mo.hasPower("Minion"))
                MinionExist = true;
        }
        if(MinionExist){
            for (AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
                if (!mo.hasPower("Minion")){
                    addToBot(new ApplyPowerAction(mo, p, new BetPower(mo, count)));
                    if(upgraded)
                        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new WeakPower((AbstractCreature)m, 99, false), 99));
                }
            }
        }
        else{
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            addToBot(new ApplyPowerAction(randomMonster, p, new BetPower(randomMonster, count)));
            if(upgraded)
                addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)randomMonster, (AbstractCreature)p, (AbstractPower)new WeakPower((AbstractCreature)randomMonster, 99, false), 99));

        }
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    public AbstractCard makeCopy() {
        return new ShadyDeal();
    }
}
