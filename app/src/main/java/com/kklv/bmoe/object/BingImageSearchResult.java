package com.kklv.bmoe.object;

import java.io.Serializable;
import java.util.List;

/**
 * 必应图片搜索返回结果
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/13 11:01
 */
public class BingImageSearchResult implements Serializable {
    public static final int VERSION_CODE = 3;

    /**
     * 偏移
     */
    private int index = 0;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 下一张图片Url
     */
    public String next() {
        if (index < getValue().size() - 1) {
            index++;
        } else {
            index = 0;
        }

        return getIndexUrl();
    }

    /**
     * 获取图片Url
     *
     * @return
     */
    public String getIndexUrl() {
        return getValue().get(index).getContentUrl();
    }

    private String _type;

    private InstrumentationBean instrumentation;
    private String webSearchUrl;
    private int totalEstimatedMatches;
    private int nextOffsetAddCount;
    private boolean displayShoppingSourcesBadges;
    private boolean displayRecipeSourcesBadges;

    private List<ValueBean> value;

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public InstrumentationBean getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(InstrumentationBean instrumentation) {
        this.instrumentation = instrumentation;
    }

    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

    public int getTotalEstimatedMatches() {
        return totalEstimatedMatches;
    }

    public void setTotalEstimatedMatches(int totalEstimatedMatches) {
        this.totalEstimatedMatches = totalEstimatedMatches;
    }

    public int getNextOffsetAddCount() {
        return nextOffsetAddCount;
    }

    public void setNextOffsetAddCount(int nextOffsetAddCount) {
        this.nextOffsetAddCount = nextOffsetAddCount;
    }

    public boolean isDisplayShoppingSourcesBadges() {
        return displayShoppingSourcesBadges;
    }

    public void setDisplayShoppingSourcesBadges(boolean displayShoppingSourcesBadges) {
        this.displayShoppingSourcesBadges = displayShoppingSourcesBadges;
    }

    public boolean isDisplayRecipeSourcesBadges() {
        return displayRecipeSourcesBadges;
    }

    public void setDisplayRecipeSourcesBadges(boolean displayRecipeSourcesBadges) {
        this.displayRecipeSourcesBadges = displayRecipeSourcesBadges;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class InstrumentationBean implements Serializable {
        private String pageLoadPingUrl;

        public String getPageLoadPingUrl() {
            return pageLoadPingUrl;
        }

        public void setPageLoadPingUrl(String pageLoadPingUrl) {
            this.pageLoadPingUrl = pageLoadPingUrl;
        }
    }

    public static class ValueBean implements Serializable {
        private String name;
        private String webSearchUrl;
        private String thumbnailUrl;
        private String datePublished;
        private String contentUrl;
        private String hostPageUrl;
        private String contentSize;
        private String encodingFormat;
        private String hostPageDisplayUrl;
        private int width;
        private int height;
        /**
         * width : 211
         * height : 300
         */

        private ThumbnailBean thumbnail;
        private String imageInsightsToken;
        private String imageId;
        private String accentColor;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWebSearchUrl() {
            return webSearchUrl;
        }

        public void setWebSearchUrl(String webSearchUrl) {
            this.webSearchUrl = webSearchUrl;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getDatePublished() {
            return datePublished;
        }

        public void setDatePublished(String datePublished) {
            this.datePublished = datePublished;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public String getHostPageUrl() {
            return hostPageUrl;
        }

        public void setHostPageUrl(String hostPageUrl) {
            this.hostPageUrl = hostPageUrl;
        }

        public String getContentSize() {
            return contentSize;
        }

        public void setContentSize(String contentSize) {
            this.contentSize = contentSize;
        }

        public String getEncodingFormat() {
            return encodingFormat;
        }

        public void setEncodingFormat(String encodingFormat) {
            this.encodingFormat = encodingFormat;
        }

        public String getHostPageDisplayUrl() {
            return hostPageDisplayUrl;
        }

        public void setHostPageDisplayUrl(String hostPageDisplayUrl) {
            this.hostPageDisplayUrl = hostPageDisplayUrl;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public ThumbnailBean getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(ThumbnailBean thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getImageInsightsToken() {
            return imageInsightsToken;
        }

        public void setImageInsightsToken(String imageInsightsToken) {
            this.imageInsightsToken = imageInsightsToken;
        }

        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getAccentColor() {
            return accentColor;
        }

        public void setAccentColor(String accentColor) {
            this.accentColor = accentColor;
        }

        public static class ThumbnailBean implements Serializable {
            private int width;
            private int height;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
