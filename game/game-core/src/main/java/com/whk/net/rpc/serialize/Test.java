package com.whk.net.rpc.serialize;

import com.whk.net.rpc.model.MessageRequest;
import com.whk.threadpool.processor.ProcessorId;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        ProtostuffSerializeUtil util = new ProtostuffSerializeUtil();

        MessageRequest request = new MessageRequest();
        request.setClassName("method.getDeclaringClass().getName()");
        request.setMethodName("method.getName()");
        Object[] a = new Object[3];
        a[0] = 1;
        a[1] = "1";
        List<TestClass> playerIds = new LinkedList<>();
        playerIds.add(new TestClass(1, "1"));
        playerIds.add(new TestClass(2, "2"));
        a[2] = playerIds.stream().map(TestClass::getA).collect(Collectors.toList());

        request.setParametersVal(a);
        request.setProcessorId(ProcessorId.DB_PROCESSOR);
        request.setNoReturnAndNonBlocking(false);
        request.setOrderId(1L);

        ByteBuf buf = util.encode(request);

        MessageRequest result = util.decode(buf.array(), MessageRequest.class);

        System.out.println(result);
    }

    @Getter
    @Setter
    public static class TestClass {
        private int a;
        private String b;

        public TestClass(int a, String b) {
            this.a = a;
            this.b = b;
        }
    }
}
