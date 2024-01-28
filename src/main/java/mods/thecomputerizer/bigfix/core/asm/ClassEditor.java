package mods.thecomputerizer.bigfix.core.asm;

import org.objectweb.asm.tree.ClassNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClassEditor {

    private final ClassNode node;
    private final List<Consumer<ClassNode>> patches;

    public ClassEditor(byte[] bytes) {
        this.node = ASMHelper.getClassNode(bytes);
        this.patches = new ArrayList<>();
    }

    public void addPatch(Consumer<ClassNode> patcher) {
        this.patches.add(patcher);
    }

    public byte[] applyPatches() {
        for(Consumer<ClassNode> patch : this.patches) patch.accept(this.node);
        return ASMHelper.writeClassNode(this.node);
    }

    public ClassNode getNode() {
        return this.node;
    }
}
