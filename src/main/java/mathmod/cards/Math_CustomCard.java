package mathmod.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;

public abstract class Math_CustomCard extends CustomCard {
    public int SMG;
    public int baseSMG;
    public boolean upgradedSMG;
    public boolean isSMGModified;
    private final CardStrings cardStrings;
    public String FLAVOR_TEXT;
    public String LABEL_TEXT;
    public int AssignedValue;
    public static final Texture value_orb = ImageMaster.loadImage("MathResources/img/512/card_value.png");
    public BitmapFont DiceFont;

    public static final TextureAtlas.AtlasRegion anima_atlas = new TextureAtlas.AtlasRegion(value_orb, 0, 0, 512, 512);

    public Math_CustomCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.isSMGModified = false;
        try {
            this.FLAVOR_TEXT = cardStrings.EXTENDED_DESCRIPTION[0];
        } catch (NullPointerException e) {
            this.FLAVOR_TEXT = "";
        }
        try {
            this.LABEL_TEXT = cardStrings.EXTENDED_DESCRIPTION[0];
        } catch (NullPointerException e) {
            this.LABEL_TEXT = "";
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("MathResources/img/fonts/seguisym.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.WHITE;
        parameter.characters = "0⚀⚁⚂⚃⚄⚅123456789";
        this.DiceFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            String UPGRADE_DESCRIPTION = null;
            try {
                UPGRADE_DESCRIPTION = this.cardStrings.UPGRADE_DESCRIPTION;
            } catch (NullPointerException nullPointerException) {}
            if (UPGRADE_DESCRIPTION != null) {
                this.rawDescription = UPGRADE_DESCRIPTION;
                initializeDescription();
            }
            try {
                if (this.cardStrings.EXTENDED_DESCRIPTION.length >= 2)
                    this.FLAVOR_TEXT = this.cardStrings.EXTENDED_DESCRIPTION[1];
            } catch (NullPointerException e) {
                this.FLAVOR_TEXT = "";
            }
        }
    }

    public void calculateValue() {
        this.AssignedValue = this.baseSMG;
    }

    public void UpAssign(int amt){
        if(AbstractDungeon.player != null){
            if(AbstractDungeon.player.hasPower("MathMod:DiceRulePower")) {
                this.baseSMG = (this.baseSMG + amt) % 6;
                if(this.baseSMG < 0)
                    this.baseSMG += 6;
            }
            else {
                this.baseSMG = (this.baseSMG + amt) % 10;
                if(this.baseSMG < 0)
                    baseSMG += 10;
            }
            this.SMG = this.baseSMG;
        }
        applyPowers();
    }

    public void renderHelper(SpriteBatch sb, float x, float y, TextureAtlas.AtlasRegion img) {
        if (img != null) {
            sb.setColor(Color.WHITE);
            sb.draw((TextureRegion)img, x + img.offsetX - img.originalWidth / 2.0F, y + img.offsetY - img.originalHeight / 2.0F, img.originalWidth / 2.0F - img.offsetX, img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle);
        }
    }

    public void renderValue(SpriteBatch sb) {
        if (!this.isLocked && this.isSeen) {
            renderHelper(sb, this.current_x, this.current_y, anima_atlas);
            Color costColor = Color.CYAN.cpy();

            costColor.a = this.transparency;
            String text = Integer.toString(this.AssignedValue);

            FontHelper.cardEnergyFont_L.getData().setScale(this.drawScale);
            BitmapFont font = FontHelper.cardEnergyFont_L;

            if(AbstractDungeon.player != null && AbstractDungeon.player.hasPower("MathMod:DiceRulePower")){
                switch (this.AssignedValue){
                    case 1: text = "⚀"; break;
                    case 2: text = "⚁"; break;
                    case 3: text = "⚂"; break;
                    case 4: text = "⚃"; break;
                    case 5: text = "⚄"; break;
                    case 6: text = "⚅"; break;
                    default: text = Integer.toString(this.AssignedValue);
                }
                font = DiceFont;
            }

            FontHelper.renderRotatedText(sb, font, text, this.current_x, this.current_y, 132.0F * this.drawScale * Settings.scale, 192.0F * this.drawScale * Settings.scale, this.angle, false, costColor);
        }
    }
}
