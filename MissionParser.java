package parser;

import model.Mission;

public interface MissionParser {
    Mission parse(String content) throws Exception;
    String getSupportedFormat();
}