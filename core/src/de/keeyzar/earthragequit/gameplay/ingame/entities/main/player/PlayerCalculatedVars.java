package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player;

import de.keeyzar.earthragequit.GlobalPlayerInformation;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.SkillsVerwalter;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.SkillsVars;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.StatsVerwalter;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerstats.allstats.STAT_VARS;

/**
 * Kümmert sich um das Berechnen der aktuellen Stats, mithilfe des Statsverwalter
 * Heißt, das Schiff hat standartmäßig 1 Leben, hat der Spieler aber schon
 * drei upgrades, dann hat er 4 leben.
 * @author = Keeyzar on 29.02.2016
 */
public class PlayerCalculatedVars {
    private Player player;
    private final StatsVerwalter statsVerwalter;
    private SkillsVerwalter skillsVerwalter;
    private GlobalPlayerInformation gpi;

    // LEFT // RIGHT
    private final float rotatingSpeed_standart = 2f;

    //UP
    private final float upwards_standart_max = 6; // 4
    private final float upwards_standart_power = 3; //3
    private final float upwards_standart_fuel = 10; //10

    //life
    private final float life_standart = 1;


    //if sidespeed
    private float sideSpeed_standart = 1;
    private float sideSpeed;
    private float sideSpeedMax_standart = 6f;
    private float sideSpeedMax;

    //if rotation
    private float rotatingSpeed;

    //u = upwards
    private float u_speed_max;
    private float u_speed_power;
    private float u_speed_fuel;
    private float u_speed_fuel_max;


    //life
    private float life;
    private float lifeMax;


    //fuel
    private float fuelTankValue;

    //Platform
    private float platform_impulse_standart = 15;
    private float platform_impulse;

    //Starting boost;
    private float starting_boost_standart = 30;
    private float starting_boost;

    //if any level wants to fix player stats
    private float fixFuel = -1;
    private float fixSpeed = -1;
    private float fixRotation = -1;
    private float fixMaxSpeed = -1;
    private float fixLife = -1;

    //skills relevant
    private float megaboostTime = 0;
    private float megaboostMultiplikator = 0;
    private float radarSize; // 10

    public PlayerCalculatedVars(Player player, StatsVerwalter statsVerwalter, SkillsVerwalter skillsVerwalter, GlobalPlayerInformation gpi){
        this.player = player;
        this.statsVerwalter = statsVerwalter;
        this.skillsVerwalter = skillsVerwalter;
        this.gpi = gpi;

        initStuff();
    }

    private void initStuff() {

        rotatingSpeed = rotatingSpeed_standart;
        //u_speed init;
        u_speed_max =upwards_standart_max + statsVerwalter.getStatByName(STAT_VARS.STAT_ROCKET_MAXIMUM_SPEED).getBoost();
        u_speed_power = upwards_standart_power + statsVerwalter.getStatByName(STAT_VARS.STAT_ROCKET_PROPULSION).getBoost();
        u_speed_fuel = upwards_standart_fuel + statsVerwalter.getStatByName(STAT_VARS.STAT_FUEL).getBoost();
        u_speed_fuel_max = u_speed_fuel;


        //life init;
        life = life_standart + statsVerwalter.getStatByName(STAT_VARS.STAT_PLATING).getBoost();
        lifeMax = life;


        //platform init;
        platform_impulse = platform_impulse_standart + statsVerwalter.getStatByName(STAT_VARS.STAT_PLATFORM_JUMP_HEIGHT).getBoost();

        //starting boost
        starting_boost = starting_boost_standart;

        sideSpeedMax = sideSpeedMax_standart;
        sideSpeed = sideSpeed_standart;

        //skills
        megaboostTime = skillsVerwalter.getSkillByName(SkillsVars.MEGABOOST).getBoostForCurrentLevel();
        megaboostMultiplikator = gpi.getMegaBoostMultiplikator();

        fuelTankValue = 5;

        radarSize = skillsVerwalter.getSkillByName(SkillsVars.RADAR).getBoostForCurrentLevel();

    }

    public void setFixSpeed(float fixSpeed){
        this.fixSpeed = fixSpeed;
    }

    public void setFixFuel(float fixFuel){
        this.fixFuel = fixFuel;
    }

    public void setFixRotation(float fixRotation){
        this.fixRotation = fixRotation;
    }

    public void setFixMaxSpeed(float fixMaxSpeed) {
        this.fixMaxSpeed = fixMaxSpeed;
    }


    public void addU_fuel(float value){
        u_speed_fuel += value;
    }

    public void addLife(float value){
        if(value < 0){
            player.playerHit();
        }
        life += value;
    }

    public float getU_speed_max() {
        if(fixMaxSpeed != -1){
            return fixMaxSpeed;
        }
        return u_speed_max;
    }

    public void setU_speed_max(float u_speed_max) {
        this.u_speed_max = u_speed_max;
    }

    public float getU_speed_power() {
        if(fixSpeed != -1){
            return fixSpeed;
        }
        return u_speed_power;
    }

    public void setU_speed_power(float u_speed_power) {
        this.u_speed_power = u_speed_power;
    }

    public float getU_speed_fuel() {
        if(fixFuel != -1){
            return fixFuel;
        }
        return u_speed_fuel;
    }

    public void setU_speed_fuel(float u_speed_fuel) {
        this.u_speed_fuel = u_speed_fuel;
    }

    public float getLife() {
        if(fixLife != -1){
            return fixLife;
        }
        return life;
    }

    public void setLife(float life) {
        this.life = life;
    }

    public float getU_speed_fuel_max() {
        if(fixFuel != -1){
            return fixFuel;
        }
        return u_speed_fuel_max;
    }

    public float getRotatingSpeed() {
        if(fixRotation != -1){
            return fixRotation;
        }
        return rotatingSpeed;
    }


    public float getPlatform_impulse() {
        return platform_impulse;
    }

    public float getLifeMax() {
        return lifeMax;
    }

    public float getStarting_boost() {
        return starting_boost;
    }

    public float getSideSpeed() {
        return sideSpeed;
    }

    public float getSideSpeedMax(){
        return sideSpeedMax;
    }

    public void setFixLife(int fixLife) {
        this.fixLife = fixLife;
    }

    public float getMegaboostTime() {
        return megaboostTime;
    }

    public float getMegaboostMultiplikator() {
        return megaboostMultiplikator;
    }

    public float getRadarSize() {
        return radarSize;
    }

    public float getFuelTankValue() {
        return fuelTankValue;
    }

    public void setLifeForLevel(int i){
        life = i;
        lifeMax = i;
    }
}
