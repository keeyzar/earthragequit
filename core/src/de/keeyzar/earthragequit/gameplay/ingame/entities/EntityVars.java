package de.keeyzar.earthragequit.gameplay.ingame.entities;

/**
 * @author = Keeyzar on 02.03.2016
 */
public class EntityVars {
    public static final int PLATFORM = 1;
    public static final int PLAYER = 2;
    public static final int GROUND = 3;
    public static final int WASP = 4;
    public static final int IONIZED_CLOUD = 5;
    public static final int BOSSWASP = 6;
    public static final int COIN = 7;
    public static final int ARROW = 8;
    public static final int FUEL = 9;
    public static final int SCHROTT = 10;
    public static final int SCHROTT_2 = 11;
    public static final int RING_DESTROYER = 13;
    public static final int INVISIBLE_CREEP = 14;
    public static final int KEY_ENTITY = 15;
    public static final int LOCKED_WALL = 16;
    public static final int LOCKED_CIRCLE = 17;
    public static final int FOLLOW_PLAYER = 18;
    public static final int MONEYBAG = 19;
    public static final int TREASURE_CHEST = 20;


    //boss 3 relevant
    public static final int TRIGGER_BUTTON = 21;
    public static final int B3_ARROW = 22;
    public static final int TRIGGER_FIRE_1 = 23;
    public static final int TRIGGER_FIRE_2 = 24;
    public static final int TRIGGER_FIRE_3 = 25;
    public static final int BULLSEYE = 26;
    public static final int ROCKET_FIREABLE = 27;
    public static final int KEY_HOLDER_STONE = 28;
    public static final int WALL_WITH_HOLE = 29;
    public static final int BOSS_LOCKED_CIRCLE = 30;

    //mostly used for radar
    public static final short GROUP_GOOD = 0x0001;
    public static final short GROUP_BAD = 0x0002;
    public static final short GROUP_NEUTRAL = 0x0004;


    public static final short GROUP_SPECIAL = 0x0008;
    public static final short CATEGORY_PLAYER = 0x0001;
    public static final short CATEGORY_ENTITY = 0x0002;
    public static final short CATEGORY_SCENERY = 0x0004;
    public static final short CATEGORY_GROUND = 0x0016;
    public static final short CATEGORY_SHOOTING = 0x0256;
    public final static short MASK_PLAYER = CATEGORY_ENTITY | CATEGORY_SCENERY | CATEGORY_GROUND;
    public final static short MASK_ENTITY = CATEGORY_PLAYER | CATEGORY_SCENERY | CATEGORY_GROUND | CATEGORY_SHOOTING;
    public final static short MASK_SCENERY = CATEGORY_ENTITY | CATEGORY_PLAYER | CATEGORY_GROUND;
    public final static short MASK_SHOOTING = CATEGORY_ENTITY;

    public final static short MASK_GROUND = -1;

    //KeyVars
    public final static int KEY_LEV_ONE = 1;
    public final static int KEY_LEV_TWO = 2;
}
