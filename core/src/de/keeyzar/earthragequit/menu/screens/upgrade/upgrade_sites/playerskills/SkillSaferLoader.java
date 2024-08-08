package de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import de.keeyzar.earthragequit.menu.screens.upgrade.upgrade_sites.playerskills.allskills.PlayerSkills;

/**
 * @author = Keeyzar on 22.02.2016
 */
public class SkillSaferLoader {
    SkillsVerwalter skillsVerwalter;
    private final String PREFS = "SKILLS";
    public SkillSaferLoader(SkillsVerwalter skillsVerwalter) {
        this.skillsVerwalter = skillsVerwalter;
    }

    public void safe() {
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        Array<PlayerSkills> playerSkillsArray = skillsVerwalter.getPlayerSkillsArray();
        for(PlayerSkills playerSkills : playerSkillsArray){
            prefs.putInteger(playerSkills.getIdentifier_name(), playerSkills.getCurrentLevel());
            prefs.putBoolean(playerSkills.getIdentifier_name() + "_lock", playerSkills.isLocked());
        }

        for(int i = 1; i<5; i++){
            prefs.putBoolean("skill_" + i + "_lock", skillsVerwalter.lockedStatus.get(i));
            if(skillsVerwalter.act_PlayerskillsMap.get(i) != null){
                prefs.putString("skill_act_" + i, skillsVerwalter.act_PlayerskillsMap.get(i).getIdentifier_name());
            }
        }
        prefs.flush();
    }

    public void load(){
        Preferences prefs = Gdx.app.getPreferences(PREFS);
        Array<PlayerSkills> playerSkillsArray = skillsVerwalter.getPlayerSkillsArray();
        for(PlayerSkills playerSkills : playerSkillsArray){
            int i = prefs.getInteger(playerSkills.getIdentifier_name(), 0);
            playerSkills.setCurrentLevel(i);
            playerSkills.setLocked(prefs.getBoolean(playerSkills.getIdentifier_name()+ "_lock", true));
        }

        for(int i = 1; i<5; i++){
            skillsVerwalter.lockedStatus.put(i, prefs.getBoolean("skill_" + i + "_lock", true));
        }

        for(int i = 1; i<5; i++){
            String name = prefs.getString("skill_act_" + i, "");
            if(!name.equals("")){
                skillsVerwalter.setPlayerSkills(i, name);
            } else {
                skillsVerwalter.setPlayerSkills(i, null);
            }
        }
    }

    public void reset() {
        Preferences preferences = Gdx.app.getPreferences(PREFS);
        preferences.clear();
        preferences.flush();
    }
}
