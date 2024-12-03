package mathmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mathmod.cards.Math_CustomCard;
import mathmod.helpers.ModHelper;
import mathmod.misc.Calculate24;

import java.util.ArrayList;

import static mathmod.modcore.MathMod.ASSIGNED;

public class TwentyFourPower extends AbstractPower{
    public static final String POWER_ID = ModHelper.makePath("TwentyFourPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int damage;
    private boolean triggered;
    private ArrayList<Integer> nums;

    public TwentyFourPower(AbstractCreature owner, int damage){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.damage = damage;
        this.nums = new ArrayList<Integer>();
        String path128 = "MathResources/img/powers/Settle84.png";
        String path48 = "MathResources/img/powers/Settle32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void atStartOfTurn() {
        triggered = false;
        nums.clear();
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!triggered) {
            if (nums.size() < 3) {
                if (card instanceof Math_CustomCard && card.hasTag(ASSIGNED))
                    nums.add(((Math_CustomCard) card).baseSMG);
                else
                    nums.add(0);
            }
            else if (nums.size() == 3) {
                if (card instanceof Math_CustomCard && card.hasTag(ASSIGNED))
                    nums.add(((Math_CustomCard) card).baseSMG);
                else
                    nums.add(0);
                if (Calculate24.judgePoint24(nums)) {
                    addToBot((AbstractGameAction) new DamageAllEnemiesAction((AbstractCreature) AbstractDungeon.player,

                            DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    triggered = true;
                }
                System.out.println(Calculate24.judgePoint24(nums));
            }
            else if(nums.size() == 4){
                nums.remove(0);
                if (card instanceof Math_CustomCard && card.hasTag(ASSIGNED))
                    nums.add(((Math_CustomCard) card).baseSMG);
                else
                    nums.add(0);
                if (Calculate24.judgePoint24(nums)) {
                    addToBot((AbstractGameAction) new DamageAllEnemiesAction((AbstractCreature) AbstractDungeon.player,

                            DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    triggered = true;
                }
                System.out.println(Calculate24.judgePoint24(nums));
            }
        }

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        if(!triggered) {
            this.description = String.format(DESCRIPTIONS[0], this.damage);
            if (nums.size() != 0) {
                this.description += DESCRIPTIONS[1];
                for (int i = 0; i < nums.size() - 1; i++) {
                    this.description = this.description + nums.get(i) + DESCRIPTIONS[2];
                }
                this.description += nums.get(nums.size() - 1);
            }
        }
        else
            this.description = DESCRIPTIONS[3];
    }
}
