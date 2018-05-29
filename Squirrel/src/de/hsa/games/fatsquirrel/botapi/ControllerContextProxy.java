package de.hsa.games.fatsquirrel.botapi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

public class ControllerContextProxy {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private ControllerContext context;

    public ControllerContextProxy(ControllerContext context) {
        this.context = context;
    }

    public ControllerContext Proxy() {
        DebugHandler handler = new DebugHandler();
        ControllerContext controllerContextProxy = (ControllerContext) Proxy.newProxyInstance(ControllerContext.class.getClassLoader(), new Class[]{ControllerContext.class}, handler);
        return controllerContextProxy;
    }

    private class DebugHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String s = "";
            s += ("* calling method " + method + " with params ");
            if (args != null) {
                for (int i = 0; i < args.length; i++)
                    s += (" " + args[i]);
            }
            logger.fine(s);

            Object result = null;
            try {
                result = method.invoke(context, args);
            } catch (IllegalAccessException ex) {
                logger.severe(ex.toString());
            } catch (InvocationTargetException ex) {
                logger.severe("* exception:" + ex.getTargetException());
                throw ex.getTargetException();
            }
            logger.fine("* result:" + result);
            return result;
        }

    }

}
