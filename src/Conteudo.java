public record Conteudo (String title, String urlImage, String font, String description ) {
    
    public String getTitle() {
        return title;
    }

    public String getUrlImage() {
        return urlImage;
    }

     public String getDescription() {
        return description;
    }  

    public String getFont() {
        return font;
    }  

}
