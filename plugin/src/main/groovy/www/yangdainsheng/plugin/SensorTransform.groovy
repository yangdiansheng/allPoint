package www.yangdainsheng.plugin

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project

public class SensorTransform extends Transform {

    private static Project project

    public SensorTransform(Project project){
        this.project = project
    }


    @Override
    String getName() {
        return 'SensorTransformAutoTrack'
    }

    /**
     * 要处理的数据类型。有两种枚举类型
     * CONTENT_CLASS 处理java class文件
     * CONTENT_RESOURCES 处理java 的资源
     * EXTERNAL_LIBRARIES 只有外部库
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 要操作的内容范围
     * PROJECT 只有项目内容
     * SUB_PROJECTS 只要子项目
     *
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    static void printCopyRight() {
        println()
        println("###############################################################")
        println("#####                                                     #####")
        println("#####                                                     #####")
        println("#####                                                     #####")
        println("#####                 欢迎使用全埋点                        #####")
        println("#####            使用中有问题请联系yds本人                   #####")
        println("#####                                                     #####")
        println("#####                                                     #####")
        println("#####                                                     #####")
        println("###############################################################")
        println()
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context,
                   Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException, TransformException, InterruptedException {
        printCopyRight()
        //transform 的input 有两种类型，一种是目录，一种是jar包，要分开遍历
        inputs.each { TransformInput input ->
            //遍历目录
            input.directoryInputs.each { DirectoryInput directoryInput ->
                //获取output目录
                def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                //将input 目录复制到output 目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            //遍历jar
            input.jarInputs.each { JarInput jarInput ->
                //重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5(jarInput.file.getAbsolutePath())
                if (jarName.equals('.jar')) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                File copyJarFile = jarInput.file
                //生成输出路径
                def dest = outputProvider.getContentLocation(jarName + md5Name,jarInput.contentTypes,jarInput.scopes,Format.JAR)
                FileUtils.copyFile(copyJarFile,dest)
            }
        }
    }
}