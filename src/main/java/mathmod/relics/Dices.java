package mathmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mathmod.cards.Math_CustomCard;

import static mathmod.modcore.MathMod.ASSIGNED;

public class Dices extends CustomRelic {
    public static final String ID = "MathMod:Dices";
    private static final String IMG_PATH = "MathResources/img/relics/Dices.png";
    private static final RelicTier RELIC_TIER = RelicTier.SPECIAL;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;
    private static int[] Dn = {0, 0, 0, 0, 0, 0};

    public Dices(){
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public void onPlayerEndTurn(){
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if (c instanceof Math_CustomCard){
                if (c.hasTag(ASSIGNED)){
                    Dn[((Math_CustomCard) c).baseSMG - 1]++;
                }
            }
        }
        int mostN = 0, bl = 0;
        for(int i = 0; i < 6; i++){
            if(Dn[i] >= mostN){
                mostN = Dn[i];
                bl = mostN * (i + 1);
            }
        }

        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, bl));
    }

    public void atTurnStart() {
        for(int i = 0; i < 6; i++){
            Dn[i] = 0;
        }
    }

    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        player.relics.stream()
                .filter(r -> r instanceof Calculator)
                .findFirst()
                .map(r -> Integer.valueOf(player.relics.indexOf(r)))
                .ifPresent(index -> instantObtain(player, index.intValue(), true));
        flash();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onVictory(){
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new Calculator());
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(Calculator.ID);
    }

    public AbstractRelic makeCopy() {
        return new Dices();
    }
}
