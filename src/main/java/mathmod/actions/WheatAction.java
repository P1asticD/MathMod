package mathmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WheatAction extends AbstractGameAction {
    private AbstractCard card;
    private int maxTime;
    private int count;

    public WheatAction(AbstractCard card, int maxTime, int count) {
        this.card = card;
        this.maxTime = maxTime;
        this.count = count;
    }

    public void update() {
        this.card.baseDamage *= 2;
        this.card.damage = this.card.baseDamage;
        this.card.applyPowers();
        if(count == maxTime){
            addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
        }
        this.isDone = true;
    }
}