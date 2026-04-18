package report;

import model.Mission;

public class SummaryReportGenerator implements ReportGenerator {
    
    @Override
    public String generate(Mission mission) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=".repeat(50)).append("\n");
        sb.append("КРАТКИЙ ОТЧЁТ\n");
        sb.append("=".repeat(50)).append("\n");
        
        sb.append("ID: ").append(mission.getMissionId()).append("\n");
        sb.append("Место: ").append(mission.getLocation()).append("\n");
        sb.append("Результат: ").append(mission.getOutcome().getDisplayName()).append("\n");
        
        if (mission.getCurse() != null) {
            sb.append("Проклятие: ").append(mission.getCurse().getName()).append("\n");
        }
        
        int sorcererCount = mission.getSorcerers() != null ? mission.getSorcerers().size() : 0;
        int techniqueCount = mission.getTechniques() != null ? mission.getTechniques().size() : 0;
        
        sb.append("Участников: ").append(sorcererCount).append("\n");
        sb.append("Техник: ").append(techniqueCount).append("\n");
        
        if (mission.getDamageCost() > 0) {
            sb.append("Ущерб: ").append(String.format("%,d", mission.getDamageCost())).append(" йен\n");
        }
        
        sb.append("=".repeat(50)).append("\n");
        return sb.toString();
    }
    
    @Override
    public String getReportType() {
        return "SUMMARY";
    }
}