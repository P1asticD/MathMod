package mathmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mathmod.cards.Math_CustomCard;

import static mathmod.modcore.MathMod.ASSIGNED;


public class IncreaseDrawAction extends AbstractGameAction {

    boolean draw1;
    public IncreaseDrawAction(boolean draw1){
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
        this.draw1 = draw1;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int handSize = AbstractDungeon.player.hand.group.size();
            if(handSize < 10 && (AbstractDungeon.player.drawPile.group.size() != 0 || AbstractDungeon.player.discardPile.group.size() != 0)) {
                if (draw1 == false) {
                    if (AbstractDungeon.player.hand.group.size() == 0) {
                        addToTop(new DrawCardAction(2));
                        addToBot(new IncreaseDrawAction(true));
                    } else {
                        addToTop(new DrawCardAction(1));
                        addToBot(new IncreaseDrawAction(true));
                    }
                } else {
                    AbstractCard cR = AbstractDungeon.player.hand.group.get(handSize - 1);
                    AbstractCard cL = AbstractDungeon.player.hand.group.get(handSize - 2);
                    int vR = 0, vL = 0;
                    if(cR instanceof Math_CustomCard && cR.hasTag(ASSIGNED))
                        vR = ((Math_CustomCard) cR).baseSMG;
                    if(cL instanceof Math_CustomCard && cL.hasTag(ASSIGNED))
                        vL = ((Math_CustomCard) cL).baseSMG;
                    if (vR >= vL) {
                        addToTop(new DrawCardAction(1));
                        addToBot(new IncreaseDrawAction(true));
                    }
                }
            }
        }

        tickDuration();
    }

}
