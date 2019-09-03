package www.yangdainsheng.plugin

import jdk.internal.org.objectweb.asm.ClassVisitor
import jdk.internal.org.objectweb.asm.Handle
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * 可以拿到类的信息，对满足条件的
 */
class SensorAnalyticsClassVisitor extends ClassVisitor implements Opcodes {

    private final static String SDK_API_CLASS = "www/yangdainsheng/lib_point/SensorDataAutoTrackHelper"
    private String[] mInterfaces
    private ClassVisitor mClassVisitor
    private Map<String, SensorAnalyticsDynamicData> mDynamicData = new HashMap<>()

    SensorAnalyticsClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor)
        mClassVisitor = classVisitor
    }

    @Override
    void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
        super.visit(i, i1, s, s1, s2, strings)
        mInterfaces = strings
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
        methodVisitor = new SensorAnalyticsDefaultMethodVisitor(methodVisitor, access, name, desc) {


            @Override
            void visitEnd() {
                super.visitEnd()
            }

            @Override
            void visitInvokeDynamicInsn(String name1, String desc1, Handle bsm, Object... bsmArgs) {
                super.visitInvokeDynamicInsn(name1, desc1, bsm, bsmArgs)

                try {
                    if (bsmArgs.size() > 0) {
                        SensorAnalyticsDynamicData data = new SensorAnalyticsDynamicData(
                                name1,
                                desc1,
                                Type.getArgumentTypes(desc1).length)
                        mDynamicData.put(bsmArgs.toString(), data)
                    }
                } catch (Exception e) {
                    e.printStackTrace()
                }
            }

            @Override
            protected void onMethodExit(int i) {
                super.onMethodExit(i)

                def nameDesc = name + desc

                if (name.contains("lambda")) {
                    //包含lambda表达式
                    for (Map.Entry<String, SensorAnalyticsDynamicData> entry : mDynamicData) {
                        if (entry.key != null) {
                            if (entry.key.contains(name + desc)) {
                                SensorAnalyticsMethod sensorAnalyticsMethod = null
                                if (checkForDynamic(entry.value.parent, entry.value.name + desc, SensorAnalyticsMethodConfig.METHOD_ONCLICK)) {
                                    sensorAnalyticsMethod = SensorAnalyticsMethodConfig.LAMBDA_METHODS.get(SensorAnalyticsMethodConfig.METHOD_ONCLICK.getNameDescParent())
                                } else if (checkForDynamic(entry.value.parent, entry.value.name + desc, SensorAnalyticsMethodConfig.METHOD_ONCHECKEDCHANGED)) {
                                    sensorAnalyticsMethod = SensorAnalyticsMethodConfig.LAMBDA_METHODS.get(SensorAnalyticsMethodConfig.METHOD_ONCHECKEDCHANGED.getNameDescParent())
                                }

                                insertOp(methodVisitor, sensorAnalyticsMethod, entry.value.localTablePosition)
                            }
                        }
                    }
                    return
                }


                if (mInterfaces != null && mInterfaces.length > 0) {
                    SensorAnalyticsMethod sensorAnalyticsMethod = null
                    if (checkForMethod(mInterfaces, nameDesc, SensorAnalyticsMethodConfig.METHOD_ONCLICK)) {
                        sensorAnalyticsMethod = SensorAnalyticsMethodConfig.LAMBDA_METHODS.get(SensorAnalyticsMethodConfig.METHOD_ONCLICK.getNameDescParent())
                    } else if (checkForMethod(mInterfaces, nameDesc, SensorAnalyticsMethodConfig.METHOD_ONCHECKEDCHANGED)) {
                        sensorAnalyticsMethod = SensorAnalyticsMethodConfig.LAMBDA_METHODS.get(SensorAnalyticsMethodConfig.METHOD_ONCHECKEDCHANGED.getNameDescParent())
                    }
                    insertOp(methodVisitor, sensorAnalyticsMethod, 1)

                }
            }
        }
        return methodVisitor
    }

    //检测lambda表达式
    boolean checkForDynamic(String parent, String nameDesc, SensorAnalyticsMethodConfig.Method method) {
        return nameDesc.contains(method.desc.substring(1, method.desc.length() - 2)) && parent.contains(method.parent)
    }

    //检测方法
    boolean checkForMethod(String[] parent, String nameDesc, SensorAnalyticsMethodConfig.Method method) {
        return nameDesc == method.getNameDesc() && parent.contains(method.parent)
    }

    //插入指令
    void insertOp(MethodVisitor methodVisitor, SensorAnalyticsMethod method, int paramStart) {
        if (method != null) {
            try {
                for (int j = paramStart; j < method.paramsCount + paramStart; j++) {
                    methodVisitor.visitVarInsn(method.opcodes.get(j - paramStart), j)
                }
                methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, method.agentName, method.agentDesc, false)
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        mDynamicData.clear()
    }
}
