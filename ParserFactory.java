package parser;

import java.util.HashMap;
import java.util.Map;

public class ParserFactory {
    private static final Map<String, MissionParser> parsers = new HashMap<>();
    
    static {
        parsers.put("json", new JsonParser());
        parsers.put("xml", new XmlParser());
        parsers.put("txt", new TxtParser());
        parsers.put("yaml", new YamlParser());
        parsers.put("yml", new YamlParser());
    }
    
    public static MissionParser getParser(String fileName, String content) {
        String extension = getFileExtension(fileName).toLowerCase();
        
        // Сначала по расширению
        if (parsers.containsKey(extension)) {
            return parsers.get(extension);
        }
        
        // Автоопределение по содержимому
        String trimmed = content.trim();
        if (trimmed.startsWith("{") && trimmed.endsWith("}")) {
            return parsers.get("json");
        }
        if (trimmed.startsWith("<")) {
            return parsers.get("xml");
        }
        if (trimmed.startsWith("missionId:") || trimmed.startsWith("[MISSION]")) {
            return parsers.get("txt");
        }
        
        return null;
    }
    
    private static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0) {
            return fileName.substring(lastDot + 1);
        }
        return "";
    }
    
    public static void registerParser(String format, MissionParser parser) {
        parsers.put(format.toLowerCase(), parser);
    }
}