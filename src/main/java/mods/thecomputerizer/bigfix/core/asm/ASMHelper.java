package mods.thecomputerizer.bigfix.core.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import static org.objectweb.asm.Opcodes.*;

public class ASMHelper {

    public static final String ENUM_CLASS = "java/lang/Enum";
    public static final String OBJECT_CLASS = "java/lang/Object";

    public static void idk(byte[] classBytes) {

    }

    public static void visitEnum(byte[] classBytes) {
        ClassReader reader = new ClassReader(classBytes);
        ClassWriter writer = new ClassWriter(0);
        writer.visit(V1_8,ACC_PUBLIC | ACC_FINAL | ACC_SUPER | ACC_ENUM,"net/minecraft/world/DimensionType",
                "Ljava/lang/Enum<Lnet/minecraft/world/DimensionType;>;",OBJECT_CLASS,null);
        writer.visitSource("DimensionType.java",null);
        writer.visitEnd();
    }

    public static byte[] addField(ClassReader reader, ClassWriter writer, int access, String name, String desc, Object defVal) {
        reader.accept(new FieldWriter(writer,access,name,desc,defVal),0);
    }
}
