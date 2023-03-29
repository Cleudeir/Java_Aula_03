public enum Description {
    MUITO_BOM("Muito bom"),
    MAIS_OU_MENOS("Mais ou menos"),
    EHHHHHH("Ehhhh!");

    private String desc;
    
    private Description(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return desc;
    }
}
