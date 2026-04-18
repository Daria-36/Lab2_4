package parser;

import model.Mission;
import model.MissionOutcome;
import extension.EconomicAssessment;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class YamlMissionParser extends AbstractParser {
    
    public Mission parse(String content) throws Exception {
        resetBuilder();
        BufferedReader reader = new BufferedReader(new StringReader(content));
        String line;
        List<String> sorcererNames = new ArrayList<>();
        List<String> sorcererRanks = new ArrayList<>();
        List<String> techniqueNames = new ArrayList<>();
        List<String> techniqueTypes = new ArrayList<>();
        List<String> techniqueOwners = new ArrayList<>();
        List<Integer> techniqueDamages = new ArrayList<>();
        
        boolean inSorcerers = false;
        boolean inTechniques = false;
        boolean inEconomic = false;
        int sorcererIndex = -1;
        int techniqueIndex = -1;
        
        EconomicAssessment assessment = null;
        
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            if (line.equals("sorcerers:")) {
                inSorcerers = true;
                inTechniques = false;
                inEconomic = false;
                continue;
            } else if (line.equals("techniques:")) {
                inSorcerers = false;
                inTechniques = true;
                inEconomic = false;
                continue;
            } else if (line.startsWith("economicAssessment:")) {
                inSorcerers = false;
                inTechniques = false;
                inEconomic = true;
                assessment = new EconomicAssessment();
                continue;
            } else if (line.startsWith("curse:")) {
                inSorcerers = false;
                inTechniques = false;
                inEconomic = false;
                continue;
            }
            
            if (!line.startsWith("-") && line.contains(":")) {
                String[] parts = line.split(":", 2);
                String key = parts[0].trim();
                String value = parts.length > 1 ? parts[1].trim() : "";
                
                if (inEconomic && assessment != null) {
                    switch (key) {
                        case "totalDamageCost": assessment.setTotalDamageCost(parseInt(value, 0)); break;
                        case "infrastructureDamage": assessment.setInfrastructureDamage(parseInt(value, 0)); break;
                        case "commercialDamage": assessment.setCommercialDamage(parseInt(value, 0)); break;
                        case "transportDamage": assessment.setTransportDamage(parseInt(value, 0)); break;
                        case "recoveryEstimateDays": assessment.setRecoveryEstimateDays(parseInt(value, 0)); break;
                        case "insuranceCovered": assessment.setInsuranceCovered(Boolean.parseBoolean(value)); break;
                    }
                } else {
                    switch (key) {
                        case "missionId": builder.setMissionId(value); break;
                        case "date": builder.setDate(value); break;
                        case "location": builder.setLocation(value); break;
                        case "outcome": builder.setOutcome(MissionOutcome.fromString(value)); break;
                        case "name": 
                            if (inSorcerers && sorcererIndex >= 0) {
                                sorcererNames.set(sorcererIndex, value);
                            } else if (inTechniques && techniqueIndex >= 0) {
                                techniqueNames.set(techniqueIndex, value);
                            } else if (!inSorcerers && !inTechniques) {
                                builder.setCurse(value, "");
                            }
                            break;
                        case "rank":
                            if (inSorcerers && sorcererIndex >= 0) {
                                sorcererRanks.set(sorcererIndex, value);
                            }
                            break;
                        case "type":
                            if (inTechniques && techniqueIndex >= 0) {
                                techniqueTypes.set(techniqueIndex, value);
                            }
                            break;
                        case "owner":
                            if (inTechniques && techniqueIndex >= 0) {
                                techniqueOwners.set(techniqueIndex, value);
                            }
                            break;
                        case "damage":
                            if (inTechniques && techniqueIndex >= 0) {
                                techniqueDamages.set(techniqueIndex, parseInt(value, 0));
                            }
                            break;
                        case "threatLevel":
                            builder.setCurse(getCurseNameFromBuilder(), value);
                            break;
                    }
                }
            } else if (line.startsWith("- name:")) {
                if (inSorcerers) {
                    sorcererIndex++;
                    sorcererNames.add("");
                    sorcererRanks.add("");
                    String name = line.substring(7).trim();
                    sorcererNames.set(sorcererIndex, name);
                } else if (inTechniques) {
                    techniqueIndex++;
                    techniqueNames.add("");
                    techniqueTypes.add("");
                    techniqueOwners.add("");
                    techniqueDamages.add(0);
                    String name = line.substring(7).trim();
                    techniqueNames.set(techniqueIndex, name);
                }
            } else if (line.startsWith("-") && inTechniques && !line.contains("name")) {
            } else if (line.startsWith("  rank:") && inSorcerers && sorcererIndex >= 0) {
                String rank = line.substring(7).trim();
                sorcererRanks.set(sorcererIndex, rank);
            } else if (line.startsWith("  type:") && inTechniques && techniqueIndex >= 0) {
                String type = line.substring(7).trim();
                techniqueTypes.set(techniqueIndex, type);
            } else if (line.startsWith("  owner:") && inTechniques && techniqueIndex >= 0) {
                String owner = line.substring(8).trim();
                techniqueOwners.set(techniqueIndex, owner);
            } else if (line.startsWith("  damage:") && inTechniques && techniqueIndex >= 0) {
                String damage = line.substring(9).trim();
                techniqueDamages.set(techniqueIndex, parseInt(damage, 0));
            }
        }
        
        for (int i = 0; i < sorcererNames.size(); i++) {
            builder.addSorcerer(sorcererNames.get(i), sorcererRanks.get(i));
        }
        
        for (int i = 0; i < techniqueNames.size(); i++) {
            builder.addTechnique(techniqueNames.get(i), techniqueTypes.get(i),
                                techniqueOwners.get(i), techniqueDamages.get(i));
        }
        
        if (assessment != null) {
            builder.putExtension("economicAssessment", assessment);
        }
        
        return builder.build();
    }
    
    private String curseName = "";
    
    private String getCurseNameFromBuilder() { return curseName; }
    
    public String getSupportedFormat() {
        return "YAML";
    }
}