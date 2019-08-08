package www.yangdainsheng.plugin


import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter

class SensorAnalyticsDefaultMethodVisitor extends AdviceAdapter{

    protected SensorAnalyticsDefaultMethodVisitor(MethodVisitor methodVisitor, int access, String name, String desc) {
        super(Opcodes.ASM5, methodVisitor, access, name, desc)
    }
}
