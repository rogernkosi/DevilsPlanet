package nkosi.roger.manutdcom.model;

/**
 * Created by MPHILE on 1/9/2017.
 */

public class BlogModel {
    public String title, content, time;

    public BlogModel(BuildBlog blog) {
        this.time = blog.time;
        this.title = blog.title;
        this.content = blog.content;
    }

    public static class BuildBlog{
        public String title, content, time;

        public BuildBlog setTitle(String title){
            this.title = title;
            return BuildBlog.this;
        }

        public BuildBlog setContent(String content){
            this.content = content;
            return BuildBlog.this;
        }

        public BuildBlog setTime(String time){
            this.time = time;
            return BuildBlog.this;
        }

        public BlogModel buildBlog(){
            return new BlogModel(BuildBlog.this);
        }
    }
}
