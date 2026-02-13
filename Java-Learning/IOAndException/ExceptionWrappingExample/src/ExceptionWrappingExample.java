import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// 自定义运行时异常
class FileReadException extends RuntimeException {
    public FileReadException(String message) {
        super(message);
    }

    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class ExceptionWrappingExample {

    /**
     * 读取文件内容 - 可能抛出两种异常：
     * 1. IOException (受检异常) - 文件读取错误
     * 2. FileNotFoundException (受检异常) - 文件不存在
     * 这里我们捕获这些受检异常，并包装成自定义运行时异常
     */
    public static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();

        } catch (FileNotFoundException e) {
            // FileNotFoundException 是 IOException 的子类，专门表示文件不存在
            // 包装成自定义运行时异常，保留原始异常信息
            throw new FileReadException("文件未找到: " + filePath +
                    "，请检查文件路径是否正确", e);

        } catch (IOException e) {
            // 其他IO异常也包装成自定义运行时异常
            throw new FileReadException("读取文件时发生错误: " + filePath, e);
        }
    }

    public static void main(String[] args) {
        // 尝试读取一个不存在的文件
        String nonExistentFile = "不存在的文件.txt";

        try {
            String content = readFile(nonExistentFile);
            System.out.println("文件内容: " + content);

        } catch (FileReadException e) {
            // 捕获自定义运行时异常，打印友好消息
            System.out.println("发生错误: " + e.getMessage());

            // 如果需要，可以获取原始异常信息
            Throwable originalCause = e.getCause();
            if (originalCause != null) {
                System.out.println("详细原因: " + originalCause.getClass().getName());
                System.out.println("技术细节: " + originalCause.getMessage());

                // 调试时可以打印完整堆栈跟踪
                // originalCause.printStackTrace();
            }

            // 友好的用户提示
            System.out.println("\n建议:");
            System.out.println("1. 检查文件路径是否正确");
            System.out.println("2. 确认文件是否存在");
            System.out.println("3. 检查文件访问权限");

        } catch (Exception e) {
            // 捕获其他可能异常
            System.out.println("发生未知错误: " + e.getMessage());
        }

        System.out.println("\n程序继续执行，不会因为文件不存在而中断...");

        // 演示程序继续执行
        System.out.println("正在执行其他任务...");
        System.out.println("任务完成!");
    }
}
