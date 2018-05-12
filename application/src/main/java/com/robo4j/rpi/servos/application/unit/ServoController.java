package com.robo4j.rpi.servos.application.unit;

import com.robo4j.CriticalSectionTrait;
import com.robo4j.RoboContext;
import com.robo4j.RoboUnit;
import com.robo4j.logging.SimpleLoggingUtil;
import com.robo4j.socket.http.codec.StringMessage;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
@CriticalSectionTrait
public class ServoController extends RoboUnit<StringMessage> {

    public ServoController(RoboContext context, String id) {
        super(StringMessage.class, context, id);
    }

    @Override
    public void onMessage(StringMessage message) {
        SimpleLoggingUtil.info(getClass(), "MESSAGE: " + message);
    }
}
