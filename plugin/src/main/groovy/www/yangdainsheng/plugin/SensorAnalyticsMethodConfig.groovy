package www.yangdainsheng.plugin

import jdk.internal.org.objectweb.asm.Opcodes

class SensorAnalyticsMethodConfig {

    public final static HashMap<String, SensorAnalyticsMethod> LAMBDA_METHODS = new HashMap<>()
    static {
        SensorAnalyticsMethod onClick = new SensorAnalyticsMethod(
                'onClick',
                '(Landroid/view/View;)V',
                'android/view/View$OnClickListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1,
                [Opcodes.ALOAD])
        LAMBDA_METHODS.put(onClick.name + onClick.desc + onClick.parent,  onClick)
        SensorAnalyticsMethod onCheckedChanged = new SensorAnalyticsMethod(
                'onCheckedChanged',
                '(Landroid/widget/CompoundButton;Z)V',
                'android/widget/CompoundButton$OnCheckedChangeListener',
                'trackViewOnCheckChange',
                '(Landroid/widget/CompoundButton;Z)V',
                2,
                [Opcodes.ALOAD,Opcodes.ILOAD])
        LAMBDA_METHODS.put(onCheckedChanged.name + onCheckedChanged.desc + onCheckedChanged.parent, onCheckedChanged)
    }
}
