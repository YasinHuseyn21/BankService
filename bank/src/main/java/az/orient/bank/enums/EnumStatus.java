package az.orient.bank.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumStatus {

    ACTIVE(1), DEACTIVE(0);

    private int value;

    public int getValue()
    {
        return this.value;
    }

}
