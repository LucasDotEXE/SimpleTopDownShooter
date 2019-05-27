package avans.shooter.ConnectionTools.Responce;

import avans.shooter.ConnectionTools.DataPacket;

import java.io.Serializable;

public class Responce<T> extends DataPacket implements Serializable {

    private T data;

    public Responce(T data, ResponceType responceType) {
        super(responceType, null);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
