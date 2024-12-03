package mathmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import mathmod.cards.Math_CustomCard;

public class CommutativeAction extends AbstractGameAction {
    Math_CustomCard c;
    public CommutativeAction(Math_CustomCard c){
        this.actionType = ActionType.SPECIAL;
        this.c = c;
        this.duration = 0.1F;
    }

    public void update(){
        if (this.duration == 0.1F) {
            int tmp = c.baseMagicNumber;
            this.c.baseMagicNumber = c.baseSMG;
            this.c.magicNumber = c.baseMagicNumber;
            c.baseSMG = tmp;
            this.c.SMG = c.baseSMG;
            c.initializeDescription();
            c.calculateValue();
        }
        tickDuration();
    }
}
