package club.sk1er.patcher.tweaker.asm;

import club.sk1er.patcher.tweaker.transform.PatcherTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;

public class WorldTransformer implements PatcherTransformer {

    /**
     * The class name that's being transformed
     *
     * @return the class name
     */
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.world.World"};
    }

    /**
     * Perform any asm in order to transform code
     *
     * @param classNode the transformed class node
     * @param name      the transformed class name
     */
    @Override
    public void transform(ClassNode classNode, String name) {
        List<String> brightness = Arrays.asList(
                "checkLightFor", "func_180500_c",
                "getLightFromNeighborsFor", "func_175671_l",
                "getLightFromNeighbors", "func_175705_a",
                "getRawLight", "func_175638_a",
                "getLight", "func_175699_k", "func_175721_c"
        );

        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equals("getHorizon")) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), setSkyHeight());
            }

            if (brightness.contains(methodName)) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), setLightLevel());
            }
        }
    }

    private InsnList setLightLevel() {
        InsnList insns = new InsnList();
        insns.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/Minecraft", "func_71410_x", // getMinecraft
                "()Lnet/minecraft/client/Minecraft;", false));
        insns.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "func_152345_ab", // isCallingFromMinecraftThread
                "()Z", false));
        LabelNode ifeq = new LabelNode();
        insns.add(new JumpInsnNode(Opcodes.IFEQ, ifeq));
        insns.add(new FieldInsnNode(Opcodes.GETSTATIC, getPatcherConfigClass(), "fullbright", "Z"));
        insns.add(new JumpInsnNode(Opcodes.IFEQ, ifeq));
        insns.add(new IntInsnNode(Opcodes.BIPUSH, 15));
        insns.add(new InsnNode(Opcodes.IRETURN));
        insns.add(ifeq);
        return insns;
    }

    private InsnList setSkyHeight() {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getPatcherConfigClass(), "skyHeight", "Z"));
        LabelNode ifeq = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, ifeq));
        list.add(new InsnNode(Opcodes.DCONST_0));
        list.add(new InsnNode(Opcodes.DRETURN));
        list.add(ifeq);
        return list;
    }
}