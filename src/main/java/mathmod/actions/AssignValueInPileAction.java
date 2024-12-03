package mathmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import mathmod.cards.Math_CustomCard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static mathmod.modcore.MathMod.ASSIGNED;

public class AssignValueInPileAction extends AbstractGameAction {
    int MinValue;
    int ValueRange;
    java.util.ArrayList<AbstractCard> g;
    public AssignValueInPileAction(int MinValue, int ValueRange, java.util.ArrayList<AbstractCard> g){
        this.actionType = ActionType.SPECIAL;
        this.g = g;
        this.MinValue = MinValue;
        this.ValueRange = ValueRange;
        this.duration = 0.1F;
    }

    public void update(){
        if (this.duration == 0.1F) {
            for(AbstractCard c: g) {
                if (c instanceof Math_CustomCard) {
                    if (c.hasTag(ASSIGNED)) {
                        ((Math_CustomCard) c).baseSMG = MinValue + cardRandomRng.random(ValueRange);
                        ((Math_CustomCard) c).SMG = ((Math_CustomCard) c).baseSMG;
                        ((Math_CustomCard) c).calculateValue();
                        CardStrings C_STRINGS = CardCrawlGame.languagePack.getCardStrings(c.cardID);
                        c.rawDescription = C_STRINGS.DESCRIPTION + C_STRINGS.EXTENDED_DESCRIPTION[0];
                        c.initializeDescription();
                    }
                }
            }
        }
        tickDuration();
    }
}
