package mathmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import mathmod.cards.OpenCage;

public class PackAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("PackAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    public PackAction(int amount) {
        this.p = AbstractDungeon.player;
        setValues((AbstractCreature)this.p, (AbstractCreature)AbstractDungeon.player, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            OpenCage OC = new OpenCage();
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.drawPile.group)
                tmp.addToRandomSpot(c);
            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }
            if (tmp.size() <= this.amount) {
                for (int i = 0; i < tmp.size(); i++) {
                    AbstractCard card = tmp.getNCardFromTop(i);
                    OC.PackGroup.add(card);
                    this.p.drawPile.removeCard(card);
                }
                if (this.p.hand.size() == 10) {
                    this.p.drawPile.moveToDiscardPile(OC);
                    this.p.createHandIsFullDialog();
                } else {
                    OC.unhover();
                    OC.lighten(true);
                    OC.setAngle(0.0F);
                    OC.drawScale = 0.12F;
                    OC.targetDrawScale = 0.75F;
                    OC.current_x = CardGroup.DRAW_PILE_X;
                    OC.current_y = CardGroup.DRAW_PILE_Y;
                    AbstractDungeon.player.hand.addToTop(OC);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }
                this.isDone = true;
                return;
            }
            if (this.amount == 2) {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[1], false);
            }
            tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            OpenCage OC = new OpenCage();
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                OC.PackGroup.add(c);
                this.p.drawPile.removeCard(c);
            }
            if (this.p.hand.size() == 10) {
                this.p.drawPile.moveToDiscardPile(OC);
                this.p.createHandIsFullDialog();
            } else {
                this.p.hand.addToTop(OC);
            }
            this.p.hand.refreshHandLayout();
            this.p.hand.applyPowers();
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
        }

        tickDuration();
    }
}
