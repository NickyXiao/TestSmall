/**
  * Copyright 2017 bejson.com 
  */
package test_small.senble.china.com.appbank_repository.beans;
import java.util.List;

/**
 * Auto-generated: 2017-08-29 18:56:43
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Result {

    private MonthReport monthReport;
    private List<RecommendMap> recommendMap;
    private NewComerProduct newComerProduct;
    private List<TopTitle> topTitle;
    private List<BannerMap> bannerMap;
//    private List<String> mediaReportMap;
    public void setMonthReport(MonthReport monthReport) {
         this.monthReport = monthReport;
     }
     public MonthReport getMonthReport() {
         return monthReport;
     }

    public void setRecommendMap(List<RecommendMap> recommendMap) {
         this.recommendMap = recommendMap;
     }
     public List<RecommendMap> getRecommendMap() {
         return recommendMap;
     }

    public void setNewComerProduct(NewComerProduct newComerProduct) {
         this.newComerProduct = newComerProduct;
     }
     public NewComerProduct getNewComerProduct() {
         return newComerProduct;
     }

    public void setTopTitle(List<TopTitle> topTitle) {
         this.topTitle = topTitle;
     }
     public List<TopTitle> getTopTitle() {
         return topTitle;
     }

    public void setBannerMap(List<BannerMap> bannerMap) {
         this.bannerMap = bannerMap;
     }
     public List<BannerMap> getBannerMap() {
         return bannerMap;
     }

//    public void setMediaReportMap(List<String> mediaReportMap) {
//         this.mediaReportMap = mediaReportMap;
//     }
//     public List<String> getMediaReportMap() {
//         return mediaReportMap;
//     }

}