package mathmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mathmod.actions.AssignValueInPileAction;
import mathmod.helpers.ModHelper;
import mathmod.powers.DiceRulePower;
import mathmod.relics.Dices;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;

public class DiceRule extends Math_CustomCard{
    public static final String ID = ModHelper.makePath(DiceRule.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/DiceRule.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DiceRule(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.SMG = this.baseSMG = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new DiceRulePower((AbstractCreature)p)));
        addToBot(new AssignValueInPileAction(1, 5, AbstractDungeon.player.hand.group));
        addToBot(new AssignValueInPileAction(1, 5, AbstractDungeon.player.drawPile.group));
        addToBot(new AssignValueInPileAction(1, 5, AbstractDungeon.player.discardPile.group));
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new Dices());
    }

    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.isInnate = true;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new DiceRule();
    }
}
