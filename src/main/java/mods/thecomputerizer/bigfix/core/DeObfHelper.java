package mods.thecomputerizer.bigfix.core;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;

public class DeObfHelper {

    private static final Lookup QUERY = MethodHandles.lookup();

    public static MethodHandle replaceSetter(Class<?> clazz, String fieldName) {
        MethodHandle ret = null;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            ret = QUERY.unreflectSetter(field);
        } catch(IllegalAccessException | NoSuchFieldException ex) {
            BFRef.LOGGER.error("Unable to deobfuscate field `{}` from class `{}`",fieldName,clazz.getName(),ex);
        }
        return ret;
    }
}
