package mods.thecomputerizer.bigfix.core.asm;

import org.objectweb.asm.tree.AbstractInsnNode;

import static org.objectweb.asm.Opcodes.*;

public class BFPatcher {

    public static byte[] patchDimensionType(ClassEditor editor) {
        editor.addPatch(ASMHelper.getReturnPatch("<clinit>",null,
                (mn,aNode) -> {
                    AbstractInsnNode[] arrayNodes = ASMHelper.makeArrayField(3,"net/minecraft/world/DimensionType",
                            i -> {
                                String field = i==0 ? "OVERWORLD" : (i==1 ? "NETHER" : "THE_END");
                                return ASMHelper.makeNode(GETSTATIC,"net/minecraft/world/DimensionType",field,
                                        "Lnet/minecraft/world/DimensionType;");
                            },ASMHelper.makeNode(PUTSTATIC,"net/minecraft/world/DimensionType","bf$ACTUAL_VALUES",
                                    "[Lnet/minecraft/world/DimensionType;"));
                    ASMHelper.insertBefore(mn.instructions,aNode,arrayNodes);
                }));
        editor.addPatch(ASMHelper.getReturnPatch("register",null,
                (mn,aNode) -> { //TODO Add to the bf$ACTUAL_VALUES array here

                }));
        editor.addPatch(ASMHelper.replaceMethod("getValues",null,
                (mn,aNode) -> { //TODO Figure out how to patch getValues since its a base enum method

                }));
        return editor.applyPatches();
    }
}
