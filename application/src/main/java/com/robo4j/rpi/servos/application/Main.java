package com.robo4j.rpi.servos.application;

import com.robo4j.RoboBuilder;
import com.robo4j.RoboContext;
import com.robo4j.RoboReference;
import com.robo4j.socket.http.codec.StringMessage;
import com.robo4j.util.SystemUtil;

/**
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public class Main {


    public static void main(String[] args) throws Exception {

        ClassLoader classLoader = Main.class.getClassLoader();
        RoboBuilder builder = new RoboBuilder().add(classLoader.getResourceAsStream("robo4j.xml"));
        RoboContext ctx = builder.build();

        System.out.println("State before start:");
        System.out.println(SystemUtil.printStateReport(ctx));
        ctx.start();

        System.out.println("State after start:");
        System.out.println(SystemUtil.printStateReport(ctx));

        final RoboReference<?> httpRef = ctx.getReference("httpServer");
        final RoboReference<StringMessage> ctrlRef = ctx.getReference("controller");
        System.out.println(SystemUtil.printSocketEndPoint(httpRef, ctrlRef));

        System.out.println("Press enter to quit!");
        System.in.read();
        ctx.shutdown();
    }
}
