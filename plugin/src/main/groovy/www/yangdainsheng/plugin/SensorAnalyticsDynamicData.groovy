package www.yangdainsheng.plugin

class SensorAnalyticsDynamicData {
    String name
    String parent
    int localTablePosition//参数在局部变量表中的位置


    SensorAnalyticsDynamicData(String name,String parent,int localTablePosition){
        this.name = name
        this.parent = parent
        this.localTablePosition = localTablePosition
    }

}
