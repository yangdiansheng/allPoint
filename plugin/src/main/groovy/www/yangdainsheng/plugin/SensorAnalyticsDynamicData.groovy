package www.yangdainsheng.plugin

class SensorAnalyticsDynamicData {
    String desc1
    String nameDesc2

    public SensorAnalyticsDynamicData(String desc1,String nameDesc2){
        this.desc1 = desc1
        this.nameDesc2 = nameDesc2
    }

    @Override
    public String toString() {
        return "SensorAnalyticsDynamicData{" +
                "desc1='" + desc1 + '\'' +
                ", nameDesc2='" + nameDesc2 + '\'' +
                '}';
    }


}
