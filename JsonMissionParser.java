package parser;

import model.Mission;
import model.MissionOutcome;
import extension.EconomicAssessment;
import extension.EnemyActivity;
import extension.EnvironmentConditions;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonMissionParser extends AbstractParser {
    

    public Mission parse(String content) throws Exception {
        resetBuilder();
        JSONObject json = new JSONObject(content);
        
        builder.setMissionId(json.optString("missionId", ""))
               .setDate(json.optString("date", ""))
               .setLocation(json.optString("location", ""))
               .setDamageCost(json.optInt("damageCost", 0));
        
        String outcomeStr = json.optString("outcome", "");
        builder.setOutcome(MissionOutcome.fromString(outcomeStr));
        
        if (json.has("curse")) {
            JSONObject curse = json.getJSONObject("curse");
            builder.setCurse(curse.optString("name", ""), curse.optString("threatLevel", ""));
        }
        
        if (json.has("sorcerers")) {
            JSONArray sorcerers = json.getJSONArray("sorcerers");
            for (int i = 0; i < sorcerers.length(); i++) {
                JSONObject s = sorcerers.getJSONObject(i);
                builder.addSorcerer(s.optString("name", ""), s.optString("rank", ""));
            }
        }
        
        if (json.has("techniques")) {
            JSONArray techniques = json.getJSONArray("techniques");
            for (int i = 0; i < techniques.length(); i++) {
                JSONObject t = techniques.getJSONObject(i);
                builder.addTechnique(t.optString("name", ""), t.optString("type", ""), 
                                    t.optString("owner", ""), t.optInt("damage", 0));
            }
        }
        
        parseExtensions(json);
        
        return builder.build();
    }
    
    private void parseExtensions(JSONObject json) {
        if (json.has("economicAssessment")) {
            JSONObject eco = json.getJSONObject("economicAssessment");
            EconomicAssessment assessment = new EconomicAssessment();
            assessment.setTotalDamageCost(eco.optInt("totalDamageCost", 0));
            assessment.setInfrastructureDamage(eco.optInt("infrastructureDamage", 0));
            assessment.setCommercialDamage(eco.optInt("commercialDamage", 0));
            assessment.setTransportDamage(eco.optInt("transportDamage", 0));
            assessment.setRecoveryEstimateDays(eco.optInt("recoveryEstimateDays", 0));
            assessment.setInsuranceCovered(eco.optBoolean("insuranceCovered", false));
            builder.putExtension("economicAssessment", assessment);
        }
        
        if (json.has("enemyActivity")) {
            JSONObject enemy = json.getJSONObject("enemyActivity");
            EnemyActivity activity = new EnemyActivity();
            activity.setBehaviorType(enemy.optString("behaviorType", ""));
            activity.setTargetPriority(enemy.optString("targetPriority", ""));
            activity.setMobility(enemy.optString("mobility", ""));
            activity.setEscalationRisk(enemy.optString("escalationRisk", ""));
            builder.putExtension("enemyActivity", activity);
        }
        
        if (json.has("environmentConditions")) {
            JSONObject env = json.getJSONObject("environmentConditions");
            EnvironmentConditions conditions = new EnvironmentConditions();
            conditions.setWeather(env.optString("weather", ""));
            conditions.setTimeOfDay(env.optString("timeOfDay", ""));
            conditions.setVisibility(env.optString("visibility", ""));
            conditions.setCursedEnergyDensity(env.optInt("cursedEnergyDensity", 0));
            builder.putExtension("environmentConditions", conditions);
        }
    }
    
    public String getSupportedFormat() {
        return "JSON";
    }
}