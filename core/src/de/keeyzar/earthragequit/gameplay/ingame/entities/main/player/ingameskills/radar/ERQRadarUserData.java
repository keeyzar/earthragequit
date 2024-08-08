package de.keeyzar.earthragequit.gameplay.ingame.entities.main.player.ingameskills.radar;

import com.badlogic.gdx.utils.ObjectMap;
import de.keeyzar.earthragequit.gameplay.ingame.entities.EntityVars;

/**
 * @author = Keeyzar on 24.03.2016
 */
public class ERQRadarUserData    {
    private ObjectMap<Integer, Radarable> goodCurrentContacts;
    private ObjectMap<Integer, Radarable> neutralCurrentContacts;
    private ObjectMap<Integer, Radarable> badCurrentContacts;
    private ObjectMap<Integer, Radarable> specialCurrentContacts;
    int pos = 1;

    public ERQRadarUserData(){
        goodCurrentContacts = new ObjectMap<Integer, Radarable>();
        neutralCurrentContacts = new ObjectMap<Integer, Radarable>();
        badCurrentContacts = new ObjectMap<Integer, Radarable>();
        specialCurrentContacts = new ObjectMap<Integer, Radarable>();
    }

    public void addContact(Radarable radarable){
        if(radarable.getMapId() == -1){
            radarable.setMapId(pos++);
        }
        switch (radarable.getGroup()){
            case EntityVars.GROUP_GOOD:
                goodCurrentContacts.put(radarable.getMapId(), radarable);
                return;
            case EntityVars.GROUP_NEUTRAL:
                neutralCurrentContacts.put(radarable.getMapId(), radarable);
                return;
            case EntityVars.GROUP_BAD:
                badCurrentContacts.put(radarable.getMapId(), radarable);
                return;
            case EntityVars.GROUP_SPECIAL:
                specialCurrentContacts.put(radarable.getMapId(), radarable);
                return;
        }
    }

    public void removeContact(Radarable radarable){
        switch (radarable.getGroup()) {
            case EntityVars.GROUP_GOOD:
                goodCurrentContacts.remove(radarable.getMapId());
                return;
            case EntityVars.GROUP_NEUTRAL:
                neutralCurrentContacts.remove(radarable.getMapId());
                return;
            case EntityVars.GROUP_BAD:
                badCurrentContacts.remove(radarable.getMapId());
                return;
            case EntityVars.GROUP_SPECIAL:
                specialCurrentContacts.remove(radarable.getMapId());
                return;
        }
    }

    public ObjectMap<Integer, Radarable> getGoodCurrentContacts() {
        return goodCurrentContacts;
    }

    public ObjectMap<Integer, Radarable> getNeutralCurrentContacts() {
        return neutralCurrentContacts;
    }

    public ObjectMap<Integer, Radarable> getBadCurrentContacts() {
        return badCurrentContacts;
    }
    public ObjectMap<Integer, Radarable> getSpecialCurrentContacts() {
        return specialCurrentContacts;
    }
}
