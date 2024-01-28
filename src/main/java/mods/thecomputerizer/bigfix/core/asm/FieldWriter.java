package mods.thecomputerizer.bigfix.core.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Objects;

public class FieldWriter extends ClassVisitor {

    private final int access;
    private final String name;
    private final String desc;
    private final Object defVal;
    private boolean isPresent;

    public FieldWriter(ClassVisitor visitor, int access, String name, String desc, Object defVal) {
        super(Opcodes.ASM5,visitor);
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.defVal = defVal;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if(name.equals(this.name)) this.isPresent = true;
        return this.cv.visitField(access,name,desc,signature,value);
    }

    @Override
    public void visitEnd() {
        if(!this.isPresent) {
            FieldVisitor visitor = cv.visitField(this.access,this.name,this.desc,null,this.defVal);
            if(Objects.nonNull(visitor)) visitor.visitEnd();
        }
        this.cv.visitEnd();
    }
}
