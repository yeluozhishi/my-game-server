package com.whk.util;

enum SingletonDemo{
    INSTANCE;

    private Singleton singleton;

    SingletonDemo(){
        singleton = new Singleton();
    }

    public void otherMethods(){
        singleton.doSomething();
    }

    private class Singleton{

        public void doSomething(){
            System.out.println("d");
        }
    }
}

