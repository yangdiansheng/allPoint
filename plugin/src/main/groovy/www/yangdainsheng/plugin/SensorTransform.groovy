package www.yangdainsheng.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.ClassWriter
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
/**
 * transform android class -> dex用来修改.class文件
 * groovy
 * https://blog.csdn.net/Neacy_Zz/article/details/78546237
 * https://blog.csdn.net/innost/article/details/48228651
 * http://www.wangyuwei.me/2017/03/05/ASM%E5%AE%9E%E6%88%98%E7%BB%9F%E8%AE%A1%E6%96%B9%E6%B3%95%E8%80%97%E6%97%B6/#more
 * https://github.com/Neacy/NeacyPlugin
 */
public class SensorTransform extends Transform {

    private static Project project

    public SensorTransform(Project project) {
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
                println "==== directoryInput.file = " + directoryInput.file
                if (directoryInput.file.isDirectory()) {
                    directoryInput.file.eachFileRecurse { File file ->
                        // ...对目录进行插入字节码
                        println("==== file.name = " + file.name)
                        def name = file.name

                        if (canModifyName(name)) {
                            println("==== 开始插入 = " + file.name)
                            byte[] bytes = modifyClass(file.bytes)

                            File destFile = new File(file.parentFile.absoluteFile, name)
                            println("==== 重新写入的位置->lastFilePath === " + destFile.getAbsolutePath())
                            FileOutputStream fileOutputStream = new FileOutputStream(destFile)
                            fileOutputStream.write(bytes)
                            fileOutputStream.close()
                        }
                    }
                }

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
                //生成输出路径
                def dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                def modifyJar = modifyJar(jarInput.file,context.getTemporaryDir())
                if (modifyJar == null){
                    modifyJar = jarInput.file
                }
                FileUtils.copyFile(modifyJar, dest)
            }
        }
    }

    private byte[] modifyClass(byte[] fileBytes){
        ClassReader classReader = new ClassReader(fileBytes)
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        SensorAnalyticsClassVisitor classVisitor = new SensorAnalyticsClassVisitor(classWriter)
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
        return classWriter.toByteArray()
    }


    private File modifyJar(File jarFile,File tempDir){
        //读取原jar
        def file = new JarFile(jarFile)
        //设置出到的jar
        def hexName = DigestUtils.md5Hex(jarFile.absolutePath).substring(0,8)
        //要修改的 class  存储在这里 输出jar
        def outputJar = new File(tempDir, hexName + jarFile.name)
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(outputJar))
        Enumeration enumeration = file.entries()
        while (enumeration.hasMoreElements()){
            JarEntry jarEntry = enumeration.nextElement()
            InputStream inputStream = file.getInputStream(jarEntry)
            String entryName = jarEntry.name
            println "----------------- jarEntry.name   " + entryName
            if (entryName.endsWith(".DSA") || entryName.endsWith(".SF")){
                //ignore
            } else {
                if (canModifyName(entryName)){
                    JarEntry outPutJarEntry = new JarEntry(entryName)
                    jarOutputStream.putNextEntry(outPutJarEntry)
                    byte[] modifiedClassBytes = null
                    byte[] sourClassBytes = IOUtils.toByteArray(inputStream)
                    modifiedClassBytes = modifyClass(sourClassBytes)
                    if (modifiedClassBytes == null){
                        modifiedClassBytes = sourClassBytes
                    }
                    jarOutputStream.write(modifiedClassBytes)
                    jarOutputStream.closeEntry()
                }
            }
        }
        jarOutputStream.close()
        file.close()
        return outputJar
    }

    private boolean canModifyName(def name){
        if (name.endsWith(".class")
                && !name.endsWith("R.class")
                && !name.endsWith("BuildConfig.class")
                && !name.contains("R\$")) {
            return true
        }
        return false
    }

    /**
     * 应该忽略的class
     */
    private boolean isIgonre(String name) {
        return (!name.endsWith("R.class")
                && !name.endsWith("BuildConfig.class")
                && !name.contains("R\$")
                && !name.contains(".gradle")
                && !name.startsWith("android")
                && !name.startsWith("www/yangdainsheng/lib_point")
        )
    }

}