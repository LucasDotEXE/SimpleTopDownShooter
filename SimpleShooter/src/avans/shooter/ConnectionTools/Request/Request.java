package avans.shooter.ConnectionTools.Request;

import avans.shooter.Client.ShooterClient;
import avans.shooter.ConnectionTools.DataPacket;

import java.io.Serializable;

public class Request  extends DataPacket implements Serializable {
    private ShooterClient requester;

    public Request(RequestType requestType, ShooterClient requester) {
        super(null, requestType);
        this.requester = requester;
    }

}
