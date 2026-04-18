package report;

import model.Mission;
import model.Sorcerer;
import model.Technique;

public class DetailedReportGenerator implements ReportGenerator {
    
    @Override
    public String generate(Mission mission) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=".repeat(70)).append("\n");
        sb.append("ДЕТАЛИЗИРОВАННЫЙ ОТЧЁТ О МИССИИ\n");
        sb.append("=".repeat(70)).append("\n\n");
        
        // Основная информация
        sb.append("ИДЕНТИФИКАТОР МИССИИ\n");
        sb.append("-".repeat(70)).append("\n");
        sb.append("  ID: ").append(mission.getMissionId()).append("\n");
        sb.append("  Дата: ").append(mission.getDate()).append("\n");
        sb.append("  Локация: ").append(mission.getLocation()).append("\n");
        sb.append("  Результат: ").append(mission.getOutcome().getDisplayName()).append("\n");
        
        if (mission.getDamageCost() > 0) {
            sb.append("  Оценка ущерба: ").append(String.format("%,d", mission.getDamageCost())).append(" йен\n");
        }
        
        // Проклятие
        if (mission.getCurse() != null) {
            sb.append("\nПРОКЛЯТИЕ\n");
            sb.append("-".repeat(70)).append("\n");
            sb.append("  Название: ").append(mission.getCurse().getName()).append("\n");
            sb.append("  Уровень угрозы: ").append(mission.getCurse().getThreatLevel()).append("\n");
        }
        
        // Участники
        if (mission.getSorcerers() != null && !mission.getSorcerers().isEmpty()) {
            sb.append("\nУЧАСТНИКИ\n");
            sb.append("-".repeat(70)).append("\n");
            for (Sorcerer s : mission.getSorcerers()) {
                sb.append("  ").append(s.toString()).append("\n");
            }
        }
        
        // Техники
        if (mission.getTechniques() != null && !mission.getTechniques().isEmpty()) {
            sb.append("\nПРОКЛЯТЫЕ ТЕХНИКИ\n");
            sb.append("-".repeat(70)).append("\n");
            for (Technique t : mission.getTechniques()) {
                sb.append("  ").append(t.toString()).append("\n");
            }
        }
        
        // Экономическая оценка
        if (mission.getEconomicAssessment() != null) {
            sb.append("\nЭКОНОМИЧЕСКАЯ ОЦЕНКА\n");
            sb.append("-".repeat(70)).append("\n");
            sb.append("  ").append(mission.getEconomicAssessment().toString()).append("\n");
        }
        
        // Активность противника
        if (mission.getEnemyActivity() != null) {
            sb.append("\nАКТИВНОСТЬ ПРОТИВНИКА\n");
            sb.append("-".repeat(70)).append("\n");
            sb.append("  ").append(mission.getEnemyActivity().toString().replace("\n", "\n  ")).append("\n");
        }
        
        // Условия среды
        if (mission.getEnvironmentConditions() != null) {
            sb.append("\nУСЛОВИЯ СРЕДЫ\n");
            sb.append("-".repeat(70)).append("\n");
            sb.append("  ").append(mission.getEnvironmentConditions().toString()).append("\n");
        }
        
        // Воздействие на гражданских
        if (mission.getCivilianImpact() != null) {
            sb.append("\nВОЗДЕЙСТВИЕ НА ГРАЖДАНСКИХ\n");
            sb.append("-".repeat(70)).append("\n");
            sb.append("  ").append(mission.getCivilianImpact().toString()).append("\n");
        }
        
        // Комментарий
        if (mission.getComment() != null && !mission.getComment().isEmpty()) {
            sb.append("\nКОММЕНТАРИЙ\n");
            sb.append("-".repeat(70)).append("\n");
            sb.append("  ").append(mission.getComment()).append("\n");
        }
        
        sb.append("\n").append("=".repeat(70)).append("\n");
        return sb.toString();
    }
    
    @Override
    public String getReportType() {
        return "DETAILED";
    }
}