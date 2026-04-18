package extension;

public class EnvironmentConditions {
    private String weather;
    private String timeOfDay;
    private String visibility;
    private int cursedEnergyDensity;
    
    public EnvironmentConditions() {}
    
    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }
    
    public String getTimeOfDay() { return timeOfDay; }
    public void setTimeOfDay(String timeOfDay) { this.timeOfDay = timeOfDay; }
    
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    
    public int getCursedEnergyDensity() { return cursedEnergyDensity; }
    public void setCursedEnergyDensity(int cursedEnergyDensity) { this.cursedEnergyDensity = cursedEnergyDensity; }
    
    public String toString() {
        return String.format("Погода: %s, Время: %s, Видимость: %s, Плотность проклятой энергии: %d",
            weather, timeOfDay, visibility, cursedEnergyDensity);
    }
}