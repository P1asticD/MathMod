package mathmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mathmod.helpers.ModHelper;

public class BetPower extends AbstractPower{
    public static final String POWER_ID = ModHelper.makePath("BetPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BetPower(AbstractCreature owner, int amount){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.amount = amount;
        String path128 = "MathResources/img/powers/Settle84.png";
        String path48 = "MathResources/img/powers/Settle32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void onDeath(){
        int count = 0;
        for (AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            if (!m.isDeadOrEscaped())
                count++;
        }

        if(count == 0){
            AbstractDungeon.player.gainGold(10*amount);
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + 10*amount + DESCRIPTIONS[1];
    }
}