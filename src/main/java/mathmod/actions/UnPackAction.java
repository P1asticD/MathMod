package mathmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mathmod.cards.OpenCage;

public class UnPackAction extends AbstractGameAction {

    private AbstractPlayer p;
    private OpenCage oc;

    public UnPackAction(OpenCage oc) {
        this.p = AbstractDungeon.player;
        this.oc = oc;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        int cardNum = p.hand.size() + oc.PackGroup.size();
        if(cardNum <= 10){
            for(AbstractCard c:oc.PackGroup){
                AbstractDungeon.player.hand.addToTop(c);
            }
        }
        else{
            int dis = cardNum - 10;
            for(int i = 0; i < dis; i++){
                AbstractDungeon.player.hand.addToTop(oc.PackGroup.get(i));
            }
            for(int i = dis; i < oc.PackGroup.size(); i++){
                this.p.drawPile.moveToDiscardPile(oc.PackGroup.get(i));
            }
        }
        this.isDone = true;
        tickDuration();
    }
}
