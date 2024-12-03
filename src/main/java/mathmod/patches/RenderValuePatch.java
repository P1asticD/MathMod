package mathmod.patches;


import mathmod.cards.Math_CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RenderValuePatch {
    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class renderOrb {
        public static void Prefix(AbstractCard c, SpriteBatch sb) {
            if (c instanceof Math_CustomCard)
                ((Math_CustomCard)c).renderValue(sb);
        }
    }
}
