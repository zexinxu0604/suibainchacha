public class ImageMessage {

    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String PicUrl;
    private String ReceiveMediaId;
    private String MsgId;
    private ImageBean Image;

    public ImageBean getImage() {
        return Image;
    }

    public void setImage(ImageBean image) {
        this.Image = image;
    }

    public static class ImageBean {


        public String getMediaId() {
            return MediaId;
        }

        public void setMediaId(String mediaId) {
            MediaId = mediaId;
        }

        ImageBean(String MediaId) {
            this.MediaId = MediaId;
        }

        private String MediaId;

    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String ToUserName) {
        this.ToUserName = ToUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String FromUserName) {
        this.FromUserName = FromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String MsgType) {
        this.MsgType = MsgType;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String PicUrl) {
        this.PicUrl = PicUrl;
    }

    public String getReceiveMediaId() {
        return ReceiveMediaId;
    }

    public void setReceiveMediaId(String MediaId) {
        this.ReceiveMediaId = MediaId;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String MsgId) {
        this.MsgId = MsgId;
    }
}
