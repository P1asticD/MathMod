package mathmod.cards;

import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mathmod.helpers.ModHelper;

import java.util.ArrayList;

import static mathmod.characters.Mathematician.Enums.MATH_CARD;
import static mathmod.modcore.MathMod.ASSIGNED;

public class Homework extends Math_CustomCard implements OnObtainCard, CustomSavable<Integer> {
    public static final String ID = ModHelper.makePath(Homework.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "MathResources/img/cards/Homework.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MATH_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Homework(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 13;
        this.SMG = this.baseSMG = 0;
        this.tags.add(ASSIGNED);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
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
            this.upgradeBlock(5);
        }
    }

    public void onObtainCard(){
        ArrayList<AbstractCard> list = (ArrayList<AbstractCard>) AbstractDungeon.player.masterDeck.group.clone();

        for (AbstractCard c : list) {
            if (c instanceof Homework && c.uuid != this.uuid) {
                AbstractDungeon.player.masterDeck.removeCard(c);
                this.baseBlock += c.baseBlock;
            }
        }
    }

    public Integer onSave() {
        return this.baseBlock;
    }

    @Override
    public void onLoad(Integer b) {
        this.block = this.baseBlock = b;
    }

    public AbstractCard makeCopy() {
        return new Homework();
    }
}
