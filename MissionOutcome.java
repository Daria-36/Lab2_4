package model;

public enum MissionOutcome {
    SUCCESS("Успех"),
    FAILURE("Провал"),
    PARTIAL_SUCCESS("Частичный успех"),
    IN_PROGRESS("В процессе");
    
    private final String displayName;
    
    MissionOutcome(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public static MissionOutcome fromString(String value) {
        if (value == null) return IN_PROGRESS;
        switch (value.toUpperCase()) {
            case "SUCCESS": return SUCCESS;
            case "FAILURE": return FAILURE;
            case "PARTIAL_SUCCESS": return PARTIAL_SUCCESS;
            default: return IN_PROGRESS;
        }
    }
}