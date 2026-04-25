package app;

import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;

@SessionScoped
public class CarBean implements Serializable {
    private String carModel;
    private String engineType;

    public String getCarModel() { return carModel; }
    public void setCarModel( String carModel) { this.carModel = carModel; }

    public String getEngineType() { return engineType; }
    public void setEngineType(String engineType) { this.engineType = engineType; }

    public String getPerformanceLabel() {
        if (this.engineType.contains("V8")) {
            return "High Performance";
        }
        return "Standard";
    }
}
