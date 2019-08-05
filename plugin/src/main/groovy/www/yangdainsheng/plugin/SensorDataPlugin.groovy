package www.yangdainsheng.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class SensorDataPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        println "this is my first plugin"

        project.task("My-Plugin-Task") << {
            println "this is my first task"
        }
    }
}