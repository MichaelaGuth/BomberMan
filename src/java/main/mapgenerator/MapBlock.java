package main.mapgenerator;

public enum MapBlock {
    DESTRUCTIBLE_BLOCK('I'), INDESTRUCTIBLE_BLOCK('W'), NONE('_');

    private char sign;

    MapBlock(char sign) {
        this.sign = sign;
    }

    public char getSign() {
        return  this.sign;
    }
}
