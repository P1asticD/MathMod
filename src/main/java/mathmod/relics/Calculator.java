package mathmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mathmod.cards.Math_CustomCard;

import static mathmod.modcore.MathMod.ASSIGNED;

public class Calculator extends CustomRelic {
    public static final String ID = "MathMod:Calculator";
    private static final String IMG_PATH = "MathResources/img/relics/Calculator.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;
    private static int bl = 0;

    public Calculator(){
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public void onPlayerEndTurn(){
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if (c instanceof Math_CustomCard){
                if (c.hasTag(ASSIGNED)){
                    if(((Math_CustomCard) c).baseSMG > bl){
                        bl = ((Math_CustomCard) c).baseSMG;
                    }
                }
            }
        }
        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, bl));
        bl = 0;
    }

    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.hasRelic("MathMod:Dices")) {
            player.relics.stream()
                    .filter(r -> r instanceof Dices)
                    .findFirst()
                    .map(r -> Integer.valueOf(player.relics.indexOf(r)))
                    .ifPresent(index -> instantObtain(player, index.intValue(), true));
            flash();
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Calculator();
    }
}
