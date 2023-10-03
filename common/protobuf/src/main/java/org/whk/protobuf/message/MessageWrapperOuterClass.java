package org.whk.protobuf.message;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MessageWrapper.proto

public final class MessageWrapperOuterClass {
  private MessageWrapperOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MessageWrapperOrBuilder extends
      // @@protoc_insertion_point(interface_extends:MessageWrapper)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string serverInstance = 1;</code>
     * @return The serverInstance.
     */
    String getServerInstance();
    /**
     * <code>string serverInstance = 1;</code>
     * @return The bytes for serverInstance.
     */
    com.google.protobuf.ByteString
        getServerInstanceBytes();

    /**
     * <code>int64 playerId = 2;</code>
     * @return The playerId.
     */
    long getPlayerId();

    /**
     * <code>int64 userId = 3;</code>
     * @return The userId.
     */
    long getUserId();

    /**
     * <code>.Message message = 4;</code>
     * @return Whether the message field is set.
     */
    boolean hasMessage();
    /**
     * <code>.Message message = 4;</code>
     * @return The message.
     */
    MessageOuterClass.Message getMessage();
    /**
     * <code>.Message message = 4;</code>
     */
    MessageOuterClass.MessageOrBuilder getMessageOrBuilder();
  }
  /**
   * Protobuf type {@code MessageWrapper}
   */
  public static final class MessageWrapper extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:MessageWrapper)
      MessageWrapperOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MessageWrapper.newBuilder() to construct.
    private MessageWrapper(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MessageWrapper() {
      serverInstance_ = "";
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new MessageWrapper();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return MessageWrapperOuterClass.internal_static_MessageWrapper_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return MessageWrapperOuterClass.internal_static_MessageWrapper_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              MessageWrapper.class, Builder.class);
    }

    private int bitField0_;
    public static final int SERVERINSTANCE_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private volatile Object serverInstance_ = "";
    /**
     * <code>string serverInstance = 1;</code>
     * @return The serverInstance.
     */
    @Override
    public String getServerInstance() {
      Object ref = serverInstance_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        serverInstance_ = s;
        return s;
      }
    }
    /**
     * <code>string serverInstance = 1;</code>
     * @return The bytes for serverInstance.
     */
    @Override
    public com.google.protobuf.ByteString
        getServerInstanceBytes() {
      Object ref = serverInstance_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        serverInstance_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PLAYERID_FIELD_NUMBER = 2;
    private long playerId_ = 0L;
    /**
     * <code>int64 playerId = 2;</code>
     * @return The playerId.
     */
    @Override
    public long getPlayerId() {
      return playerId_;
    }

    public static final int USERID_FIELD_NUMBER = 3;
    private long userId_ = 0L;
    /**
     * <code>int64 userId = 3;</code>
     * @return The userId.
     */
    @Override
    public long getUserId() {
      return userId_;
    }

    public static final int MESSAGE_FIELD_NUMBER = 4;
    private MessageOuterClass.Message message_;
    /**
     * <code>.Message message = 4;</code>
     * @return Whether the message field is set.
     */
    @Override
    public boolean hasMessage() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>.Message message = 4;</code>
     * @return The message.
     */
    @Override
    public MessageOuterClass.Message getMessage() {
      return message_ == null ? MessageOuterClass.Message.getDefaultInstance() : message_;
    }
    /**
     * <code>.Message message = 4;</code>
     */
    @Override
    public MessageOuterClass.MessageOrBuilder getMessageOrBuilder() {
      return message_ == null ? MessageOuterClass.Message.getDefaultInstance() : message_;
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
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(serverInstance_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, serverInstance_);
      }
      if (playerId_ != 0L) {
        output.writeInt64(2, playerId_);
      }
      if (userId_ != 0L) {
        output.writeInt64(3, userId_);
      }
      if (((bitField0_ & 0x00000001) != 0)) {
        output.writeMessage(4, getMessage());
      }
      getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(serverInstance_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, serverInstance_);
      }
      if (playerId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(2, playerId_);
      }
      if (userId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(3, userId_);
      }
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(4, getMessage());
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
      if (!(obj instanceof MessageWrapper)) {
        return super.equals(obj);
      }
      MessageWrapper other = (MessageWrapper) obj;

      if (!getServerInstance()
          .equals(other.getServerInstance())) return false;
      if (getPlayerId()
          != other.getPlayerId()) return false;
      if (getUserId()
          != other.getUserId()) return false;
      if (hasMessage() != other.hasMessage()) return false;
      if (hasMessage()) {
        if (!getMessage()
            .equals(other.getMessage())) return false;
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
      hash = (37 * hash) + SERVERINSTANCE_FIELD_NUMBER;
      hash = (53 * hash) + getServerInstance().hashCode();
      hash = (37 * hash) + PLAYERID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getPlayerId());
      hash = (37 * hash) + USERID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getUserId());
      if (hasMessage()) {
        hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
        hash = (53 * hash) + getMessage().hashCode();
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static MessageWrapper parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static MessageWrapper parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static MessageWrapper parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static MessageWrapper parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static MessageWrapper parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static MessageWrapper parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static MessageWrapper parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static MessageWrapper parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static MessageWrapper parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static MessageWrapper parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static MessageWrapper parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static MessageWrapper parseFrom(
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
    public static Builder newBuilder(MessageWrapper prototype) {
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
     * Protobuf type {@code MessageWrapper}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:MessageWrapper)
        MessageWrapperOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return MessageWrapperOuterClass.internal_static_MessageWrapper_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return MessageWrapperOuterClass.internal_static_MessageWrapper_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                MessageWrapper.class, Builder.class);
      }

      // Construct using MessageWrapperOuterClass.MessageWrapper.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
          getMessageFieldBuilder();
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        serverInstance_ = "";
        playerId_ = 0L;
        userId_ = 0L;
        message_ = null;
        if (messageBuilder_ != null) {
          messageBuilder_.dispose();
          messageBuilder_ = null;
        }
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return MessageWrapperOuterClass.internal_static_MessageWrapper_descriptor;
      }

      @Override
      public MessageWrapper getDefaultInstanceForType() {
        return MessageWrapper.getDefaultInstance();
      }

      @Override
      public MessageWrapper build() {
        MessageWrapper result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public MessageWrapper buildPartial() {
        MessageWrapper result = new MessageWrapper(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(MessageWrapper result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.serverInstance_ = serverInstance_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.playerId_ = playerId_;
        }
        if (((from_bitField0_ & 0x00000004) != 0)) {
          result.userId_ = userId_;
        }
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000008) != 0)) {
          result.message_ = messageBuilder_ == null
              ? message_
              : messageBuilder_.build();
          to_bitField0_ |= 0x00000001;
        }
        result.bitField0_ |= to_bitField0_;
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
        if (other instanceof MessageWrapper) {
          return mergeFrom((MessageWrapper)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(MessageWrapper other) {
        if (other == MessageWrapper.getDefaultInstance()) return this;
        if (!other.getServerInstance().isEmpty()) {
          serverInstance_ = other.serverInstance_;
          bitField0_ |= 0x00000001;
          onChanged();
        }
        if (other.getPlayerId() != 0L) {
          setPlayerId(other.getPlayerId());
        }
        if (other.getUserId() != 0L) {
          setUserId(other.getUserId());
        }
        if (other.hasMessage()) {
          mergeMessage(other.getMessage());
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
              case 10: {
                serverInstance_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              case 16: {
                playerId_ = input.readInt64();
                bitField0_ |= 0x00000002;
                break;
              } // case 16
              case 24: {
                userId_ = input.readInt64();
                bitField0_ |= 0x00000004;
                break;
              } // case 24
              case 34: {
                input.readMessage(
                    getMessageFieldBuilder().getBuilder(),
                    extensionRegistry);
                bitField0_ |= 0x00000008;
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
      private int bitField0_;

      private Object serverInstance_ = "";
      /**
       * <code>string serverInstance = 1;</code>
       * @return The serverInstance.
       */
      public String getServerInstance() {
        Object ref = serverInstance_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          serverInstance_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string serverInstance = 1;</code>
       * @return The bytes for serverInstance.
       */
      public com.google.protobuf.ByteString
          getServerInstanceBytes() {
        Object ref = serverInstance_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          serverInstance_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string serverInstance = 1;</code>
       * @param value The serverInstance to set.
       * @return This builder for chaining.
       */
      public Builder setServerInstance(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        serverInstance_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>string serverInstance = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearServerInstance() {
        serverInstance_ = getDefaultInstance().getServerInstance();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>string serverInstance = 1;</code>
       * @param value The bytes for serverInstance to set.
       * @return This builder for chaining.
       */
      public Builder setServerInstanceBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        serverInstance_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      private long playerId_ ;
      /**
       * <code>int64 playerId = 2;</code>
       * @return The playerId.
       */
      @Override
      public long getPlayerId() {
        return playerId_;
      }
      /**
       * <code>int64 playerId = 2;</code>
       * @param value The playerId to set.
       * @return This builder for chaining.
       */
      public Builder setPlayerId(long value) {

        playerId_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>int64 playerId = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearPlayerId() {
        bitField0_ = (bitField0_ & ~0x00000002);
        playerId_ = 0L;
        onChanged();
        return this;
      }

      private long userId_ ;
      /**
       * <code>int64 userId = 3;</code>
       * @return The userId.
       */
      @Override
      public long getUserId() {
        return userId_;
      }
      /**
       * <code>int64 userId = 3;</code>
       * @param value The userId to set.
       * @return This builder for chaining.
       */
      public Builder setUserId(long value) {

        userId_ = value;
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }
      /**
       * <code>int64 userId = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearUserId() {
        bitField0_ = (bitField0_ & ~0x00000004);
        userId_ = 0L;
        onChanged();
        return this;
      }

      private MessageOuterClass.Message message_;
      private com.google.protobuf.SingleFieldBuilderV3<
          MessageOuterClass.Message, MessageOuterClass.Message.Builder, MessageOuterClass.MessageOrBuilder> messageBuilder_;
      /**
       * <code>.Message message = 4;</code>
       * @return Whether the message field is set.
       */
      public boolean hasMessage() {
        return ((bitField0_ & 0x00000008) != 0);
      }
      /**
       * <code>.Message message = 4;</code>
       * @return The message.
       */
      public MessageOuterClass.Message getMessage() {
        if (messageBuilder_ == null) {
          return message_ == null ? MessageOuterClass.Message.getDefaultInstance() : message_;
        } else {
          return messageBuilder_.getMessage();
        }
      }
      /**
       * <code>.Message message = 4;</code>
       */
      public Builder setMessage(MessageOuterClass.Message value) {
        if (messageBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          message_ = value;
        } else {
          messageBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000008;
        onChanged();
        return this;
      }
      /**
       * <code>.Message message = 4;</code>
       */
      public Builder setMessage(
          MessageOuterClass.Message.Builder builderForValue) {
        if (messageBuilder_ == null) {
          message_ = builderForValue.build();
        } else {
          messageBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000008;
        onChanged();
        return this;
      }
      /**
       * <code>.Message message = 4;</code>
       */
      public Builder mergeMessage(MessageOuterClass.Message value) {
        if (messageBuilder_ == null) {
          if (((bitField0_ & 0x00000008) != 0) &&
            message_ != null &&
            message_ != MessageOuterClass.Message.getDefaultInstance()) {
            getMessageBuilder().mergeFrom(value);
          } else {
            message_ = value;
          }
        } else {
          messageBuilder_.mergeFrom(value);
        }
        if (message_ != null) {
          bitField0_ |= 0x00000008;
          onChanged();
        }
        return this;
      }
      /**
       * <code>.Message message = 4;</code>
       */
      public Builder clearMessage() {
        bitField0_ = (bitField0_ & ~0x00000008);
        message_ = null;
        if (messageBuilder_ != null) {
          messageBuilder_.dispose();
          messageBuilder_ = null;
        }
        onChanged();
        return this;
      }
      /**
       * <code>.Message message = 4;</code>
       */
      public MessageOuterClass.Message.Builder getMessageBuilder() {
        bitField0_ |= 0x00000008;
        onChanged();
        return getMessageFieldBuilder().getBuilder();
      }
      /**
       * <code>.Message message = 4;</code>
       */
      public MessageOuterClass.MessageOrBuilder getMessageOrBuilder() {
        if (messageBuilder_ != null) {
          return messageBuilder_.getMessageOrBuilder();
        } else {
          return message_ == null ?
              MessageOuterClass.Message.getDefaultInstance() : message_;
        }
      }
      /**
       * <code>.Message message = 4;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          MessageOuterClass.Message, MessageOuterClass.Message.Builder, MessageOuterClass.MessageOrBuilder> 
          getMessageFieldBuilder() {
        if (messageBuilder_ == null) {
          messageBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              MessageOuterClass.Message, MessageOuterClass.Message.Builder, MessageOuterClass.MessageOrBuilder>(
                  getMessage(),
                  getParentForChildren(),
                  isClean());
          message_ = null;
        }
        return messageBuilder_;
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


      // @@protoc_insertion_point(builder_scope:MessageWrapper)
    }

    // @@protoc_insertion_point(class_scope:MessageWrapper)
    private static final MessageWrapper DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new MessageWrapper();
    }

    public static MessageWrapper getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MessageWrapper>
        PARSER = new com.google.protobuf.AbstractParser<MessageWrapper>() {
      @Override
      public MessageWrapper parsePartialFrom(
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

    public static com.google.protobuf.Parser<MessageWrapper> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<MessageWrapper> getParserForType() {
      return PARSER;
    }

    @Override
    public MessageWrapper getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MessageWrapper_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MessageWrapper_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\024MessageWrapper.proto\032\rMessage.proto\"e\n" +
      "\016MessageWrapper\022\026\n\016serverInstance\030\001 \001(\t\022" +
      "\020\n\010playerId\030\002 \001(\003\022\016\n\006userId\030\003 \001(\003\022\031\n\007mes" +
      "sage\030\004 \001(\0132\010.Messageb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          MessageOuterClass.getDescriptor(),
        });
    internal_static_MessageWrapper_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_MessageWrapper_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MessageWrapper_descriptor,
        new String[] { "ServerInstance", "PlayerId", "UserId", "Message", });
    MessageOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
