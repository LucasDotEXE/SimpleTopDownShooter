package avans.shooter.ConnectionTools.Request;

import avans.shooter.Client.ShooterClient;
import avans.shooter.ConnectionTools.DataPacket;

public class Request  extends DataPacket {
    private ShooterClient requester;

    public Request(RequestType requestType, ShooterClient requester) {
        super(null, requestType);
        this.requester = requester;
    }

}
