package com.lmm.task.entity.dept;

public enum LevelAndName {
    TOWNSHIP(0),
    VILLAGE(1),
    SOCIETY(2),
    HOUSEHOLD(3),
    ;
    private Integer level;
    private String name;

    LevelAndName(Integer level) {
        this.level = level;
        switch (level){
            case 0:this.name = "乡镇";
            break;
            case 1:this.name = "村";
            break;
            case 2:this.name = "社";
                break;
            case 3:this.name = "户";
                break;
        }
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
