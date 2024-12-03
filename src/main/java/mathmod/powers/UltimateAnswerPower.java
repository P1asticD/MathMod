package mathmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mathmod.cards.Math_CustomCard;
import mathmod.helpers.ModHelper;

public class UltimateAnswerPower extends AbstractPower{
    public static final String POWER_ID = ModHelper.makePath("UltimateAnswerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int SumV;

    public UltimateAnswerPower(AbstractCreature owner, int SumV){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = -1;
        this.SumV = SumV;
        String path128 = "MathResources/img/powers/DiceRule84.png";
        String path48 = "MathResources/img/powers/DiceRule32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        int s = 0;
        if(!AbstractDungeon.player.stance.ID.equals("Divinity")) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof Math_CustomCard) {
                    s += ((Math_CustomCard) c).baseSMG;
                    if (s == 42)
                        addToBot((AbstractGameAction) new ChangeStanceAction("Divinity"));
                }
            }
        }
        SumV = s;
        this.updateDescription();
    }

    public void onCardDraw(AbstractCard card) {
        int s = 0;
        if(!AbstractDungeon.player.stance.ID.equals("Divinity")) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof Math_CustomCard) {
                    s += ((Math_CustomCard) c).baseSMG;
                    if (s == 42)
                        addToBot((AbstractGameAction) new ChangeStanceAction("Divinity"));
                }
            }
        }
        SumV = s;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + SumV + DESCRIPTIONS[2];
    }
}
