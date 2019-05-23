package avans.shooter.ConnectionTools.Responce;

import avans.shooter.ConnectionTools.DataPacket;

public class Responce<T> extends DataPacket {

    private T data;

    public Responce(T data, ResponceType responceType) {
        super(responceType, null);
        this.data = data;
    }


}
