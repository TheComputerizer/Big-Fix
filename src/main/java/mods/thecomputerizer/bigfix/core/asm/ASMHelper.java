package mods.thecomputerizer.bigfix.core.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import javax.annotation.Nullable;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.objectweb.asm.Opcodes.*;

public class ASMHelper {

    public static final String ENUM_CLASS = "java/lang/Enum";
    public static final String OBJECT_CLASS = "java/lang/Object";
    private static final int PRIVATE_FINAL = ACC_PRIVATE | ACC_FINAL;
    private static final int PRIVATE_STATIC = ACC_PRIVATE | ACC_STATIC;
    private static final int PRIVATE_STATIC_FINAL = ACC_PRIVATE | ACC_STATIC | ACC_FINAL;
    private static final int PROTECTED_FINAL = ACC_PROTECTED | ACC_FINAL;
    private static final int PROTECTED_STATIC = ACC_PROTECTED | ACC_STATIC;
    private static final int PROTECTED_STATIC_FINAL = ACC_PROTECTED | ACC_STATIC | ACC_FINAL;
    private static final int PUBLIC_FINAL = ACC_PUBLIC | ACC_FINAL;
    private static final int PUBLIC_STATIC = ACC_PUBLIC | ACC_STATIC;
    private static final int PUBLIC_STATIC_FINAL = ACC_PUBLIC | ACC_STATIC | ACC_FINAL;

    public static void addField(ClassReader reader, ClassWriter writer, int access, String name, String desc, Object defVal) {
        reader.accept(new FieldWriter(writer,access,name,desc,defVal),0);
    }

    public static ClassWriter addWriter(ClassReader reader) {
        return new ClassWriter(reader,0);
    }

    /**
     * Matches the first equal name if the desc is null. Returns null if no matches are found
     */
    public static @Nullable MethodNode findMethodNode(ClassNode node, String name, @Nullable String desc) {
        for(MethodNode method : node.methods)
            if(method.name.equals(name) && (Objects.isNull(desc) || method.desc.equals(desc))) return method;
        return null;
    }

    /**
     * Finds the first instruction based on the input matcher. Returns null if nothing is found
     */
    public static @Nullable AbstractInsnNode findInstructionNode(MethodNode node, Function<AbstractInsnNode,Boolean> matcher) {
        ListIterator<AbstractInsnNode> nodeItr = node.instructions.iterator();
        while(nodeItr.hasNext()) {
            AbstractInsnNode aNode = nodeItr.next();
            if(matcher.apply(aNode)) return aNode;
        }
        return null;
    }

    public static ClassNode getClassNode(byte[] bytes) {
        ClassNode node = new ClassNode();
        getReader(bytes).accept(node,0);
        return node;
    }

    public static ClassReader getReader(byte[] bytes) {
        return new ClassReader(bytes);
    }

    /**
     * Assumes input nodes are already ordered properly
     */
    public static void insertBefore(InsnList instructions, AbstractInsnNode location, AbstractInsnNode ... nodes) {
        for(AbstractInsnNode node : nodes)  instructions.insertBefore(location,node);
    }

    /**
     * Assumes input nodes are already ordered properly
     */
    public static void insertBefore(InsnList instructions, AbstractInsnNode location, Iterable<AbstractInsnNode> nodes) {
        for(AbstractInsnNode node : nodes)  instructions.insertBefore(location,node);
    }

    /**
     * Finds the return node of the input method and applies the consumer to it
     */
    public static Consumer<ClassNode> getReturnPatch(
            String methodName, String desc, BiConsumer<MethodNode,AbstractInsnNode> insnConsumer) {
        return node -> {
            MethodNode mn = ASMHelper.findMethodNode(node,methodName,desc);
            if(Objects.nonNull(mn)) {
                AbstractInsnNode insn = findInstructionNode(mn,aNode -> aNode.getOpcode()==Opcodes.RETURN || aNode.getOpcode()==ARETURN);
                if(Objects.nonNull(insn)) insnConsumer.accept(mn,insn);
            }
        };
    }

    /**
     * Returns an array of nodes to add an element to an array. Don't use on final array fields
     */
    public static AbstractInsnNode[] addToArray(
            String type, AbstractInsnNode fieldGetter, Function<Integer,Integer> newElementHandler,
            AbstractInsnNode fieldSetter) {
        return new AbstractInsnNode[0]; //TODO Implement this
    }

    /**
     * Returns an array of nodes to instantiate an array, add elements to it, and then set the array based on
     * the input fieldSetter
     */
    public static AbstractInsnNode[] makeArrayField(int size, String type, Function<Integer,AbstractInsnNode> elementGetter,
                                                    AbstractInsnNode fieldSetter) {
        AbstractInsnNode[] nodes = new AbstractInsnNode[(size+1)*4-1];
        nodes[0] = makeIntNode(size);
        nodes[1] = makeNode(ANEWARRAY,type);
        nodes[2] = makeNode(DUP);
        int index = 3;
        for(int i=0; i<size; i++) {
            nodes[index] = makeIntNode(i);
            index++;
            nodes[index] = elementGetter.apply(i);
            index++;
            nodes[index] = makeNode(AASTORE);
            index++;
            if(i+1<size) {
                nodes[index] = makeNode(DUP);
                i++;
            }
        }
        nodes[index] = fieldSetter;
        return nodes;
    }

    public static AbstractInsnNode makeIntNode(int size) {
        switch(size) {
            case 0: return new InsnNode(ICONST_0);
            case 1: return new InsnNode(ICONST_1);
            case 2: return new InsnNode(ICONST_2);
            case 3: return new InsnNode(ICONST_3);
            case 4: return new InsnNode(ICONST_4);
            case 5: return new InsnNode(ICONST_5);
            default: return new LdcInsnNode(size);
        }
    }

    public static AbstractInsnNode makeNode(int opcode, Object ... parameters) {
        switch(opcode) {
            case GETFIELD:
            case GETSTATIC:
            case PUTFIELD:
            case PUTSTATIC:
                return new FieldInsnNode(opcode,parameters[0].toString(),parameters[1].toString(),parameters[2].toString());
            case NEW:
            case ANEWARRAY:
            case CHECKCAST:
            case INSTANCEOF:
                return new TypeInsnNode(opcode,parameters[0].toString());
            case LDC:
                return new LdcInsnNode(parameters[0]);
            default: return new InsnNode(opcode);
        }
    }

    /**
     * Assumes the entire method node will be replaced
     */
    public static Consumer<ClassNode> replaceMethod(String methodName, @Nullable String desc,
                                                    BiConsumer<ClassNode,MethodNode> replacer) {
        return node -> {
            MethodNode mn = findMethodNode(node,methodName,desc);
            if(Objects.nonNull(mn)) replacer.accept(node,mn);
        };
    }

    public static byte[] writeClassNode(ClassNode node) {
        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        return writer.toByteArray();
    }
}
