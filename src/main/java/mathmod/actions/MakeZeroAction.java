package mathmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import mathmod.cards.Math_CustomCard;

import java.util.ArrayList;

import static mathmod.modcore.MathMod.ASSIGNED;

public class MakeZeroAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("UpValueAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private boolean upgraded;
    private ArrayList<AbstractCard> cannotAssign = new ArrayList<>();
    private int addedValue;

    public MakeZeroAction(){
        this.actionType = ActionType.SPECIAL;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (c.hasTag(ASSIGNED)) {
                    ((Math_CustomCard) c).baseSMG = 0;
                    ((Math_CustomCard) c).SMG = ((Math_CustomCard) c).baseSMG;
                    c.applyPowers();
                }
            }
            this.isDone = true;
        }
        tickDuration();
    }
}
