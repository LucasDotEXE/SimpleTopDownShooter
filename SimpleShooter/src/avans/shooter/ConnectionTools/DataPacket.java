package avans.shooter.ConnectionTools;

import avans.shooter.ConnectionTools.Responce.ResponceType;
import avans.shooter.ConnectionTools.Request.RequestType;

public abstract class DataPacket {


    private ResponceType responceType;
    private RequestType requestType;

    public DataPacket(ResponceType responceType, RequestType requestType) {
        this.responceType = responceType;
        this.requestType = requestType;
    }

    public boolean isRequest() {
        return requestType != null;
    }

    public boolean isResponce() {
        return responceType != null;
    }

    public ResponceType getResponceType() {
        return responceType;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
