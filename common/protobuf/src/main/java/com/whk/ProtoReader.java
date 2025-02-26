package com.whk;

import com.whk.protobuf.message.MSGIDProto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ProtoReader {

    private String template;
    private String result;


    private final String CMD_MESSAGE = "cmdToMessageMap.put(MSGIDProto.MSGID.%s.getNumber(), %s.%s.getDefaultInstance());";
    private final String MESSAGE_CMD = "messageToCmdMap.put(%s.%s.class, MSGIDProto.MSGID.%s.getNumber());";


    public static void main(String[] args) throws IOException {
        ProtoReader reader = new ProtoReader();
        reader.readFile();
        reader.fillContent();
        reader.writeToFile();
    }

    public void readFile() throws IOException {
        byte[] bytes = Files.readAllBytes(new File(System.getProperty("user.dir") + "\\common\\protobuf\\src\\main\\java\\com\\whk\\CmdToMessageUtil.template").toPath());
        template = new String(bytes);

    }

    public void fillContent() {
        StringBuilder cmdToMessageBuilder = new StringBuilder();
        StringBuilder messageToCmdBuilder = new StringBuilder();
        for (MSGIDProto.MSGID value : MSGIDProto.MSGID.values()) {
            if (value == MSGIDProto.MSGID.UNRECOGNIZED) continue;
            String[] name = value.getValueDescriptor().getName().split("_");
            // setCmdToMessageMapString
            cmdToMessageBuilder.append(CMD_MESSAGE.formatted(value.getValueDescriptor().getName(), name[0], name[1]));
            cmdToMessageBuilder.append("\n");
            cmdToMessageBuilder.append("        ");
            // setMessageToCmdMap
            messageToCmdBuilder.append(MESSAGE_CMD.formatted(name[0], name[1], value.getValueDescriptor().getName()));
            messageToCmdBuilder.append("\n");
            messageToCmdBuilder.append("        ");
        }

        result = template.replace("{setCmdToMessageMap}", cmdToMessageBuilder);
        result = result.replace("{setMessageToCmdMap}", messageToCmdBuilder);
    }

    public void writeToFile() throws IOException {
        File file = new File(System.getProperty("user.dir") + "\\common\\protobuf\\src\\main\\java\\com\\whk\\CmdToMessageUtil.java");
        Files.writeString(file.toPath(), result);
    }

}
