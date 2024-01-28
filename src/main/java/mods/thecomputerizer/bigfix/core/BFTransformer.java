package mods.thecomputerizer.bigfix.core;

import mods.thecomputerizer.bigfix.core.asm.BFPatcher;
import mods.thecomputerizer.bigfix.core.asm.ClassEditor;
import net.minecraft.launchwrapper.IClassTransformer;


import java.util.function.Function;

@SuppressWarnings("unused")
public class BFTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if(transformedName.equals("net.minecraft.world.DimensionType"))
            return editClass(bytes,BFPatcher::patchDimensionType);
        return bytes;
    }

    private byte[] editClass(byte[] bytes, Function<ClassEditor,byte[]> editor) {
        return editor.apply(new ClassEditor(bytes));
    }
}
