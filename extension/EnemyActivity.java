package extension;

import java.util.ArrayList;
import java.util.List;

public class EnemyActivity {
    private String behaviorType;
    private String targetPriority;
    private String mobility;
    private String escalationRisk;
    private List<String> attackPatterns;
    private List<String> countermeasuresUsed;
    
    public EnemyActivity() {
        this.attackPatterns = new ArrayList<>();
        this.countermeasuresUsed = new ArrayList<>();
    }
    
    public String getBehaviorType() { return behaviorType; }
    public void setBehaviorType(String behaviorType) { this.behaviorType = behaviorType; }
    
    public String getTargetPriority() { return targetPriority; }
    public void setTargetPriority(String targetPriority) { this.targetPriority = targetPriority; }
    
    public String getMobility() { return mobility; }
    public void setMobility(String mobility) { this.mobility = mobility; }
    
    public String getEscalationRisk() { return escalationRisk; }
    public void setEscalationRisk(String escalationRisk) { this.escalationRisk = escalationRisk; }
    
    public List<String> getAttackPatterns() { return attackPatterns; }
    public void setAttackPatterns(List<String> attackPatterns) { this.attackPatterns = attackPatterns; }
    
    public List<String> getCountermeasuresUsed() { return countermeasuresUsed; }
    public void setCountermeasuresUsed(List<String> countermeasuresUsed) { this.countermeasuresUsed = countermeasuresUsed; }
    
    public void addAttackPattern(String pattern) { this.attackPatterns.add(pattern); }
    public void addCountermeasure(String measure) { this.countermeasuresUsed.add(measure); }
}