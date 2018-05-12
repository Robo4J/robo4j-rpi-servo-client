package com.robo4j.rpi.servos.unit;

import com.robo4j.ConfigurationException;
import com.robo4j.RoboContext;
import com.robo4j.RoboUnit;
import com.robo4j.configuration.Configuration;
import com.robo4j.hw.rpi.Servo;
import com.robo4j.hw.rpi.i2c.pwm.PCA9685Servo;
import com.robo4j.hw.rpi.i2c.pwm.PWMPCA9685Device;
import com.robo4j.logging.SimpleLoggingUtil;
import com.robo4j.socket.http.codec.StringMessage;

import java.io.IOException;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public class ServosControlUnit extends RoboUnit<StringMessage> {

    private static final int SERVO_FREQUENCY = 50;
    private Servo servo;

    public ServosControlUnit(RoboContext context, String id) {
        super(StringMessage.class, context, id);
    }

    @Override
    protected void onInitialization(Configuration configuration) throws ConfigurationException {
        PWMPCA9685Device device;
        try {
            device = new PWMPCA9685Device();
            device.setPWMFrequency(SERVO_FREQUENCY);
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage());
        }
        int servoChannel = configuration.getInteger("servoChannel", 0);

        servo = new PCA9685Servo(device.getChannel(servoChannel));
    }

    @Override
    public void start() {
        System.out.println("center servo");
        process(0F);
    }

    @Override
    public void onMessage(StringMessage message) {
        Float position = Float.valueOf(message.getMessage());
        process(position);
    }

    private void process(float position) {
        try {
            if (position >= -1 || position <= 1)
                servo.setInput(position);
        } catch (IOException e) {
            SimpleLoggingUtil.error(getClass(), e.getMessage());
        }
    }
}
