package com.kklv.bmoe.object;

import java.util.List;

/**
 *必应图片搜索返回结果
 * 
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/13 11:00
 */
public class BingImageSearchResult {

    /**
     * _type : Images
     * instrumentation : {"pageLoadPingUrl":"https://www.bingapis.com/api/ping/pageload?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&Type=Event.CPT&DATA=0"}
     * webSearchUrl : https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=1IwtMoefyJF6I8P0yjlvEI_hn_BaqEaRg6XX0p79dl8&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fq%3dfate%2520stay%2520night%26FORM%3dOIIARP&p=DevEx,5074.1
     * totalEstimatedMatches : 1000
     * value : [{"name":"Fate/stay night - Wikipedia, the free ...","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=qyY9GjukDOfZ661cOnrWimt5L238pLpmMSkaCo8lpSE&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3dB0D94F4CA3E382460AA61FA3156AFE91649CD787%26simid%3d608001185015335343&p=DevEx,5006.1","thumbnailUrl":"https://tse2-mm.cn.bing.net/th?id=OIP.M519191f9d3b31ca4e0239f86f23e392do0&pid=Api","datePublished":"2016-04-28T21:50:00","contentUrl":"https://upload.wikimedia.org/wikipedia/en/d/d4/Fate-stay_night.jpg","hostPageUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=YA0LRUl6sIHXtu-t4swymnxBH8-dZr8X3o2eJZwX0S8&v=1&r=https%3a%2f%2fen.wikipedia.org%2fwiki%2fFate%2fstay_night&p=DevEx,5007.1","contentSize":"83724 B","encodingFormat":"jpeg","hostPageDisplayUrl":"https://en.wikipedia.org/wiki/Fate/stay_night","width":230,"height":326,"thumbnail":{"width":211,"height":300},"imageInsightsToken":"ccid_UZGR+dOz*mid_B0D94F4CA3E382460AA61FA3156AFE91649CD787*simid_608001185015335343","imageId":"B0D94F4CA3E382460AA61FA3156AFE91649CD787","accentColor":"C30713"},{"name":"这是fate stay night哪里的_百度 ...","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=tvt4Ri_VDLya45KtZPMxXN0fRFK4gaIY9WgEHoDQlbI&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3d3CEB41FBD1F6F41D0DCDA368C8E3D55D4A0DA83C%26simid%3d608010724138615647&p=DevEx,5012.1","thumbnailUrl":"https://tse3-mm.cn.bing.net/th?id=OIP.Md680ddb189df3cbb2b8616e0970f6327o0&pid=Api","datePublished":"2014-04-01T16:33:00","contentUrl":"http://c.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=e21cae5ea5efce1bea7ec0cc9f61dfe6/0d338744ebf81a4c23fc0161d52a6059242da648.jpg","hostPageUrl":"http://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=v6Sa__pSwdEv1aKx-lb3zUP2Ho1C6OjXWIhuzpsF_u4&v=1&r=http%3a%2f%2fzhidao.baidu.com%2fquestion%2f1174448672951633819.html&p=DevEx,5013.1","contentSize":"2974531 B","encodingFormat":"animatedgif","hostPageDisplayUrl":"zhidao.baidu.com/question/1174448672951633819.html","width":444,"height":250,"thumbnail":{"width":300,"height":168},"imageInsightsToken":"ccid_1oDdsYnf*mid_3CEB41FBD1F6F41D0DCDA368C8E3D55D4A0DA83C*simid_608010724138615647","imageId":"3CEB41FBD1F6F41D0DCDA368C8E3D55D4A0DA83C","accentColor":"CB8F00"},{"name":"Fate/stay night \u2022 Anime","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=GaIlDjhzdCSM6Jj9YBz9xFQeq5VLZo3Tx9sqv0qQWsI&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3d749E7310EF9C6590C6C02F2C493BE5765AE89254%26simid%3d608047854129908793&p=DevEx,5018.1","thumbnailUrl":"https://tse4-mm.cn.bing.net/th?id=OIP.Mc672e729375f5af41452de212f7d5991o0&pid=Api","datePublished":"2010-02-10T13:43:00","contentUrl":"http://www.animint.com/encyclopedie/base/image/f/fatestaynight.jpg","hostPageUrl":"http://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=QrDju29dZNf3-4KcW1f4ntv01Kbn4W__8NH1yzOTdjY&v=1&r=http%3a%2f%2fwww.animint.com%2fencyclopedie%2fbase%2f4_905_0-fate-stay-night.html&p=DevEx,5019.1","contentSize":"44449 B","encodingFormat":"jpeg","hostPageDisplayUrl":"www.animint.com/encyclopedie/base/4_905_0-fate-stay-night...","width":300,"height":415,"thumbnail":{"width":216,"height":300},"imageInsightsToken":"ccid_xnLnKTdf*mid_749E7310EF9C6590C6C02F2C493BE5765AE89254*simid_608047854129908793","imageId":"749E7310EF9C6590C6C02F2C493BE5765AE89254","accentColor":"BB101C"},{"name":"Fate/stay night [Unlimited Blade ...","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=kIrN_CyPB8-g2AWwTgqWT85wWz4BPuAet1HFFuumqyY&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3d5253376F9B018F11AB784B1EF2CA56E096894CAB%26simid%3d608027002068207964&p=DevEx,5024.1","thumbnailUrl":"https://tse3-mm.cn.bing.net/th?id=OIP.M608eded0caad850fbc4cde94b084f9d9o0&pid=Api","datePublished":"2015-12-14T22:30:00","contentUrl":"http://www.moview.jp/wp-content/uploads/2015/04/fatestaynight2-3.jpg","hostPageUrl":"http://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=viRuMxKNSvE4O-Zk4JByufFUAZQ5U6-gbHjBDtFUrSI&v=1&r=http%3a%2f%2fwww.moview.jp%2f958926288.html&p=DevEx,5025.1","contentSize":"76835 B","encodingFormat":"jpeg","hostPageDisplayUrl":"www.moview.jp/958926288.html","width":300,"height":421,"thumbnail":{"width":213,"height":300},"imageInsightsToken":"ccid_YI7e0Mqt*mid_5253376F9B018F11AB784B1EF2CA56E096894CAB*simid_608027002068207964","imageId":"5253376F9B018F11AB784B1EF2CA56E096894CAB","accentColor":"A42927"},{"name":"Fate/stay night: Unlimited Blade Works (TV ...","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=OMy3r_FAAkQXa8eGyq2tsBrKetXl5EU0Ph6m9XPGif8&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3d4593297E5AEA28BF204B14927B89A9084217889B%26simid%3d608021568937001334&p=DevEx,5030.1","thumbnailUrl":"https://tse4-mm.cn.bing.net/th?id=OIP.M5ab0b0fea975c7e106ce93b3e2e3fd06o0&pid=Api","datePublished":"2016-03-30T18:21:00","contentUrl":"http://www.gogoanime.com/images/fate_stay_night_2.jpg","hostPageUrl":"http://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=pTF8wq0NGtmUmZ3e1YeKNSGPoYBxWUwoLXxj9lnZzZo&v=1&r=http%3a%2f%2fwww.gogoanime.com%2fcategory%2ffatestay-night-unlimited-blade-works-tv-2nd-season&p=DevEx,5031.1","contentSize":"66037 B","encodingFormat":"jpeg","hostPageDisplayUrl":"www.gogoanime.com/category/fatestay-night-unlimited-blade...","width":300,"height":429,"thumbnail":{"width":209,"height":300},"imageInsightsToken":"ccid_WrCw/ql1*mid_4593297E5AEA28BF204B14927B89A9084217889B*simid_608021568937001334","imageId":"4593297E5AEA28BF204B14927B89A9084217889B","accentColor":"243C78"},{"name":"... Fate/stay night / Archer (Fate/stay night","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=zoVUIeUVEgVGQ_fwqaPGhmOcnlS_sMMwyswm9482C3k&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3d2B4B0ED4643AEB03FFE5777E3B11FF553BF4C6B5%26simid%3d607992895723211077&p=DevEx,5036.1","thumbnailUrl":"https://tse4-mm.cn.bing.net/th?id=OIP.M0ae56b83dab20bf7cf0b1503ac6727c6o0&pid=Api","datePublished":"2015-04-24T23:54:00","contentUrl":"http://s3.zerochan.net/Archer.%28Fate.stay.night%29.240.1862828.jpg","hostPageUrl":"http://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=Bz1Pg5ub2EjBJRfz6v82XKLvXisyVoRdUufjKoIqFIc&v=1&r=http%3a%2f%2fwww.zerochan.net%2fArcher%2b(Fate%2fstay%2bnight)&p=DevEx,5037.1","contentSize":"11372 B","encodingFormat":"jpeg","hostPageDisplayUrl":"www.zerochan.net/Archer+(Fate/stay+night)","width":240,"height":136,"thumbnail":{"width":240,"height":136},"imageInsightsToken":"ccid_CuVrg9qy*mid_2B4B0ED4643AEB03FFE5777E3B11FF553BF4C6B5*simid_607992895723211077","imageId":"2B4B0ED4643AEB03FFE5777E3B11FF553BF4C6B5","accentColor":"69101C"},{"name":"Rental DVD | Fate/stay night [Unlimited ...","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=kHJA2Gzq-fQ3Bntml1WfC-YiUY0sg1i4RJletUJQ7eM&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3d4396596EB6AACBFC7098773818F115FFBA5C4E87%26simid%3d608043129667191183&p=DevEx,5042.1","thumbnailUrl":"https://tse2-mm.cn.bing.net/th?id=OIP.M1a5cb97f907cdbf8a5ee22d91a153c2co0&pid=Api","datePublished":"2014-10-25T08:01:00","contentUrl":"http://www.fate-sn.com/rental/img/jkt01.jpg","hostPageUrl":"http://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=-EKrOF7SSdcZMZqPsyCyPbU6skrZPDNCAy-N7Ce90jo&v=1&r=http%3a%2f%2fwww.fate-sn.com%2frental%2f&p=DevEx,5043.1","contentSize":"81710 B","encodingFormat":"jpeg","hostPageDisplayUrl":"www.fate-sn.com/rental","width":300,"height":430,"thumbnail":{"width":209,"height":300},"imageInsightsToken":"ccid_Gly5f5B8*mid_4396596EB6AACBFC7098773818F115FFBA5C4E87*simid_608043129667191183","imageId":"4396596EB6AACBFC7098773818F115FFBA5C4E87","accentColor":"B2192A"},{"name":"PSP 《Fate Stay Night》壁纸_psp壁纸 ...","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=kR5j6W4shcO5Qz6tLNN5_Y2u9Y2vq5qAqcw5quzVoLk&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3d7BB91866B104AE7036F8974ED7AF7379497DE16E%26simid%3d607991177738520475&p=DevEx,5048.1","thumbnailUrl":"https://tse2-mm.cn.bing.net/th?id=OIP.M0d783c0ec2bccc64be42b903caf1a15do0&pid=Api","datePublished":"2011-07-02T03:50:00","contentUrl":"http://img1.pcgames.com.cn/pcgames/0906/17/1489795_57975709661.jpg","hostPageUrl":"http://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=rWwKq9PugtfCmT8VgB2G38X8Pzz1RjdsH1M1VzXTfcQ&v=1&r=http%3a%2f%2fpsp.pcgames.com.cn%2f000040033%2f0906%2f1489795_3.html&p=DevEx,5049.1","contentSize":"38769 B","encodingFormat":"jpeg","hostPageDisplayUrl":"psp.pcgames.com.cn/000040033/0906/1489795_3.html","width":480,"height":272,"thumbnail":{"width":300,"height":170},"imageInsightsToken":"ccid_DXg8DsK8*mid_7BB91866B104AE7036F8974ED7AF7379497DE16E*simid_607991177738520475","imageId":"7BB91866B104AE7036F8974ED7AF7379497DE16E","accentColor":"A82A23"},{"name":"Rental DVD | Fate/stay night [Unlimited ...","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=al6I4Vc2VW9Xr6IXz1hMQATYCau5EfALm4pKumXQfCM&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3d4396596EB6AACBFC709854717A8DED1834F38001%26simid%3d607994003835716288&p=DevEx,5054.1","thumbnailUrl":"https://tse4-mm.cn.bing.net/th?id=OIP.M7709bc3553da34bcf6dc610c0f4b427fo0&pid=Api","datePublished":"2014-10-25T08:01:00","contentUrl":"http://www.fate-sn.com/rental/img/jkt02.jpg","hostPageUrl":"http://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=-EKrOF7SSdcZMZqPsyCyPbU6skrZPDNCAy-N7Ce90jo&v=1&r=http%3a%2f%2fwww.fate-sn.com%2frental%2f&p=DevEx,5055.1","contentSize":"75402 B","encodingFormat":"jpeg","hostPageDisplayUrl":"www.fate-sn.com/rental","width":300,"height":430,"thumbnail":{"width":209,"height":300},"imageInsightsToken":"ccid_dwm8NVPa*mid_4396596EB6AACBFC709854717A8DED1834F38001*simid_607994003835716288","imageId":"4396596EB6AACBFC709854717A8DED1834F38001","accentColor":"A97422"},{"name":"... 版アニメ『Fate/stay night』の","webSearchUrl":"https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=UJF0KL8VQzhbB8qEIKaDq3_Uw-ZtbVQ5BTlVMOJ-yhw&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3d029E996C5E439F0A466D90A2E989B12468FFD5BB%26simid%3d608035974252857835&p=DevEx,5060.1","thumbnailUrl":"https://tse1-mm.cn.bing.net/th?id=OIP.Mf6f225ca35209ba26cd74c24a465912fo0&pid=Api","datePublished":"2016-06-25T00:10:00","contentUrl":"http://subculwalker.com/wp-content/uploads/2014/08/fate-stay-night-%E7%94%BB%E5%83%8F-e1415976234881.jpg","hostPageUrl":"http://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=Yzbz5BgzpLBGlj6jF1nlbF2LQMp7AGjuJs8FI8_IUgU&v=1&r=http%3a%2f%2fsubculwalker.com%2farchives%2f1660%2f&p=DevEx,5061.1","contentSize":"20827 B","encodingFormat":"jpeg","hostPageDisplayUrl":"subculwalker.com/archives/1660","width":400,"height":218,"thumbnail":{"width":300,"height":163},"imageInsightsToken":"ccid_9vIlyjUg*mid_029E996C5E439F0A466D90A2E989B12468FFD5BB*simid_608035974252857835","imageId":"029E996C5E439F0A466D90A2E989B12468FFD5BB","accentColor":"AD5F1E"}]
     * nextOffsetAddCount : 7
     * displayShoppingSourcesBadges : false
     * displayRecipeSourcesBadges : true
     */

    private String _type;
    /**
     * pageLoadPingUrl : https://www.bingapis.com/api/ping/pageload?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&Type=Event.CPT&DATA=0
     */

    private InstrumentationBean instrumentation;
    private String webSearchUrl;
    private int totalEstimatedMatches;
    private int nextOffsetAddCount;
    private boolean displayShoppingSourcesBadges;
    private boolean displayRecipeSourcesBadges;
    /**
     * name : Fate/stay night - Wikipedia, the free ...
     * webSearchUrl : https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=qyY9GjukDOfZ661cOnrWimt5L238pLpmMSkaCo8lpSE&v=1&r=https%3a%2f%2fwww.bing.com%2fimages%2fsearch%3fview%3ddetailv2%26FORM%3dOIIRPO%26q%3dfate%2bstay%2bnight%26id%3dB0D94F4CA3E382460AA61FA3156AFE91649CD787%26simid%3d608001185015335343&p=DevEx,5006.1
     * thumbnailUrl : https://tse2-mm.cn.bing.net/th?id=OIP.M519191f9d3b31ca4e0239f86f23e392do0&pid=Api
     * datePublished : 2016-04-28T21:50:00
     * contentUrl : https://upload.wikimedia.org/wikipedia/en/d/d4/Fate-stay_night.jpg
     * hostPageUrl : https://www.bing.com/cr?IG=27E7175A2E674538A8B953AF858B63AF&CID=33481A2060A0669A37F913716191676D&rd=1&h=YA0LRUl6sIHXtu-t4swymnxBH8-dZr8X3o2eJZwX0S8&v=1&r=https%3a%2f%2fen.wikipedia.org%2fwiki%2fFate%2fstay_night&p=DevEx,5007.1
     * contentSize : 83724 B
     * encodingFormat : jpeg
     * hostPageDisplayUrl : https://en.wikipedia.org/wiki/Fate/stay_night
     * width : 230
     * height : 326
     * thumbnail : {"width":211,"height":300}
     * imageInsightsToken : ccid_UZGR+dOz*mid_B0D94F4CA3E382460AA61FA3156AFE91649CD787*simid_608001185015335343
     * imageId : B0D94F4CA3E382460AA61FA3156AFE91649CD787
     * accentColor : C30713
     */

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

    public static class InstrumentationBean {
        private String pageLoadPingUrl;

        public String getPageLoadPingUrl() {
            return pageLoadPingUrl;
        }

        public void setPageLoadPingUrl(String pageLoadPingUrl) {
            this.pageLoadPingUrl = pageLoadPingUrl;
        }
    }

    public static class ValueBean {
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

        public static class ThumbnailBean {
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
