package www.yangdainsheng.plugin

class SensorAnalyticsMethod {

    /**
     * 原方法名
     */
    String name
    /**
     * 原方法描述
     */
    String desc
    /**
     * 方法所在的接口或类
     */
    String parent
    /**
     * 插入方法名
     */
    String agentName
    /**
     * 插入方法描述符
     */
    String agentDesc
    /**
     * 插入方法几条指令
     */
    int paramsCount
    /**
     * 参数类型对应的ASM指令，加载不同类型的参数需要不同的指令
     */
    List<Integer> opcodes


    SensorAnalyticsMethod(String name, String desc, String parent, String agentName, String agentDesc,int paramsCount, List<Integer> opcodes) {
        this.name = name
        this.desc = desc
        this.parent = parent
        this.agentName = agentName
        this.agentDesc = agentDesc
        this.paramsCount = paramsCount
        this.opcodes = opcodes
    }


    @Override
    public String toString() {
        return "SensorAnalyticsMethod{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", parent='" + parent + '\'' +
                ", agentName='" + agentName + '\'' +
                ", agentDesc='" + agentDesc + '\'' +
                ", paramsCount=" + paramsCount +
                ", opcodes=" + opcodes +
                '}';
    }
}
