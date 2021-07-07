package cn.mayu.yugioh.skywalking.amp.plugin.resilience4j;

public class FeignResolvedURL {
    /**
     * url before resolved
     */
    private String originUrl;
    /**
     * url after resolved
     */
    private String url;

    public FeignResolvedURL(String originUrl) {
        this.originUrl = originUrl;
    }

    public FeignResolvedURL(String originUrl, String url) {
        this.originUrl = originUrl;
        this.url = url;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}