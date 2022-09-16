package tddmicroexercises.telemetrysystem;

public class TelemetryDiagnosticControls
{
    private final String DiagnosticChannelConnectionString = "*111#";

    private final TelemetryClient telemetryClient;
    private final TelemetryClientConnection telemetryClientConnection;
    private String diagnosticInfo = "";

    public TelemetryDiagnosticControls(TelemetryClient telemetryClient, TelemetryClientConnection telemetryClientConnection)
    {
        this.telemetryClient = telemetryClient;
        this.telemetryClientConnection = telemetryClientConnection;
    }

    public String getDiagnosticInfo(){
        return diagnosticInfo;
    }

    public void setDiagnosticInfo(String diagnosticInfo){
        this.diagnosticInfo = diagnosticInfo;
    }

    public void checkTransmission() throws Exception
    {
        diagnosticInfo = "";

        telemetryClientConnection.disconnect();

        int retryLeft = 3;
        while (!telemetryClientConnection.getOnlineStatus() && retryLeft > 0)
        {
            telemetryClientConnection.connect(DiagnosticChannelConnectionString);
            retryLeft -= 1;
        }

        if(!telemetryClientConnection.getOnlineStatus())
        {
            throw new Exception("Unable to connect.");
        }

        telemetryClient.send(TelemetryClient.DIAGNOSTIC_MESSAGE);
        diagnosticInfo = telemetryClient.receive();
    }
}