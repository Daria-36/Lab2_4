package parser;

import model.Mission;
import model.MissionOutcome;
import extension.EconomicAssessment;
import extension.EnemyActivity;
import extension.EnvironmentConditions;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class XmlMissionParser extends AbstractParser {
    
    public Mission parse(String content) throws Exception {
        resetBuilder();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = factory.newDocumentBuilder().parse(new ByteArrayInputStream(content.getBytes()));
        doc.getDocumentElement().normalize();
        
        builder.setMissionId(getTagValue("missionId", doc))
               .setDate(getTagValue("date", doc))
               .setLocation(getTagValue("location", doc))
               .setDamageCost(parseInt(getTagValue("damageCost", doc), 0));
        
        String outcomeStr = getTagValue("outcome", doc);
        builder.setOutcome(MissionOutcome.fromString(outcomeStr));
        
        NodeList curseNodes = doc.getElementsByTagName("curse");
        if (curseNodes.getLength() > 0) {
            Element curse = (Element) curseNodes.item(0);
            builder.setCurse(getElementValue(curse, "name"), getElementValue(curse, "threatLevel"));
        }
        
        NodeList sorcererNodes = doc.getElementsByTagName("sorcerer");
        for (int i = 0; i < sorcererNodes.getLength(); i++) {
            Element s = (Element) sorcererNodes.item(i);
            builder.addSorcerer(getElementValue(s, "name"), getElementValue(s, "rank"));
        }
        
        NodeList techniqueNodes = doc.getElementsByTagName("technique");
        for (int i = 0; i < techniqueNodes.getLength(); i++) {
            Element t = (Element) techniqueNodes.item(i);
            builder.addTechnique(getElementValue(t, "name"), getElementValue(t, "type"),
                                getElementValue(t, "owner"), parseInt(getElementValue(t, "damage"), 0));
        }
        
        parseExtensions(doc);
        
        return builder.build();
    }
    
    private void parseExtensions(Document doc) {
        NodeList enemyNodes = doc.getElementsByTagName("enemyActivity");
        if (enemyNodes.getLength() > 0) {
            Element enemy = (Element) enemyNodes.item(0);
            EnemyActivity activity = new EnemyActivity();
            activity.setBehaviorType(getElementValue(enemy, "behaviorType"));
            activity.setTargetPriority(getElementValue(enemy, "targetPriority"));
            activity.setMobility(getElementValue(enemy, "mobility"));
            activity.setEscalationRisk(getElementValue(enemy, "escalationRisk"));
            
            NodeList patternNodes = enemy.getElementsByTagName("pattern");
            for (int i = 0; i < patternNodes.getLength(); i++) {
                activity.addAttackPattern(patternNodes.item(i).getTextContent());
            }
            builder.putExtension("enemyActivity", activity);
        }
        
        NodeList envNodes = doc.getElementsByTagName("environmentConditions");
        if (envNodes.getLength() > 0) {
            Element env = (Element) envNodes.item(0);
            EnvironmentConditions conditions = new EnvironmentConditions();
            conditions.setWeather(getElementValue(env, "weather"));
            conditions.setTimeOfDay(getElementValue(env, "timeOfDay"));
            conditions.setVisibility(getElementValue(env, "visibility"));
            builder.putExtension("environmentConditions", conditions);
        }
    }
    
    private String getTagValue(String tag, Document doc) {
        NodeList list = doc.getElementsByTagName(tag);
        if (list.getLength() > 0 && list.item(0).getFirstChild() != null) {
            return list.item(0).getFirstChild().getNodeValue();
        }
        return "";
    }
    
    private String getElementValue(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0 && list.item(0).getFirstChild() != null) {
            return list.item(0).getFirstChild().getNodeValue();
        }
        return "";
    }
    
    public String getSupportedFormat() {
        return "XML";
    }
}