package report;

import model.Mission;

public class RiskReportGenerator implements ReportGenerator {
    
    @Override
    public String generate(Mission mission) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=".repeat(50)).append("\n");
        sb.append("ОТЧЁТ ПО РИСКАМ\n");
        sb.append("=".repeat(50)).append("\n\n");
        
        sb.append("Миссия: ").append(mission.getMissionId()).append("\n");
        
        // Оценка уровня угрозы проклятия
        String threatLevel = mission.getCurse() != null ? mission.getCurse().getThreatLevel() : "НЕ УКАЗАН";
        sb.append("\n[1] УРОВЕНЬ УГРОЗЫ ПРОКЛЯТИЯ: ").append(threatLevel);
        
        // Риск на основе результата
        sb.append("\n[2] РЕЗУЛЬТАТ МИССИИ: ").append(mission.getOutcome().getDisplayName());
        if (mission.getOutcome() == domain.MissionOutcome.FAILURE) {
            sb.append(" (ВЫСОКИЙ РИСК НЕУДАЧИ)");
        }
        
        // Экономические риски
        if (mission.getEconomicAssessment() != null && mission.getEconomicAssessment().getTotalDamageCost() > 0) {
            sb.append("\n[3] ЭКОНОМИЧЕСКИЙ РИСК: ");
            int total = mission.getEconomicAssessment().getTotalDamageCost();
            if (total > 5000000) {
                sb.append("КРИТИЧЕСКИЙ (ущерб превышает 5 млн йен)");
            } else if (total > 2000000) {
                sb.append("ВЫСОКИЙ (ущерб превышает 2 млн йен)");
            } else {
                sb.append("СРЕДНИЙ");
            }
        }
        
        // Риск эскалации
        if (mission.getEnemyActivity() != null && mission.getEnemyActivity().getEscalationRisk() != null) {
            sb.append("\n[4] РИСК ЭСКАЛАЦИИ: ").append(mission.getEnemyActivity().getEscalationRisk());
        }
        
        // Воздействие на гражданских
        if (mission.getCivilianImpact() != null) {
            int injured = mission.getCivilianImpact().getInjured();
            if (injured > 0) {
                sb.append("\n[5] РИСК ДЛЯ ГРАЖДАНСКИХ: ВЫСОКИЙ (").append(injured).append(" пострадавших)");
            }
        }
        
        sb.append("\n\n").append("=".repeat(50)).append("\n");
        return sb.toString();
    }
    
    @Override
    public String getReportType() {
        return "RISK";
    }
}
