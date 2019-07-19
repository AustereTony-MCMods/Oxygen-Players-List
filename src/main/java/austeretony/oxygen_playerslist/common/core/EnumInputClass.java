package austeretony.oxygen_playerslist.common.core;

import java.util.Iterator;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public enum EnumInputClass {

    //Client

    MC_GUI_INGAME("Minecraft", "GuiIngame", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_GUI_INGAME_FORGE("Minecraft", "GuiIngameForge", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

    private static final String HOOKS_CLASS = "austeretony/oxygen_playerslist/common/core/PlayersListHooks";

    public final String domain, clazz;

    public final int readerFlags, writerFlags;

    EnumInputClass(String domain, String clazz, int readerFlags, int writerFlags) {
        this.domain = domain;
        this.clazz = clazz;
        this.readerFlags = readerFlags;
        this.writerFlags = writerFlags;
    }

    public boolean patch(ClassNode classNode) {
        switch (this) {
        case MC_GUI_INGAME:
            return this.pathcMCGuiIngame(classNode);
        case MC_GUI_INGAME_FORGE:
            return this.pathcMCGuiIngameForge(classNode);
        }
        return false;
    }

    private boolean pathcMCGuiIngame(ClassNode classNode) {
        String renderGameOverlayMethodName = PlayersListCorePlugin.isObfuscated() ? "a" : "renderGameOverlay";
        boolean isSuccessful = false;   
        int ifeqCount = 0;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(renderGameOverlayMethodName) && methodNode.desc.equals("(F)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {    
                        ifeqCount++;
                        if (ifeqCount == 11) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isTabOverlayEnabled", "()Z", false));
                            nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                            methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious(), nodesList); 
                            isSuccessful = true;                        
                            break;
                        }
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean pathcMCGuiIngameForge(ClassNode classNode) {
        String renderPlayerListMethodName = "renderPlayerList";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(renderPlayerListMethodName) && methodNode.desc.equals("(II)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {    
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isTabOverlayEnabled", "()Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }
}
