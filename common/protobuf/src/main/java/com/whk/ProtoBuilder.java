package com.whk;

import cn.hutool.core.util.RuntimeUtil;
import com.whk.protobuf.message.MSGIDProto;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ProtoBuilder {

    private String template;

    private final String templateFileName = "CmdToMessageUtil";

    private final String CMD_MESSAGE = "cmdToMessageMap.put(%s, %s.%s.getDefaultInstance());";
    private final String MESSAGE_CMD = "messageToCmdMap.put(%s.%s.class, %s);";


    public static void main(String[] args) throws IOException {
        ProtoBuilder reader = new ProtoBuilder();
        // 生成proto的java文件
        reader.processProtoFiles();
        // 读取模板文件
        reader.readTemplateFile();
        // 填充内容
        reader.fillContent();
        // 写入文件
        reader.writeToFile();
    }

    public void processProtoFiles() throws IOException {
        String userDir = System.getProperty("user.dir");
        String protoPath = userDir + "\\common\\protobuf\\src\\main\\java\\com\\whk\\protobuf";
        String outputPath = userDir + "\\common\\protobuf\\src\\main\\java";
        String protocExePath = userDir + "\\common\\protobuf\\src\\main\\java\\com\\whk\\";

        File protoDir = new File(protoPath);
        if (!protoDir.exists()) {
            System.err.println("Proto 目录不存在: " + protoPath);
            return;
        }

        System.out.println("开始处理 Proto 文件...");
        System.out.println("Proto 目录: " + protoPath);
        System.out.println("输出目录: " + outputPath);

        List<File> protoFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(protoPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".proto"))
                    .forEach(p -> protoFiles.add(p.toFile()));
        }

        if (protoFiles.isEmpty()) {
            System.out.println("未找到 Proto 文件");
            return;
        }

        System.out.println("找到 " + protoFiles.size() + " 个 Proto 文件");

        for (File protoFile : protoFiles) {
            System.out.println("处理: " + protoFile.getName());
            compileProtoFile(protocExePath, protoFile, protoPath, outputPath);
        }

        System.out.println("\nProto 文件处理完成！");
        System.out.println("生成的 Java 文件位于: " + outputPath);

    }

    private void compileProtoFile(String protocExePath, File protoFile, String protoPath, String outputPath) {
        String protocCommand = protocExePath + "protoc --proto_path=" + protoPath + " --java_out=" + outputPath + " " + protoFile.getAbsolutePath();

        try {
            Process process = RuntimeUtil.exec(protocCommand);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = stdInput.readLine()) != null) {
                System.out.println("  [INFO] " + line);
            }

            StringBuilder errorOutput = new StringBuilder();
            while ((line = stdError.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("  [ERROR] 编译失败 (退出码: " + exitCode + ")");
                if (!errorOutput.isEmpty()) {
                    System.err.println("  [ERROR] " + errorOutput);
                }
            } else {
                System.out.println("  [SUCCESS] 编译成功");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("  [ERROR] 编译过程被中断: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("  [ERROR] 执行 protoc 命令失败: " + e.getMessage());
            System.err.println("  请确保 protoc.exe 在系统 PATH 中");
        }
    }

    public void readTemplateFile() throws IOException {
        byte[] bytes = Files.readAllBytes(new File(System.getProperty("user.dir") + "\\common\\protobuf\\src\\main\\java\\com\\whk\\" + templateFileName + ".template").toPath());
        template = new String(bytes);

    }

    public void fillContent() {
        StringBuilder cmdToMessageBuilder = new StringBuilder();
        StringBuilder messageToCmdBuilder = new StringBuilder();
        for (MSGIDProto.MSGID value : MSGIDProto.MSGID.values()) {
            if (value == MSGIDProto.MSGID.UNRECOGNIZED) continue;
            if (value == MSGIDProto.MSGID.gate_server || value == MSGIDProto.MSGID.game_server || value == MSGIDProto.MSGID.scene_server) {
                cmdToMessageBuilder.append("\n");
                cmdToMessageBuilder.append("        ");
                messageToCmdBuilder.append("\n");
                messageToCmdBuilder.append("        ");
                continue;
            }
            String[] name = value.getValueDescriptor().getName().split("_");
            // setCmdToMessageMapString
            cmdToMessageBuilder.append(CMD_MESSAGE.formatted(value.getNumber(), name[0], name[1]));
            cmdToMessageBuilder.append("\n");
            cmdToMessageBuilder.append("        ");
            // setMessageToCmdMap
            messageToCmdBuilder.append(MESSAGE_CMD.formatted(name[0], name[1], value.getNumber()));
            messageToCmdBuilder.append("\n");
            messageToCmdBuilder.append("        ");
            System.out.println(Arrays.toString(name));
        }

        template = template.replace("{setCmdToMessageMap}", cmdToMessageBuilder);
        template = template.replace("{setMessageToCmdMap}", messageToCmdBuilder);
    }

    public void writeToFile() throws IOException {
        File file = new File(System.getProperty("user.dir") + "\\common\\protobuf\\src\\main\\java\\com\\whk\\" + templateFileName + ".java");
        Files.writeString(file.toPath(), template);
        System.out.println(templateFileName + ".java 已生成");
    }
}
