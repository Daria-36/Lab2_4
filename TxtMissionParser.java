package parser;

import model.Mission;
import model.MissionOutcome;
import extension.EnvironmentConditions;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TxtMissionParser extends AbstractParser {
    
    public Mission parse(String content) throws Exception {
        resetBuilder();
        String[] lines = content.split("\n");
        
        String currentSection = "";
        EnvironmentConditions environment = null;
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            if (line.startsWith("[") && line.endsWith("]")) {
                currentSection = line.substring(1, line.length() - 1);
                if (currentSection.equals("ENVIRONMENT")) {
                    environment = new EnvironmentConditions();
                }
                continue;
            }
            
            String[] parts = line.split("=", 2);
            if (parts.length < 2) continue;
            
            String key = parts[0].trim();
            String value = parts[1].trim();
            
            if (currentSection.isEmpty() || currentSection.equals("MISSION")) {
                switch (key) {
                    case "missionId": builder.setMissionId(value); break;
                    case "date": builder.setDate(value); break;
                    case "location": builder.setLocation(value); break;
                    case "outcome": builder.setOutcome(MissionOutcome.fromString(value)); break;
                    case "damageCost": builder.setDamageCost(parseInt(value, 0)); break;
                }
            } else if (currentSection.equals("CURSE")) {
                if (key.equals("name")) {
                    builder.setCurse(value, "");
                } else if (key.equals("threatLevel")) {
                    builder.setCurse(getCurseNameFromBuilder(), value);
                }
            } else if (currentSection.equals("SORCERER")) {
                if (key.equals("name")) {
                    setTempSorcererName(value);
                } else if (key.equals("rank")) {
                    builder.addSorcerer(getTempSorcererName(), value);
                }
            } else if (currentSection.equals("TECHNIQUE")) {
                if (key.equals("name")) setTempTechniqueName(value);
                else if (key.equals("type")) setTempTechniqueType(value);
                else if (key.equals("owner")) setTempTechniqueOwner(value);
                else if (key.equals("damage")) {
                    builder.addTechnique(getTempTechniqueName(), getTempTechniqueType(), 
                                        getTempTechniqueOwner(), parseInt(value, 0));
                }
            } else if (currentSection.equals("ENVIRONMENT") && environment != null) {
                switch (key) {
                    case "weather": environment.setWeather(value); break;
                    case "timeOfDay": environment.setTimeOfDay(value); break;
                    case "visibility": environment.setVisibility(value); break;
                    case "cursedEnergyDensity": environment.setCursedEnergyDensity(parseInt(value, 0)); break;
                }
            }
        }
        
        if (environment != null) {
            builder.putExtension("environmentConditions", environment);
        }
        
        return builder.build();
    }
    
    private String tempSorcererName = "";
    private String tempTechniqueName = "";
    private String tempTechniqueType = "";
    private String tempTechniqueOwner = "";
    private String tempCurseName = "";
    
    private void setTempSorcererName(String name) { this.tempSorcererName = name; }
    private String getTempSorcererName() { return tempSorcererName; }
    
    private void setTempTechniqueName(String name) { this.tempTechniqueName = name; }
    private String getTempTechniqueName() { return tempTechniqueName; }
    
    private void setTempTechniqueType(String type) { this.tempTechniqueType = type; }
    private String getTempTechniqueType() { return tempTechniqueType; }
    
    private void setTempTechniqueOwner(String owner) { this.tempTechniqueOwner = owner; }
    private String getTempTechniqueOwner() { return tempTechniqueOwner; }
    
    private String getCurseNameFromBuilder() { return tempCurseName; }
    
    public String getSupportedFormat() {
        return "TXT";
    }
}