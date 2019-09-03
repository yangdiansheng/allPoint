package www.yangdainsheng.plugin

import jdk.internal.org.objectweb.asm.Opcodes

class SensorAnalyticsMethodConfig {

    static class Method {

        Method(String name, String desc, String parent) {
            this.name = name
            this.desc = desc
            this.parent = parent
        }

        String name
        String desc
        String parent

        String getNameDesc(){
            return name + desc
        }

        String getNameDescParent(){
            return name + desc + parent
        }
    }

    public final static HashMap<String, SensorAnalyticsMethod> LAMBDA_METHODS = new HashMap<>()
    public final static Method METHOD_ONCLICK = new Method('onClick', '(Landroid/view/View;)V', 'android/view/View$OnClickListener')
    public final static Method METHOD_ONCHECKEDCHANGED = new Method('onCheckedChanged', '(Landroid/widget/CompoundButton;Z)V', 'android/widget/CompoundButton$OnCheckedChangeListener')


    static {
        SensorAnalyticsMethod onClick = new SensorAnalyticsMethod(
                METHOD_ONCLICK.name,
                METHOD_ONCLICK.desc,
                METHOD_ONCLICK.parent,
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1,
                [Opcodes.ALOAD])
        LAMBDA_METHODS.put(METHOD_ONCLICK.getNameDescParent(), onClick)

        SensorAnalyticsMethod onCheckedChanged = new SensorAnalyticsMethod(
                METHOD_ONCHECKEDCHANGED.name,
                METHOD_ONCHECKEDCHANGED.desc,
                METHOD_ONCHECKEDCHANGED.parent,
                'trackViewOnCheckChange',
                '(Landroid/widget/CompoundButton;Z)V',
                2,
                [Opcodes.ALOAD, Opcodes.ILOAD])
        LAMBDA_METHODS.put(METHOD_ONCHECKEDCHANGED.getNameDescParent(), onCheckedChanged)
    }
}
