package mathmod.relics;

import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mathmod.actions.AssignValueInPileAction;
import mathmod.cards.Math_CustomCard;

import static mathmod.modcore.MathMod.ASSIGNED;

public class RuleBook extends CustomRelic implements OnApplyPowerRelic {
    public static final String ID = "MathMod:RuleBook";
    private static final String IMG_PATH = "MathResources/img/relics/RuleBook.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;
    public static int TurnValue = 0;

    public RuleBook(){
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public void atPreBattle(){
        addToBot(new AssignValueInPileAction(0, 9, AbstractDungeon.player.drawPile.group));
    }

    public void atTurnStart(){
        TurnValue = 0;
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c instanceof Math_CustomCard){
            if (c.hasTag(ASSIGNED)){
                TurnValue += ((Math_CustomCard) c).baseSMG;
            }
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[1] + DESCRIPTIONS[3];
    }

    public boolean onApplyPower(AbstractPower toApply, AbstractCreature target, AbstractCreature source) {
        return true;
    }

    public AbstractRelic makeCopy() {
        return new RuleBook();
    }
}
