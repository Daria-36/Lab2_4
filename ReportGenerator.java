package report;

import model.Mission;

public interface ReportGenerator {
    String generate(Mission mission);
    String getReportType();
}