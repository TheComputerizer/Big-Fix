package mods.thecomputerizer.bigfix.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.GeneratorAdapter;

public class MethodAdaptor extends GeneratorAdapter {

    public MethodAdaptor(MethodVisitor visitor, int access, String name, String desc, int opcode) {
        super(Opcodes.ASM5,visitor,access,name,desc);
    }

    @Override
    public void visitInsn(int opcode) {
        if(opcode==Opcodes.RETURN) {
            // before return insert c.showTwo();
            super.visitVarInsn(Opcodes.ALOAD,1); // variable c
            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"ClassName","showTwo","()V",false);
        }
        super.visitInsn(opcode);
    }
}
