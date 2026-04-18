package report;

import model.Mission;

public class ReportContext {
    private ReportGenerator generator;
    
    public void setGenerator(ReportGenerator generator) {
        this.generator = generator;
    }
    
    public String generateReport(Mission mission) {
        if (generator == null) {
            throw new IllegalStateException("ReportGenerator не установлен");
        }
        return generator.generate(mission);
    }
}
