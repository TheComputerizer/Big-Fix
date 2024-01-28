package mods.thecomputerizer.bigfix.core.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodEditor extends ClassVisitor {

    private final String name;
    private final String desc;
    private final MethodAdaptor adaptor;

    public MethodEditor(ClassVisitor visitor, String name, String desc, MethodAdaptor adaptor) {
        super(Opcodes.ASM5,visitor);
        this.name = name;
        this.desc = desc;
        this.adaptor = adaptor;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access,name,desc,signature,exceptions);
        return name.equals(this.name) && desc.equals(this.desc) ? this.adaptor : mv;
    }
}
