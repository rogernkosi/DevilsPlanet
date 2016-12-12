package nkosi.roger.manutdcom;

/**
 * Created by Roger on 12/6/2016.
 */

public class HeadlinesModel {
    String headline, img, details, hId, source;

    public HeadlinesModel(HeadlineBuilder builder) {
        this.details = builder.details;
        this.headline = builder.headline;
        this.img = builder.img;
        this.hId = builder.hId;
        this.source = builder.source;
    }

    public static class HeadlineBuilder{
        String headline, img, details, hId, source;

        public HeadlineBuilder setSource(String source){
            this.source = source;
            return HeadlineBuilder.this;
        }

        public HeadlineBuilder setHeadline(String headline){
            this.headline = headline;
            return HeadlineBuilder.this;
        }

        public HeadlineBuilder setImgUri(String uri){
            this.img = uri;
            return HeadlineBuilder.this;
        }

        public HeadlineBuilder setDetails(String details){
            this.details = details;
            return HeadlineBuilder.this;
        }

        public HeadlineBuilder setHID(String hId){
            this.hId = hId;
            return HeadlineBuilder.this;
        }

        public HeadlinesModel buildHeadLines(){
            return new HeadlinesModel(HeadlineBuilder.this);
        }
    }
}
