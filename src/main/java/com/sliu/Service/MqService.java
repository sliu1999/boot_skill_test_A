package com.sliu.Service;

import java.io.IOException;

public interface MqService {

    public void sendMsg(String message) throws IOException;

    public void sendDelayMessage(String msg);

    public void contextLoads();
}
