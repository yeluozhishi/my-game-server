package com.whk;

import com.whk.protobuf.message.MSGIDProto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class RouterBuilder {
    private String template;

    private final String templateFileName = "Router";

    private final String CMD_ROUTER = "cmdRouter.put(MSGIDProto.MSGID.%s.getNumber(), MSGIDProto.MSGID.%s);";


    public static void main(String[] args) throws IOException {
        RouterBuilder reader = new RouterBuilder();
        // 读取模板文件
        reader.readTemplateFile();
        // 填充内容
        reader.fillContent();
        // 写入文件
        reader.writeToFile();
    }

    public void readTemplateFile() throws IOException {
        byte[] bytes = Files.readAllBytes(new File(System.getProperty("user.dir") + "\\common\\protobuf\\src\\main\\java\\com\\whk\\" + templateFileName + ".template").toPath());
        template = new String(bytes);
    }

    public void fillContent() {
        StringBuilder cmdToRouter = new StringBuilder();
        MSGIDProto.MSGID serverType = MSGIDProto.MSGID.gate_server;
        for (MSGIDProto.MSGID value : MSGIDProto.MSGID.values()) {
            if (value == MSGIDProto.MSGID.UNRECOGNIZED) continue;
            if (value == MSGIDProto.MSGID.gate_server || value == MSGIDProto.MSGID.game_server || value == MSGIDProto.MSGID.scene_server) {
                serverType = value;
                cmdToRouter.append("\n");
                cmdToRouter.append("        // " + value.getValueDescriptor().getName());
                cmdToRouter.append("\n");
                cmdToRouter.append("        ");
                continue;
            }
            String[] name = value.getValueDescriptor().getName().split("_");
            // setCmdRouter
            cmdToRouter.append(CMD_ROUTER.formatted(value.getValueDescriptor().getName(), serverType.getValueDescriptor().getName()));
            cmdToRouter.append("\n");
            cmdToRouter.append("        ");
            System.out.println(Arrays.toString(name));
        }

        template = template.replace("{setCmdRouter}", cmdToRouter);
    }

    public void writeToFile() throws IOException {
        File file = new File(System.getProperty("user.dir") + "\\common\\protobuf\\src\\main\\java\\com\\whk\\" +templateFileName+ ".java");
        Files.writeString(file.toPath(), template);
        System.out.println(templateFileName + ".java 已生成");
    }
}
