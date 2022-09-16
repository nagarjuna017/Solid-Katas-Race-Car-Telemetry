package tddmicroexercises.telemetrysystem;

public interface ClientConnection {
    public void connect(String telemetryServerConnectionString);
    public void disconnect();
}
