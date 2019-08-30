package www.yangdainsheng.plugin

class SensorAnalyticsDynamicData {
    String desc1
    String nameDesc2
    int localTablePosition//参数在局部变量表中的位置

    public SensorAnalyticsDynamicData(String desc1,String nameDesc2,int localTablePosition){
        this.desc1 = desc1
        this.nameDesc2 = nameDesc2
        this.localTablePosition = localTablePosition
    }

    @Override
    public String toString() {
        return "SensorAnalyticsDynamicData{" +
                "desc1='" + desc1 + '\'' +
                ", nameDesc2='" + nameDesc2 + '\'' +
                '}';
    }


}
