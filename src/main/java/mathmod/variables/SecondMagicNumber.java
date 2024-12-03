package mathmod.variables;

import mathmod.cards.Math_CustomCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SecondMagicNumber extends DynamicVariable{
    public String key() {
        return "M2";
    }

    public boolean isModified(AbstractCard card) {
        if (card instanceof Math_CustomCard)
            return ((Math_CustomCard)card).isSMGModified;
        return false;
    }

    public int value(AbstractCard card) {
        if (card instanceof Math_CustomCard)
            return ((Math_CustomCard)card).SMG;
        return -1;
    }

    public int baseValue(AbstractCard card) {
        if (card instanceof Math_CustomCard)
            return ((Math_CustomCard)card).baseSMG;
        return -1;
    }

    public boolean upgraded(AbstractCard card) {
        if (card instanceof Math_CustomCard)
            return ((Math_CustomCard)card).upgradedSMG;
        return false;
    }
}
