package model;

import java.util.HashMap;
import java.util.Map;

public class Mission {
    // Базовые поля
    private String missionId;
    private String date;
    private String location;
    private MissionOutcome outcome;
    private int damageCost;
    private Curse curse;
    private java.util.List<Sorcerer> sorcerers;
    private java.util.List<Technique> techniques;
    
    // Расширяемые блоки
    private Map<String, Object> extensionBlocks;
    
    public Mission() {
        this.sorcerers = new java.util.ArrayList<>();
        this.techniques = new java.util.ArrayList<>();
        this.extensionBlocks = new HashMap<>();
    }
    
    // Геттеры и сеттеры для базовых полей
    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public MissionOutcome getOutcome() { return outcome; }
    public void setOutcome(MissionOutcome outcome) { this.outcome = outcome; }
    
    public int getDamageCost() { return damageCost; }
    public void setDamageCost(int damageCost) { this.damageCost = damageCost; }
    
    public Curse getCurse() { return curse; }
    public void setCurse(Curse curse) { this.curse = curse; }
    
    public java.util.List<Sorcerer> getSorcerers() { return sorcerers; }
    public void setSorcerers(java.util.List<Sorcerer> sorcerers) { this.sorcerers = sorcerers; }
    
    public java.util.List<Technique> getTechniques() { return techniques; }
    public void setTechniques(java.util.List<Technique> techniques) { this.techniques = techniques; }
    
    public void addSorcerer(Sorcerer sorcerer) { this.sorcerers.add(sorcerer); }
    public void addTechnique(Technique technique) { this.techniques.add(technique); }
    
    // Работа с расширяемыми блоками
    public void putExtension(String key, Object value) {
        this.extensionBlocks.put(key, value);
    }
    
    public Object getExtension(String key) {
        return this.extensionBlocks.get(key);
    }
    
    public boolean hasExtension(String key) {
        return this.extensionBlocks.containsKey(key);
    }
    
    public Map<String, Object> getAllExtensions() {
        return new HashMap<>(extensionBlocks);
    }
}