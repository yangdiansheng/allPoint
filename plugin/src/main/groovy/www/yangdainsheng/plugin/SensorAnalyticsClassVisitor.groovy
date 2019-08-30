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
//        println " --------- strings " + strings.toString() + "-------------"
        mInterfaces = strings
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
        methodVisitor = new SensorAnalyticsDefaultMethodVisitor(methodVisitor, access, name, desc) {


            @Override
            void visitEnd() {
                super.visitEnd()
//                println " --------- visitEnd    -------------"
            }

            @Override
            void visitInvokeDynamicInsn(String name1, String desc1, Handle bsm, Object... bsmArgs) {
                super.visitInvokeDynamicInsn(name1, desc1, bsm, bsmArgs)

                if (name1 == 'onCheckedChanged') {
                    //                println " --------- dynamic  name1  -------------" + name1
//                println " --------- dynamic  desc1  -------------" + desc1
//                println " --------- dynamic  bsm  -------------" + bsm.toString()
//                println " --------- dynamic  bsmArgs  -------------" + bsmArgs
//                    println " --------- dynamic  Type.getArgumentTypes(desc1)  -------------" + Type.getArgumentTypes(desc1)
//                println " --------- dynamic  Type.getArgumentsAndReturnSizes(desc1).  -------------" + Type.getArgumentsAndReturnSizes(desc1)
//                println " --------- dynamic  Type.getMethodType(desc1).  -------------" + Type.getMethodType(desc1)
//                    println " --------- "
//                    println " --------- "
//                    println " --------- "
//                    println " --------- "
//                    println " --------- "
                }


                if (name1 == 'onClick') {
//
//                    println " --------- dynamic  name1  -------------" + name1
//                    println " --------- dynamic  desc1  -------------" + desc1
//                    println " --------- dynamic  bsm  -------------" + bsm.toString()
//                    println " --------- dynamic  bsmArgs  -------------" + bsmArgs
//                    println " --------- dynamic  Type.getArgumentTypes(desc1)  -------------" + Type.getArgumentTypes(desc1)
//                    println " --------- dynamic  Type.getArgumentsAndReturnSizes(desc1).  -------------" + Type.getArgumentsAndReturnSizes(desc1)
//                    println " --------- dynamic  Type.getMethodType(desc1).  -------------" + Type.getMethodType(desc1)
//                    println " --------- "
//                    println " --------- "
//                    println " --------- "
//                    println " --------- "
//                    println " --------- "




                    try {
                        if (bsmArgs.size() > 0) {
                            SensorAnalyticsDynamicData data = new SensorAnalyticsDynamicData(
                                    desc1,
                                    name1 + bsmArgs[0],
                                    Type.getArgumentTypes(desc1).length)
                            mDynamicData.put(bsmArgs.toString(), data)
                        }
                    } catch (Exception e) {
                        e.printStackTrace()
                    }
                }
            }

            @Override
            protected void onMethodExit(int i) {
                super.onMethodExit(i)

                def nameDesc = name + desc

                if (name.contains("lambda")) {
                    //包含lambda表达式
//                    println(name)
//                    println(desc)

//                    println " --------- mDynamicData   ------------- " + mDynamicData.toString()

                    String dynamicKey = ''
                    for (Map.Entry<String, SensorAnalyticsDynamicData> entry : mDynamicData) {
                        if (entry.key != null) {
                            if (entry.key.contains(name + desc)) {
                                dynamicKey = entry.key
                                if (entry.value.desc1.contains('Landroid/view/View$OnClickListener') && entry.value.nameDesc2 == 'onClick(Landroid/view/View;)V') {
//                                    println " ---------  visitInvokeDynamicInsn start insert -------------"
//                                    println " ---------  accesst -------------" + access


//                                    println " --------- method getArgumentTypes  ------------- " + Type.getArgumentTypes(desc)
//                                    println " --------- method name  ------------- " + name
//                                    println " --------- method desc  ------------- " + desc


                                    if ((access & Opcodes.ACC_STATIC) != 0) {
                                        //方法的标识按位计算需要使用&操作
                                        //这个表示为static方法，局部变量表第一个位置为view
//                                        println " --------- lambda  0 ------------- " + name
                                        methodVisitor.visitVarInsn(ALOAD, entry.value.localTablePosition)
                                    } else {
                                        methodVisitor.visitVarInsn(ALOAD, entry.value.localTablePosition)
//                                        println " --------- lambda   1  ------------- " + name
                                    }
                                    methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/view/View;)V", false)
                                }
                            }
                        }
                    }
                    if (!(dynamicKey == '')) {
                        mDynamicData.remove(dynamicKey)
                    }
                    return
                }


                if (mInterfaces != null && mInterfaces.length > 0) {
                    SensorAnalyticsMethod sensorAnalyticsMethod
                    if ((mInterfaces.contains('android/view/View$OnClickListener') && nameDesc == 'onClick(Landroid/view/View;)V')) {
                        sensorAnalyticsMethod = SensorAnalyticsMethodConfig.LAMBDA_METHODS.get(nameDesc + 'android/view/View$OnClickListener')
                    } else if ((mInterfaces.contains('android/widget/CompoundButton$OnCheckedChangeListener') && nameDesc == 'onCheckedChanged(Landroid/widget/CompoundButton;Z)V')) {
                        sensorAnalyticsMethod = SensorAnalyticsMethodConfig.LAMBDA_METHODS.get(nameDesc + 'android/widget/CompoundButton$OnCheckedChangeListener')
                    }

                    if (sensorAnalyticsMethod != null) {
                        try {
                            int initParamsStart = 1
                            for (int j = initParamsStart; j < sensorAnalyticsMethod.paramsCount + initParamsStart; j++) {
                                methodVisitor.visitVarInsn(sensorAnalyticsMethod.opcodes.get(j - initParamsStart), j)
                            }
                            methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, sensorAnalyticsMethod.agentName, sensorAnalyticsMethod.agentDesc, false)
                        } catch(Exception e){
                            e.printStackTrace()
                        }
                    }

                }
            }
        }
        return methodVisitor
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        mDynamicData.clear()
//        println " --------- class visit end  -------------"
//        println " -- "
//        println " -- "
//        println " -- "
    }
}
