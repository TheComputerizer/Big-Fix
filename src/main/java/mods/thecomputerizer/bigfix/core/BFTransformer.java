package mods.thecomputerizer.bigfix.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class BFTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        ClassReader reader = new ClassReader(basicClass);
        ClassWriter writer = new ClassWriter(reader,0);
        return new byte[0];
    }
}
