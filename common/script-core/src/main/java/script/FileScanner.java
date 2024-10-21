package script;

import com.whk.classScan.IClassScan;
import com.whk.classScan.ScannerClassException;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;

@Getter
@Setter
public class FileScanner implements IClassScan {

    Logger logger = Logger.getLogger(FileScanner.class.getName());

    private String defaultPath = Objects.requireNonNull(FileScanner.class.getResource("/")).getPath();

    /**
     * 扫描类文件
     *
     * @param searchPath 包路径
     * @return 类全限定名列表
     */
    protected List<Class<?>> scannerClass(String searchPath, ClassLoader classLoader, Predicate<Class<?>> predicate) throws ScannerClassException {
        File packageFile = new File(searchPath);
        List<Class<?>> classList = new ArrayList<>();
        Queue<File> files = new LinkedList<>();
        files.add(packageFile);

        while (!files.isEmpty()) {
            var file = files.poll();
            //如果是一个文件夹，加入到文件队列中
            if (file.isDirectory()) {
                files.addAll(List.of(Objects.requireNonNull(file.listFiles())));
            } else {
                if (file.getName().endsWith(CLASS_SUFFIX)) {
                    //如果是class文件我们就放入我们的集合中。
                    try {
                        var name = file.getName().substring(0, file.getName().length() - 6);
                        String packageName = file.getPath().substring(searchPath.length() + 1, file.getPath().length() - file.getName().length());
                        byte[] classBytes = Files.readAllBytes(file.getAbsoluteFile().toPath());
                        name = packageName.replace("\\", ".") + name;
                        Class<?> clazz = ((ScriptClassLoader) classLoader).defineScriptClass(name, classBytes, 0, classBytes.length);
                        if (predicate == null || predicate.test(clazz)) {
                            classList.add(clazz);
                            logger.info("加载class %s".formatted(name));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (classList.isEmpty()){
            logger.warning("未加载到class，检查路径或编译");
        }
        return classList;
    }

    @Override
    public List<Class<?>> search(String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) {
        return null;
    }
}
