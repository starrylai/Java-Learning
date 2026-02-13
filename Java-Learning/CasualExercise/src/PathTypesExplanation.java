import java.nio.file.*;

public class PathTypesExplanation {
    public static void main(String[] args) {
        // 1. 绝对路径 vs 相对路径
        Path relativePath = Paths.get("documents/file.txt");
        Path absolutePath = Paths.get("/home/user/documents/file.txt");

        System.out.println("相对路径: " + relativePath);
        System.out.println("绝对路径: " + absolutePath);
        System.out.println("相对路径是绝对路径? " + relativePath.isAbsolute());
        System.out.println("绝对路径是绝对路径? " + absolutePath.isAbsolute());

        // 2. 规范化路径 (Normalized Path)
        Path complexPath = Paths.get("/home/./user/../user/documents/./file.txt");
        Path normalizedPath = complexPath.normalize();

        System.out.println("复杂路径: " + complexPath);
        System.out.println("规范化路径: " + normalizedPath);

        // 3. 父路径 (Parent Path)
        Path filePath = Paths.get("/home/user/documents/file.txt");
        Path parentPath = filePath.getParent();
        Path rootPath = filePath.getRoot();

        System.out.println("文件路径: " + filePath);
        System.out.println("父路径: " + parentPath);
        System.out.println("根路径: " + rootPath);

        // 4. 路径解析
        Path base = Paths.get("/base");
        Path resolved = base.resolve("subfolder/file.txt");
        Path normalizedResolved = resolved.normalize();

        System.out.println("基础路径: " + base);
        System.out.println("解析后路径: " + resolved);
        System.out.println("规范化解析路径: " + normalizedResolved);

        // 5. 实际文件系统操作
        try {
            // 获取当前工作目录
            Path currentDir = Paths.get(".").toAbsolutePath().normalize();
            System.out.println("当前工作目录: " + currentDir);

            // 创建测试文件结构
            Path testDir = Paths.get("test_example");
            if (!Files.exists(testDir)) {
                Files.createDirectories(testDir);
            }

            // 验证路径属性
            System.out.println("testDir 是目录: " + Files.isDirectory(testDir));
            System.out.println("testDir 父路径: " + testDir.getParent());
            System.out.println("testDir 根路径: " + testDir.toAbsolutePath().getRoot());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}