package builder;

import model.*;

public class MissionBuilder {
    private Mission mission;
    
    public MissionBuilder() {
        this.mission = new Mission();
    }
    
    public MissionBuilder setMissionId(String missionId) {
        mission.setMissionId(missionId);
        return this;
    }
    
    public MissionBuilder setDate(String date) {
        mission.setDate(date);
        return this;
    }
    
    public MissionBuilder setLocation(String location) {
        mission.setLocation(location);
        return this;
    }
    
    public MissionBuilder setOutcome(MissionOutcome outcome) {
        mission.setOutcome(outcome);
        return this;
    }
    
    public MissionBuilder setDamageCost(int damageCost) {
        mission.setDamageCost(damageCost);
        return this;
    }
    
    public MissionBuilder setCurse(String name, String threatLevel) {
        mission.setCurse(new Curse(name, threatLevel));
        return this;
    }
    
    public MissionBuilder addSorcerer(String name, String rank) {
        mission.addSorcerer(new Sorcerer(name, rank));
        return this;
    }
    
    public MissionBuilder addTechnique(String name, String type, String owner, int damage) {
        mission.addTechnique(new Technique(name, type, owner, damage));
        return this;
    }
    
    public MissionBuilder putExtension(String key, Object value) {
        mission.putExtension(key, value);
        return this;
    }
    
    public Mission build() {
        return mission;
    }
}