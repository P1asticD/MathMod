package mathmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import mathmod.cards.Math_CustomCard;

import java.util.ArrayList;

import static mathmod.modcore.MathMod.ASSIGNED;

public class UpValueAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("UpValueAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private boolean upgraded;
    private ArrayList<AbstractCard> cannotAssign = new ArrayList<>();
    private int addedValue;

    public UpValueAction(boolean isUpgraded, int value){
        this.actionType = ActionType.SPECIAL;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = isUpgraded;
        this.addedValue = value;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.upgraded) {
                for (AbstractCard c : this.p.hand.group) {
                    if (c.hasTag(ASSIGNED)) {
                        ((Math_CustomCard) c).UpAssign(addedValue);
                        c.superFlash();
                        c.applyPowers();
                    }
                }
                this.isDone = true;
                return;
            }

            for (AbstractCard c : this.p.hand.group) {
                if (!c.hasTag(ASSIGNED))
                    this.cannotAssign.add(c);
            }
            if (this.cannotAssign.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotAssign.size() == 1)
                for (AbstractCard c : this.p.hand.group) {
                    if (c.hasTag(ASSIGNED)) {
                        ((Math_CustomCard) c).UpAssign(addedValue);
                        c.superFlash();
                        c.applyPowers();
                        this.isDone = true;
                        return;
                    }
                }
            this.p.hand.group.removeAll(this.cannotAssign);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                if(this.p.hand.getTopCard().hasTag(ASSIGNED)) {
                    ((Math_CustomCard) this.p.hand.getTopCard()).UpAssign(addedValue);
                    this.p.hand.getTopCard().superFlash();
                    returnCards();
                    this.isDone = true;
                }
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if(c.hasTag(ASSIGNED)) {
                    ((Math_CustomCard) c).UpAssign(addedValue);
                    c.superFlash();
                    c.applyPowers();
                    this.p.hand.addToTop(c);
                }
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotAssign)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}
