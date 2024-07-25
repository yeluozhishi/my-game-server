package com.whk.listener.listener;

public interface IEventListener<T> {

    void dealEvent(T event);
}
