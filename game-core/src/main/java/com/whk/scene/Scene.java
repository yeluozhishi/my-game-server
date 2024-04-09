package com.whk.scene;

public class Scene implements SceneInterface{
    @Override
    public String SceneId() {
        return "11";
    }

    public void tick(){
        synchronized (this){
            try {
                this.wait(1000);
                System.out.println(SceneId() + "tick finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
