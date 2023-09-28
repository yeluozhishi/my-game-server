package org.whk.protobuf.message;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Message.proto

public final class MessageOuterClass {
  private MessageOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Message)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 command = 1;</code>
     * @return The command.
     */
    long getCommand();

    /**
     * <code>.LoginRes loginRes = 2;</code>
     * @return Whether the loginRes field is set.
     */
    boolean hasLoginRes();
    /**
     * <code>.LoginRes loginRes = 2;</code>
     * @return The loginRes.
     */
    LoginResOuterClass.LoginRes getLoginRes();
    /**
     * <code>.LoginRes loginRes = 2;</code>
     */
    LoginResOuterClass.LoginResOrBuilder getLoginResOrBuilder();

    /**
     * <code>.LoginReq loginReq = 3;</code>
     * @return Whether the loginReq field is set.
     */
    boolean hasLoginReq();
    /**
     * <code>.LoginReq loginReq = 3;</code>
     * @return The loginReq.
     */
    LoginReqOuterClass.LoginReq getLoginReq();
    /**
     * <code>.LoginReq loginReq = 3;</code>
     */
    LoginReqOuterClass.LoginReqOrBuilder getLoginReqOrBuilder();

    /**
     * <code>.Empty empty = 4;</code>
     * @return Whether the empty field is set.
     */
    boolean hasEmpty();
    /**
     * <code>.Empty empty = 4;</code>
     * @return The empty.
     */
    EmptyOuterClass.Empty getEmpty();
    /**
     * <code>.Empty empty = 4;</code>
     */
    EmptyOuterClass.EmptyOrBuilder getEmptyOrBuilder();

    Message.BodyCase getBodyCase();
  }
  /**
   * Protobuf type {@code Message}
   */
  public static final class Message extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Message)
      MessageOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Message.newBuilder() to construct.
    private Message(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Message() {
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new Message();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return MessageOuterClass.internal_static_Message_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return MessageOuterClass.internal_static_Message_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Message.class, Builder.class);
    }

    private int bodyCase_ = 0;
    @SuppressWarnings("serial")
    private Object body_;
    public enum BodyCase
        implements com.google.protobuf.Internal.EnumLite,
            InternalOneOfEnum {
      LOGINRES(2),
      LOGINREQ(3),
      EMPTY(4),
      BODY_NOT_SET(0);
      private final int value;
      private BodyCase(int value) {
        this.value = value;
      }
      /**
       * @param value The number of the enum to look for.
       * @return The enum associated with the given number.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @Deprecated
      public static BodyCase valueOf(int value) {
        return forNumber(value);
      }

      public static BodyCase forNumber(int value) {
        switch (value) {
          case 2: return LOGINRES;
          case 3: return LOGINREQ;
          case 4: return EMPTY;
          case 0: return BODY_NOT_SET;
          default: return null;
        }
      }
      public int getNumber() {
        return this.value;
      }
    };

    public BodyCase
    getBodyCase() {
      return BodyCase.forNumber(
          bodyCase_);
    }

    public static final int COMMAND_FIELD_NUMBER = 1;
    private long command_ = 0L;
    /**
     * <code>int64 command = 1;</code>
     * @return The command.
     */
    @Override
    public long getCommand() {
      return command_;
    }

    public static final int LOGINRES_FIELD_NUMBER = 2;
    /**
     * <code>.LoginRes loginRes = 2;</code>
     * @return Whether the loginRes field is set.
     */
    @Override
    public boolean hasLoginRes() {
      return bodyCase_ == 2;
    }
    /**
     * <code>.LoginRes loginRes = 2;</code>
     * @return The loginRes.
     */
    @Override
    public LoginResOuterClass.LoginRes getLoginRes() {
      if (bodyCase_ == 2) {
         return (LoginResOuterClass.LoginRes) body_;
      }
      return LoginResOuterClass.LoginRes.getDefaultInstance();
    }
    /**
     * <code>.LoginRes loginRes = 2;</code>
     */
    @Override
    public LoginResOuterClass.LoginResOrBuilder getLoginResOrBuilder() {
      if (bodyCase_ == 2) {
         return (LoginResOuterClass.LoginRes) body_;
      }
      return LoginResOuterClass.LoginRes.getDefaultInstance();
    }

    public static final int LOGINREQ_FIELD_NUMBER = 3;
    /**
     * <code>.LoginReq loginReq = 3;</code>
     * @return Whether the loginReq field is set.
     */
    @Override
    public boolean hasLoginReq() {
      return bodyCase_ == 3;
    }
    /**
     * <code>.LoginReq loginReq = 3;</code>
     * @return The loginReq.
     */
    @Override
    public LoginReqOuterClass.LoginReq getLoginReq() {
      if (bodyCase_ == 3) {
         return (LoginReqOuterClass.LoginReq) body_;
      }
      return LoginReqOuterClass.LoginReq.getDefaultInstance();
    }
    /**
     * <code>.LoginReq loginReq = 3;</code>
     */
    @Override
    public LoginReqOuterClass.LoginReqOrBuilder getLoginReqOrBuilder() {
      if (bodyCase_ == 3) {
         return (LoginReqOuterClass.LoginReq) body_;
      }
      return LoginReqOuterClass.LoginReq.getDefaultInstance();
    }

    public static final int EMPTY_FIELD_NUMBER = 4;
    /**
     * <code>.Empty empty = 4;</code>
     * @return Whether the empty field is set.
     */
    @Override
    public boolean hasEmpty() {
      return bodyCase_ == 4;
    }
    /**
     * <code>.Empty empty = 4;</code>
     * @return The empty.
     */
    @Override
    public EmptyOuterClass.Empty getEmpty() {
      if (bodyCase_ == 4) {
         return (EmptyOuterClass.Empty) body_;
      }
      return EmptyOuterClass.Empty.getDefaultInstance();
    }
    /**
     * <code>.Empty empty = 4;</code>
     */
    @Override
    public EmptyOuterClass.EmptyOrBuilder getEmptyOrBuilder() {
      if (bodyCase_ == 4) {
         return (EmptyOuterClass.Empty) body_;
      }
      return EmptyOuterClass.Empty.getDefaultInstance();
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (command_ != 0L) {
        output.writeInt64(1, command_);
      }
      if (bodyCase_ == 2) {
        output.writeMessage(2, (LoginResOuterClass.LoginRes) body_);
      }
      if (bodyCase_ == 3) {
        output.writeMessage(3, (LoginReqOuterClass.LoginReq) body_);
      }
      if (bodyCase_ == 4) {
        output.writeMessage(4, (EmptyOuterClass.Empty) body_);
      }
      getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (command_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, command_);
      }
      if (bodyCase_ == 2) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, (LoginResOuterClass.LoginRes) body_);
      }
      if (bodyCase_ == 3) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(3, (LoginReqOuterClass.LoginReq) body_);
      }
      if (bodyCase_ == 4) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(4, (EmptyOuterClass.Empty) body_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Message)) {
        return super.equals(obj);
      }
      Message other = (Message) obj;

      if (getCommand()
          != other.getCommand()) return false;
      if (!getBodyCase().equals(other.getBodyCase())) return false;
      switch (bodyCase_) {
        case 2:
          if (!getLoginRes()
              .equals(other.getLoginRes())) return false;
          break;
        case 3:
          if (!getLoginReq()
              .equals(other.getLoginReq())) return false;
          break;
        case 4:
          if (!getEmpty()
              .equals(other.getEmpty())) return false;
          break;
        case 0:
        default:
      }
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + COMMAND_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getCommand());
      switch (bodyCase_) {
        case 2:
          hash = (37 * hash) + LOGINRES_FIELD_NUMBER;
          hash = (53 * hash) + getLoginRes().hashCode();
          break;
        case 3:
          hash = (37 * hash) + LOGINREQ_FIELD_NUMBER;
          hash = (53 * hash) + getLoginReq().hashCode();
          break;
        case 4:
          hash = (37 * hash) + EMPTY_FIELD_NUMBER;
          hash = (53 * hash) + getEmpty().hashCode();
          break;
        case 0:
        default:
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Message parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Message parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Message parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Message parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Message parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Message parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Message parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Message parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Message parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static Message parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Message parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Message parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Message prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Message}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Message)
        MessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return MessageOuterClass.internal_static_Message_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return MessageOuterClass.internal_static_Message_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Message.class, Builder.class);
      }

      // Construct using MessageOuterClass.Message.newBuilder()
      private Builder() {

      }

      private Builder(
          BuilderParent parent) {
        super(parent);

      }
      @Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        command_ = 0L;
        if (loginResBuilder_ != null) {
          loginResBuilder_.clear();
        }
        if (loginReqBuilder_ != null) {
          loginReqBuilder_.clear();
        }
        if (emptyBuilder_ != null) {
          emptyBuilder_.clear();
        }
        bodyCase_ = 0;
        body_ = null;
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return MessageOuterClass.internal_static_Message_descriptor;
      }

      @Override
      public Message getDefaultInstanceForType() {
        return Message.getDefaultInstance();
      }

      @Override
      public Message build() {
        Message result = buildPartial();
        if (!result.isInitialized()) {
          throw Builder.newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public Message buildPartial() {
        Message result = new Message(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        buildPartialOneofs(result);
        onBuilt();
        return result;
      }

      private void buildPartial0(Message result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.command_ = command_;
        }
      }

      private void buildPartialOneofs(Message result) {
        result.bodyCase_ = bodyCase_;
        result.body_ = this.body_;
        if (bodyCase_ == 2 &&
            loginResBuilder_ != null) {
          result.body_ = loginResBuilder_.build();
        }
        if (bodyCase_ == 3 &&
            loginReqBuilder_ != null) {
          result.body_ = loginReqBuilder_.build();
        }
        if (bodyCase_ == 4 &&
            emptyBuilder_ != null) {
          result.body_ = emptyBuilder_.build();
        }
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Message) {
          return mergeFrom((Message)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Message other) {
        if (other == Message.getDefaultInstance()) return this;
        if (other.getCommand() != 0L) {
          setCommand(other.getCommand());
        }
        switch (other.getBodyCase()) {
          case LOGINRES: {
            mergeLoginRes(other.getLoginRes());
            break;
          }
          case LOGINREQ: {
            mergeLoginReq(other.getLoginReq());
            break;
          }
          case EMPTY: {
            mergeEmpty(other.getEmpty());
            break;
          }
          case BODY_NOT_SET: {
            break;
          }
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 8: {
                command_ = input.readInt64();
                bitField0_ |= 0x00000001;
                break;
              } // case 8
              case 18: {
                input.readMessage(
                    getLoginResFieldBuilder().getBuilder(),
                    extensionRegistry);
                bodyCase_ = 2;
                break;
              } // case 18
              case 26: {
                input.readMessage(
                    getLoginReqFieldBuilder().getBuilder(),
                    extensionRegistry);
                bodyCase_ = 3;
                break;
              } // case 26
              case 34: {
                input.readMessage(
                    getEmptyFieldBuilder().getBuilder(),
                    extensionRegistry);
                bodyCase_ = 4;
                break;
              } // case 34
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bodyCase_ = 0;
      private Object body_;
      public BodyCase
          getBodyCase() {
        return BodyCase.forNumber(
            bodyCase_);
      }

      public Builder clearBody() {
        bodyCase_ = 0;
        body_ = null;
        onChanged();
        return this;
      }

      private int bitField0_;

      private long command_ ;
      /**
       * <code>int64 command = 1;</code>
       * @return The command.
       */
      @Override
      public long getCommand() {
        return command_;
      }
      /**
       * <code>int64 command = 1;</code>
       * @param value The command to set.
       * @return This builder for chaining.
       */
      public Builder setCommand(long value) {

        command_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>int64 command = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearCommand() {
        bitField0_ = (bitField0_ & ~0x00000001);
        command_ = 0L;
        onChanged();
        return this;
      }

      private com.google.protobuf.SingleFieldBuilderV3<
          LoginResOuterClass.LoginRes, LoginResOuterClass.LoginRes.Builder, LoginResOuterClass.LoginResOrBuilder> loginResBuilder_;
      /**
       * <code>.LoginRes loginRes = 2;</code>
       * @return Whether the loginRes field is set.
       */
      @Override
      public boolean hasLoginRes() {
        return bodyCase_ == 2;
      }
      /**
       * <code>.LoginRes loginRes = 2;</code>
       * @return The loginRes.
       */
      @Override
      public LoginResOuterClass.LoginRes getLoginRes() {
        if (loginResBuilder_ == null) {
          if (bodyCase_ == 2) {
            return (LoginResOuterClass.LoginRes) body_;
          }
          return LoginResOuterClass.LoginRes.getDefaultInstance();
        } else {
          if (bodyCase_ == 2) {
            return loginResBuilder_.getMessage();
          }
          return LoginResOuterClass.LoginRes.getDefaultInstance();
        }
      }
      /**
       * <code>.LoginRes loginRes = 2;</code>
       */
      public Builder setLoginRes(LoginResOuterClass.LoginRes value) {
        if (loginResBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          body_ = value;
          onChanged();
        } else {
          loginResBuilder_.setMessage(value);
        }
        bodyCase_ = 2;
        return this;
      }
      /**
       * <code>.LoginRes loginRes = 2;</code>
       */
      public Builder setLoginRes(
          LoginResOuterClass.LoginRes.Builder builderForValue) {
        if (loginResBuilder_ == null) {
          body_ = builderForValue.build();
          onChanged();
        } else {
          loginResBuilder_.setMessage(builderForValue.build());
        }
        bodyCase_ = 2;
        return this;
      }
      /**
       * <code>.LoginRes loginRes = 2;</code>
       */
      public Builder mergeLoginRes(LoginResOuterClass.LoginRes value) {
        if (loginResBuilder_ == null) {
          if (bodyCase_ == 2 &&
              body_ != LoginResOuterClass.LoginRes.getDefaultInstance()) {
            body_ = LoginResOuterClass.LoginRes.newBuilder((LoginResOuterClass.LoginRes) body_)
                .mergeFrom(value).buildPartial();
          } else {
            body_ = value;
          }
          onChanged();
        } else {
          if (bodyCase_ == 2) {
            loginResBuilder_.mergeFrom(value);
          } else {
            loginResBuilder_.setMessage(value);
          }
        }
        bodyCase_ = 2;
        return this;
      }
      /**
       * <code>.LoginRes loginRes = 2;</code>
       */
      public Builder clearLoginRes() {
        if (loginResBuilder_ == null) {
          if (bodyCase_ == 2) {
            bodyCase_ = 0;
            body_ = null;
            onChanged();
          }
        } else {
          if (bodyCase_ == 2) {
            bodyCase_ = 0;
            body_ = null;
          }
          loginResBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>.LoginRes loginRes = 2;</code>
       */
      public LoginResOuterClass.LoginRes.Builder getLoginResBuilder() {
        return getLoginResFieldBuilder().getBuilder();
      }
      /**
       * <code>.LoginRes loginRes = 2;</code>
       */
      @Override
      public LoginResOuterClass.LoginResOrBuilder getLoginResOrBuilder() {
        if ((bodyCase_ == 2) && (loginResBuilder_ != null)) {
          return loginResBuilder_.getMessageOrBuilder();
        } else {
          if (bodyCase_ == 2) {
            return (LoginResOuterClass.LoginRes) body_;
          }
          return LoginResOuterClass.LoginRes.getDefaultInstance();
        }
      }
      /**
       * <code>.LoginRes loginRes = 2;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          LoginResOuterClass.LoginRes, LoginResOuterClass.LoginRes.Builder, LoginResOuterClass.LoginResOrBuilder> 
          getLoginResFieldBuilder() {
        if (loginResBuilder_ == null) {
          if (!(bodyCase_ == 2)) {
            body_ = LoginResOuterClass.LoginRes.getDefaultInstance();
          }
          loginResBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              LoginResOuterClass.LoginRes, LoginResOuterClass.LoginRes.Builder, LoginResOuterClass.LoginResOrBuilder>(
                  (LoginResOuterClass.LoginRes) body_,
                  getParentForChildren(),
                  isClean());
          body_ = null;
        }
        bodyCase_ = 2;
        onChanged();
        return loginResBuilder_;
      }

      private com.google.protobuf.SingleFieldBuilderV3<
          LoginReqOuterClass.LoginReq, LoginReqOuterClass.LoginReq.Builder, LoginReqOuterClass.LoginReqOrBuilder> loginReqBuilder_;
      /**
       * <code>.LoginReq loginReq = 3;</code>
       * @return Whether the loginReq field is set.
       */
      @Override
      public boolean hasLoginReq() {
        return bodyCase_ == 3;
      }
      /**
       * <code>.LoginReq loginReq = 3;</code>
       * @return The loginReq.
       */
      @Override
      public LoginReqOuterClass.LoginReq getLoginReq() {
        if (loginReqBuilder_ == null) {
          if (bodyCase_ == 3) {
            return (LoginReqOuterClass.LoginReq) body_;
          }
          return LoginReqOuterClass.LoginReq.getDefaultInstance();
        } else {
          if (bodyCase_ == 3) {
            return loginReqBuilder_.getMessage();
          }
          return LoginReqOuterClass.LoginReq.getDefaultInstance();
        }
      }
      /**
       * <code>.LoginReq loginReq = 3;</code>
       */
      public Builder setLoginReq(LoginReqOuterClass.LoginReq value) {
        if (loginReqBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          body_ = value;
          onChanged();
        } else {
          loginReqBuilder_.setMessage(value);
        }
        bodyCase_ = 3;
        return this;
      }
      /**
       * <code>.LoginReq loginReq = 3;</code>
       */
      public Builder setLoginReq(
          LoginReqOuterClass.LoginReq.Builder builderForValue) {
        if (loginReqBuilder_ == null) {
          body_ = builderForValue.build();
          onChanged();
        } else {
          loginReqBuilder_.setMessage(builderForValue.build());
        }
        bodyCase_ = 3;
        return this;
      }
      /**
       * <code>.LoginReq loginReq = 3;</code>
       */
      public Builder mergeLoginReq(LoginReqOuterClass.LoginReq value) {
        if (loginReqBuilder_ == null) {
          if (bodyCase_ == 3 &&
              body_ != LoginReqOuterClass.LoginReq.getDefaultInstance()) {
            body_ = LoginReqOuterClass.LoginReq.newBuilder((LoginReqOuterClass.LoginReq) body_)
                .mergeFrom(value).buildPartial();
          } else {
            body_ = value;
          }
          onChanged();
        } else {
          if (bodyCase_ == 3) {
            loginReqBuilder_.mergeFrom(value);
          } else {
            loginReqBuilder_.setMessage(value);
          }
        }
        bodyCase_ = 3;
        return this;
      }
      /**
       * <code>.LoginReq loginReq = 3;</code>
       */
      public Builder clearLoginReq() {
        if (loginReqBuilder_ == null) {
          if (bodyCase_ == 3) {
            bodyCase_ = 0;
            body_ = null;
            onChanged();
          }
        } else {
          if (bodyCase_ == 3) {
            bodyCase_ = 0;
            body_ = null;
          }
          loginReqBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>.LoginReq loginReq = 3;</code>
       */
      public LoginReqOuterClass.LoginReq.Builder getLoginReqBuilder() {
        return getLoginReqFieldBuilder().getBuilder();
      }
      /**
       * <code>.LoginReq loginReq = 3;</code>
       */
      @Override
      public LoginReqOuterClass.LoginReqOrBuilder getLoginReqOrBuilder() {
        if ((bodyCase_ == 3) && (loginReqBuilder_ != null)) {
          return loginReqBuilder_.getMessageOrBuilder();
        } else {
          if (bodyCase_ == 3) {
            return (LoginReqOuterClass.LoginReq) body_;
          }
          return LoginReqOuterClass.LoginReq.getDefaultInstance();
        }
      }
      /**
       * <code>.LoginReq loginReq = 3;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          LoginReqOuterClass.LoginReq, LoginReqOuterClass.LoginReq.Builder, LoginReqOuterClass.LoginReqOrBuilder> 
          getLoginReqFieldBuilder() {
        if (loginReqBuilder_ == null) {
          if (!(bodyCase_ == 3)) {
            body_ = LoginReqOuterClass.LoginReq.getDefaultInstance();
          }
          loginReqBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              LoginReqOuterClass.LoginReq, LoginReqOuterClass.LoginReq.Builder, LoginReqOuterClass.LoginReqOrBuilder>(
                  (LoginReqOuterClass.LoginReq) body_,
                  getParentForChildren(),
                  isClean());
          body_ = null;
        }
        bodyCase_ = 3;
        onChanged();
        return loginReqBuilder_;
      }

      private com.google.protobuf.SingleFieldBuilderV3<
          EmptyOuterClass.Empty, EmptyOuterClass.Empty.Builder, EmptyOuterClass.EmptyOrBuilder> emptyBuilder_;
      /**
       * <code>.Empty empty = 4;</code>
       * @return Whether the empty field is set.
       */
      @Override
      public boolean hasEmpty() {
        return bodyCase_ == 4;
      }
      /**
       * <code>.Empty empty = 4;</code>
       * @return The empty.
       */
      @Override
      public EmptyOuterClass.Empty getEmpty() {
        if (emptyBuilder_ == null) {
          if (bodyCase_ == 4) {
            return (EmptyOuterClass.Empty) body_;
          }
          return EmptyOuterClass.Empty.getDefaultInstance();
        } else {
          if (bodyCase_ == 4) {
            return emptyBuilder_.getMessage();
          }
          return EmptyOuterClass.Empty.getDefaultInstance();
        }
      }
      /**
       * <code>.Empty empty = 4;</code>
       */
      public Builder setEmpty(EmptyOuterClass.Empty value) {
        if (emptyBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          body_ = value;
          onChanged();
        } else {
          emptyBuilder_.setMessage(value);
        }
        bodyCase_ = 4;
        return this;
      }
      /**
       * <code>.Empty empty = 4;</code>
       */
      public Builder setEmpty(
          EmptyOuterClass.Empty.Builder builderForValue) {
        if (emptyBuilder_ == null) {
          body_ = builderForValue.build();
          onChanged();
        } else {
          emptyBuilder_.setMessage(builderForValue.build());
        }
        bodyCase_ = 4;
        return this;
      }
      /**
       * <code>.Empty empty = 4;</code>
       */
      public Builder mergeEmpty(EmptyOuterClass.Empty value) {
        if (emptyBuilder_ == null) {
          if (bodyCase_ == 4 &&
              body_ != EmptyOuterClass.Empty.getDefaultInstance()) {
            body_ = EmptyOuterClass.Empty.newBuilder((EmptyOuterClass.Empty) body_)
                .mergeFrom(value).buildPartial();
          } else {
            body_ = value;
          }
          onChanged();
        } else {
          if (bodyCase_ == 4) {
            emptyBuilder_.mergeFrom(value);
          } else {
            emptyBuilder_.setMessage(value);
          }
        }
        bodyCase_ = 4;
        return this;
      }
      /**
       * <code>.Empty empty = 4;</code>
       */
      public Builder clearEmpty() {
        if (emptyBuilder_ == null) {
          if (bodyCase_ == 4) {
            bodyCase_ = 0;
            body_ = null;
            onChanged();
          }
        } else {
          if (bodyCase_ == 4) {
            bodyCase_ = 0;
            body_ = null;
          }
          emptyBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>.Empty empty = 4;</code>
       */
      public EmptyOuterClass.Empty.Builder getEmptyBuilder() {
        return getEmptyFieldBuilder().getBuilder();
      }
      /**
       * <code>.Empty empty = 4;</code>
       */
      @Override
      public EmptyOuterClass.EmptyOrBuilder getEmptyOrBuilder() {
        if ((bodyCase_ == 4) && (emptyBuilder_ != null)) {
          return emptyBuilder_.getMessageOrBuilder();
        } else {
          if (bodyCase_ == 4) {
            return (EmptyOuterClass.Empty) body_;
          }
          return EmptyOuterClass.Empty.getDefaultInstance();
        }
      }
      /**
       * <code>.Empty empty = 4;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          EmptyOuterClass.Empty, EmptyOuterClass.Empty.Builder, EmptyOuterClass.EmptyOrBuilder> 
          getEmptyFieldBuilder() {
        if (emptyBuilder_ == null) {
          if (!(bodyCase_ == 4)) {
            body_ = EmptyOuterClass.Empty.getDefaultInstance();
          }
          emptyBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              EmptyOuterClass.Empty, EmptyOuterClass.Empty.Builder, EmptyOuterClass.EmptyOrBuilder>(
                  (EmptyOuterClass.Empty) body_,
                  getParentForChildren(),
                  isClean());
          body_ = null;
        }
        bodyCase_ = 4;
        onChanged();
        return emptyBuilder_;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:Message)
    }

    // @@protoc_insertion_point(class_scope:Message)
    private static final Message DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Message();
    }

    public static Message getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Message>
        PARSER = new com.google.protobuf.AbstractParser<Message>() {
      @Override
      public Message parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<Message> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Message> getParserForType() {
      return PARSER;
    }

    @Override
    public Message getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Message_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Message_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\rMessage.proto\032\016LoginRes.proto\032\016LoginRe" +
      "q.proto\032\013empty.proto\"y\n\007Message\022\017\n\007comma" +
      "nd\030\001 \001(\003\022\035\n\010loginRes\030\002 \001(\0132\t.LoginResH\000\022" +
      "\035\n\010loginReq\030\003 \001(\0132\t.LoginReqH\000\022\027\n\005empty\030" +
      "\004 \001(\0132\006.EmptyH\000B\006\n\004bodyb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          LoginResOuterClass.getDescriptor(),
          LoginReqOuterClass.getDescriptor(),
          EmptyOuterClass.getDescriptor(),
        });
    internal_static_Message_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Message_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Message_descriptor,
        new String[] { "Command", "LoginRes", "LoginReq", "Empty", "Body", });
    LoginResOuterClass.getDescriptor();
    LoginReqOuterClass.getDescriptor();
    EmptyOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
