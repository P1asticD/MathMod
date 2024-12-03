package mathmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import mathmod.cards.Math_CustomCard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static mathmod.modcore.MathMod.ASSIGNED;

public class AssignValueSpecialCardAction extends AbstractGameAction {
    AbstractCard c;
    int MinValue;
    int ValueRange;
    boolean ModifyCost;
    public AssignValueSpecialCardAction(int MinValue, int ValueRange, AbstractCard c){
        this.actionType = ActionType.SPECIAL;
        this.c = c;
        this.MinValue = MinValue;
        this.ValueRange = ValueRange;
        this.duration = 0.1F;
    }

    public AssignValueSpecialCardAction(int MinValue, int ValueRange, AbstractCard c, boolean ModifyCost){
        this.actionType = ActionType.SPECIAL;
        this.c = c;
        this.MinValue = MinValue;
        this.ValueRange = ValueRange;
        this.duration = 0.1F;
        this.ModifyCost = ModifyCost;
    }

    public void update(){
        if (this.duration == 0.1F) {
            if (c instanceof Math_CustomCard) {
                if (c.hasTag(ASSIGNED)) {
                    ((Math_CustomCard) c).baseSMG = MinValue + cardRandomRng.random(ValueRange);
                    ((Math_CustomCard) c).SMG = ((Math_CustomCard) c).baseSMG;
                    ((Math_CustomCard) c).calculateValue();
                    CardStrings C_STRINGS = CardCrawlGame.languagePack.getCardStrings(c.cardID);
                    c.rawDescription = C_STRINGS.DESCRIPTION + C_STRINGS.EXTENDED_DESCRIPTION[0];
                    c.initializeDescription();
                }
                if(ModifyCost)
                    c.updateCost(-((Math_CustomCard) c).baseSMG);
            }
        }
        tickDuration();
    }
}
