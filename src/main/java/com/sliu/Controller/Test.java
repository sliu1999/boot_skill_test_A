package com.sliu.Controller;

import com.sliu.Service.MqService;
import com.sliu.Service.TestService;
import com.sliu.util.GlobalSingleThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping({"/testApi"})
public class Test {

    @Autowired
    private TestService testService;

    @Autowired
    private MqService mqService;

    @GetMapping(value = "/test")
    public String test(){
        System.out.print("test");
        return "test";
    }
    @GetMapping(value = "/getSwipers")
    public List<HashMap> getSwipers(){
        List<HashMap> result = new ArrayList<>(3);
        HashMap one = new HashMap(2);
        one.put("id",0);
        one.put("img","http://47.117.128.180/files/cat.jpg");
        HashMap two = new HashMap(2);
        two.put("id",0);
        two.put("img","http://47.117.128.180/files/202106081455000363.jpg");
        HashMap three = new HashMap(2);
        three.put("id",0);
        three.put("img","http://47.117.128.180/files/qiqiu.jpg");
        result.add(one);
        result.add(two);
        result.add(three);
        System.out.print("test");
        return result;
    }

    @GetMapping(value = "/getSwipperList/{id}")
    public List<HashMap> getSwipperList(@PathVariable String id){
        List<HashMap> result = new ArrayList<>(1);
        HashMap one = new HashMap(1);
        one.put("img","//m.360buyimg.com/mobilecms/s750x750_jfs/t1/118064/27/12885/59959/5f17b7efE453f688d/5b33ac76b2aaea9b.jpg!q80.dpg.webp");
        HashMap two = new HashMap(1);
        two.put("img","//m.360buyimg.com/mobilecms/s843x843_jfs/t1/139925/9/3487/36638/5f17b7eeE2d7fb367/5b7f5a8d57bf804a.jpg!q70.dpg.webp");
        HashMap three = new HashMap(1);
        three.put("img","//m.360buyimg.com/mobilecms/s843x843_jfs/t1/121032/11/7794/38820/5f17b7eeE306dc16a/8344c7bbf49cd8a9.jpg!q70.dpg.webp");
        HashMap four = new HashMap(1);
        four.put("img","//m.360buyimg.com/mobilecms/s843x843_jfs/t1/137344/40/5026/29866/5f17b7eeEc9da3d4a/cd293063f60c8609.jpg!q70.dpg.webp");
        HashMap five = new HashMap(1);
        five.put("img","//m.360buyimg.com/mobilecms/s843x843_jfs/t1/125573/21/7718/17826/5f17b7efEa27913e0/3e8b37ff4e21492f.jpg!q70.dpg.webp");
        HashMap six = new HashMap(1);
        six.put("img","//img12.360buyimg.com/jdphoto/jfs/t11506/139/448468161/290/701986d3/59f085fbN2932bfce.jpg!q70.dpg.webp");
        result.add(one);
        result.add(two);
        result.add(three);
        result.add(four);
        result.add(five);
        result.add(six);
        return result;
    }

    @GetMapping(value = "/getDetail")
    public String getDetail(){
        String result = "<div class=\"detail_info_wrap\" id=\"detail\" style=\"height: 10077px;\">\n" +
                "        <div class=\"detail_list\" id=\"detailCont\" style=\"transform: translate3d(0px, 0px, 0px); transition: all 0ms ease 0s;\">\n" +
                "            \n" +
                "            <!-- 商品介绍 -->\n" +
                "            <div class=\"detail_item p_desc\" id=\"detail1\" style=\"position: relative; padding: 0px;\">\n" +
                "                \n" +
                "                <!-- 视频播放器 -->\n" +
                "                <div class=\"jdvideo_div\" id=\"videoMain\"></div>\n" +
                "                <!-- 强制广告位 -->\n" +
                "                <div style=\"display:none;\" id=\"adPosition\"></div>\n" +
                "                <!-- 关联版式、统一海报 -->\n" +
                "                <div style=\"display:none;\" id=\"detailAdvertise\"></div>\n" +
                "                <div style=\"display:none;\" id=\"globalNotice\" class=\"world\">\n" +
                "                    <div class=\"world_title\">\n" +
                "                        <span class=\"text\">京东国际公告</span>\n" +
                "                    </div>\n" +
                "                    <div><div id=\"globalNoticeArea\" class=\"detail_pc\"></div></div>\n" +
                "                </div>\n" +
                "                <div class=\"world_title\" id=\"globalComm\" style=\"display:none;\">\n" +
                "                    <span class=\"text\">商品详情</span>\n" +
                "                </div>\n" +
                "                <div class=\"refer_area\" style=\"display:none;\" id=\"sizeArea\"></div>\n" +
                "                <div><div id=\"commDesc\" class=\"detail_pc\" hasdata=\"1\"><div cssurl=\"//sku-market-gw.jd.com/css/mobile/100020671116.css?t=1618453364881\"></div><div skucode=\"100011\"></div><center>\t<img src=\"//img30.360buyimg.com/sku/jfs/t1/160722/18/7944/573398/6037654aE7a94a4f8/5b7c981648d6516e.jpg!q70.dpg.webp\" alt=\"\" loaded=\"31\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/170150/24/18478/146468/6076df17E45bdaa9c/f31755447f0f0a89.jpg!q70.dpg.webp\" alt=\"\" loaded=\"30\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/164902/37/7733/40408/60339f52Ea460f869/946af279e31e8fac.jpg!q70.dpg.webp\" alt=\"\" loaded=\"29\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/153785/33/19061/261722/60339f53E6df4a631/f5f63d28e85d7d84.jpg!q70.dpg.webp\" alt=\"\" loaded=\"28\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/165904/4/7296/92376/60339f52E7010bc3d/9cf2119909b8b8d6.jpg!q70.dpg.webp\" alt=\"\" loaded=\"27\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/170136/39/7393/295192/60339f53Ed254f676/bad4d871dd406289.jpg!q70.dpg.webp\" alt=\"\" loaded=\"26\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/150807/34/19717/101929/60339f52E26fe8cb9/18e49335acc8b9ba.jpg!q70.dpg.webp\" alt=\"\" loaded=\"25\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/154446/33/19493/87994/60339f52Edff6a306/e39a0f2eacd27932.jpg!q70.dpg.webp\" alt=\"\" loaded=\"24\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/170452/30/7580/350654/60339f53E036b75ee/7376d76e9d11a70d.jpg!q70.dpg.webp\" alt=\"\" loaded=\"23\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/158922/38/8219/59245/60339f52Ec66e5b94/7449770da88040a7.jpg!q70.dpg.webp\" alt=\"\" loaded=\"22\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/157691/4/8113/264773/60339f53Eb446e064/a8241539a8ccd0b2.jpg!q70.dpg.webp\" alt=\"\" loaded=\"21\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/171027/18/7420/292202/60339f53Ee32ea09a/6ab3883923e26b99.jpg!q70.dpg.webp\" alt=\"\" loaded=\"20\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/171303/29/7544/146449/60339f52E6ea5320f/fa0788becd5bb063.jpg!q70.dpg.webp\" alt=\"\" loaded=\"19\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/159909/37/8086/557101/60339f53E5799e8aa/1de8980eeead5be2.jpg!q70.dpg.webp\" alt=\"\" loaded=\"18\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/160203/13/8077/419026/60339f53E887354f3/c0e3ddf409b82474.jpg!q70.dpg.webp\" alt=\"\" loaded=\"17\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/160279/20/7723/168185/60339f52E48916720/0549321f044c1f2a.jpg!q70.dpg.webp\" alt=\"\" loaded=\"16\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/156286/5/10849/128238/60339f52E6627ecf4/f63d7205d281385f.jpg!q70.dpg.webp\" alt=\"\" loaded=\"15\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/167811/9/7336/171250/60339f52Ebc577b9b/0e7b8085c397977e.jpg!q70.dpg.webp\" alt=\"\" loaded=\"14\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/164837/2/7250/415082/60339f53E411d6e1b/3bf4d917c339e1c0.jpg!q70.dpg.webp\" alt=\"\" loaded=\"13\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/170020/39/7494/139026/60339f52E965cd0d3/230f16d2f05dd060.jpg!q70.dpg.webp\" alt=\"\" loaded=\"12\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/169235/20/7360/146660/60339f52E9a03db4c/c44d841b01bfff36.jpg!q70.dpg.webp\" alt=\"\" loaded=\"11\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/158597/3/8152/158227/60339f52E12fb6007/cbc24428c4dfc022.jpg!q70.dpg.webp\" alt=\"\" loaded=\"10\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/160629/15/7563/149078/60339f52E3b1eff16/b39225a7da8d69ec.jpg!q70.dpg.webp\" alt=\"\" loaded=\"9\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/161130/25/7412/155512/60339f52E57aeb8b0/38a8d1e280464320.jpg!q70.dpg.webp\" alt=\"\" loaded=\"8\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/161176/25/7450/76387/60339f52E077215cf/4feb79333053fb3e.jpg!q70.dpg.webp\" alt=\"\" loaded=\"7\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/164317/38/18188/155775/6076dfa5E6f8ac6e6/8ec2738aa63a35e8.jpg!q70.dpg.webp\" alt=\"\" loaded=\"6\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/167221/10/18586/130251/6076dfa5E326af63a/561fad4b8fad4646.jpg!q70.dpg.webp\" alt=\"\" loaded=\"5\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/170348/13/7508/2560/60339f51Eef68c21f/864fba8c9d52bcab.jpg!q70.dpg.webp\" alt=\"\" loaded=\"4\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/161982/32/7563/114618/60339f62E5f8ad6a0/53e6e9bb0f8398d8.jpg!q70.dpg.webp\" alt=\"\" loaded=\"3\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/157593/4/8148/186592/60339f62E215436fd/7b3ec5a50e4e80ad.jpg!q70.dpg.webp\" alt=\"\" loaded=\"2\" style=\"max-width: 375px;\"><img src=\"//img30.360buyimg.com/sku/jfs/t1/163840/27/7373/108844/60339f62Ee2e32ebf/8994adebba5b4a65.jpg!q70.dpg.webp\" alt=\"\" loaded=\"1\" style=\"max-width: 375px;\"> </center><script></script></div></div>\n" +
                "                <div><div style=\"display:none;\" class=\"detail_pc\" id=\"detailAdvertise1\"></div></div>\n" +
                "                <!-- 品牌活动 -->\n" +
                "                <div style=\"\" id=\"brandAct\"></div>\n" +
                "                <!-- 温馨提示语 -->\n" +
                "                <div style=\"display:none;\" class=\"reminder\" id=\"reminder\"></div>\n" +
                "            </div>\n" +
                "            <!-- 商品参数 -->\n" +
                "            <div class=\"detail_item p_prop\" id=\"detail2\">\n" +
                "                \n" +
                "                <div id=\"package\">\n" +
                "                    <div class=\"mod_tit_line\">\n" +
                "                        <h3>包装清单</h3>\n" +
                "                    </div>\n" +
                "                    <div class=\"mod_row\" id=\"detPackage\"></div>\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                    <div class=\"mod_tit_line\">\n" +
                "                        <h3>商品参数</h3>\n" +
                "                    </div>\n" +
                "                    <div id=\"detParam\"></div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <!--售后保障-->\n" +
                "            <div class=\"detail_item p_cmt\" id=\"detail3\">\n" +
                "                \n" +
                "                <div id=\"detail3Normal\">\n" +
                "                    <div id=\"detServer\"></div>\n" +
                "                    <div id=\"skusfkc\" style=\"display: none;\">\n" +
                "                        <div class=\"mod_tit_line\"><h3>商品提示</h3></div>\n" +
                "                        该商品超时未付款，订单将被自动取消\n" +
                "                    </div>\n" +
                "                    <div id=\"normalServerPromise\" style=\"display:none;\"></div>\n" +
                "\n" +
                "                    <div id=\"serverPromise\">\n" +
                "                        <div class=\"mod_tit_line\"><h3>服务承诺</h3></div>\n" +
                "                        京东商城向您保证所售商品均为正品行货，京东自营商品开具机打发票或电子发票。凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场选购的商品享受相同的质量保证。京东商城还为您提供具有竞争力的商品价格和<a target=\"_self\" href=\"//wqs.jd.com/my/cart/extraYunfeiRule.shtml?detail=1\">运费政策</a>，请您放心购买！\n" +
                "                        <div class=\"for_separator\"></div>\n" +
                "                        注：因厂家会在没有任何提前通知的情况下更改产品包装、产地或者一些附件，本司不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！\n" +
                "                    </div>\n" +
                "                    <div id=\"freshServerPromise\" style=\"display:none;\">\n" +
                "                        <div class=\"mod_tit_line\"><h3>售后服务</h3></div>\n" +
                "                        1、生鲜“优鲜赔”绿色通道\n" +
                "                        <div class=\"for_separator\"></div>\n" +
                "                        “优鲜赔”服务是为魔镜等级S2及以上的京东优质客户提供的售后特色服务，符合条件的生鲜自营订单商品有破损或腐坏等问题，可以在商品签收后48小时内提交“优鲜赔”申请，提交申请后100分钟内审核通过后即享补偿或退款，无需返回商品，为客户节省了返回商品的物流等待时间和收货检测的处理时间（非鲜活易腐类商品除外）。\n" +
                "                        <div class=\"for_separator\"></div>\n" +
                "                        2、专业生鲜客服团队—让您售后无忧 \n" +
                "                        <div class=\"for_separator\"></div>\n" +
                "                        微信在线客服：JD-fresh\n" +
                "                        <div class=\"for_separator\"></div>\n" +
                "                        京东生鲜专享客服电话：400-606-3311 \n" +
                "                        <div class=\"for_separator\"></div>\n" +
                "                        3、京东自营商品开具机打发票或电子发票\n" +
                "                        <div class=\"for_separator\"></div>\n" +
                "                        如您发现商品有质量问题，请在收到商品之时起48小时内及时提交申请或联系客服处理。\n" +
                "                    </div>\n" +
                "                    <div>\n" +
                "                        <div class=\"mod_tit_line\"><h3>权利声明</h3></div>\n" +
                "                        京东商城上的所有商品信息、客户评价、商品咨询、网友讨论等内容，是京东商城重要的经营资源，未经许可，禁止非法转载使用。\n" +
                "                        <div class=\"for_separator\"></div>\n" +
                "                        <p><b>注：</b>本站商品信息均来自于厂商，其真实性、准确性和合法性由信息拥有者（厂商）负责。本站不提供任何保证，并不承担任何法律责任。</p>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div id=\"priceDescDetail\">\n" +
                "                    <div class=\"mod_tit_line\"><h3>价格说明</h3></div>\n" +
                "                    <p><strong>1.京东价：</strong>京东价为商品的销售价，是您最终决定是否购买商品的依据。</p>\n" +
                "                    <p><strong>2.划线价：</strong>商品展示的划横线价格为参考价，该价格可能是品牌专柜标价、商品吊牌价或由品牌供应商提供的正品零售价（如厂商指导价、建议零售价等）或该商品在京东平台上曾经展示过的销售价；由于地区、时间的差异性和市场行情波动，品牌专柜标价、商品吊牌价等可能会与您购物时展示的不一致，该价格仅供您参考。</p>\n" +
                "                    <p><strong>3.折扣：</strong>如无特殊说明，折扣指销售商在原价、或划线价（如品牌专柜标价、商品吊牌价、厂商指导价、厂商建议零售价）等某一价格基础上计算出的优惠比例或优惠金额；如有疑问，您可在购买前联系销售商进行咨询。</p>\n" +
                "                    <p><strong>4.异常问题：</strong>商品促销信息以商品详情页“促销”栏中的信息为准；商品的具体售价以订单结算页价格为准；如您发现活动商品售价或促销信息有异常，建议购买前先联系销售商咨询。</p>\n" +
                "                </div>\n" +
                "               <div id=\"ecoTip\" style=\"display:none;\">\n" +
                "                   <div class=\"mod_tit_line\"><h3>能效标识说明</h3></div>\n" +
                "                   <p>根据国家相关能效标识法规和标准的要求，京东自营在售商品的能效标识图样，将会逐步替换为新版能源效率标识贴；受能效标识标准变化影响，部分产品的新版和旧版能效标识，在能效等级、测试值等方面会有差异，但产品实际性能完全一样，并不影响购买和使用，加贴新版或旧版能效标识的商品会随机发放，请您放心购买；如有疑问，请在购买前通过咚咚或来电咨询。</p>\n" +
                "               </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>";
        return result;
    }

    @GetMapping(value = "/getGoodList/{pageNum}")
    public List<HashMap> getGoodList(@PathVariable String pageNum){
        List<HashMap> result = new ArrayList<>(3);
        HashMap one = new HashMap(5);
        one.put("id",0);
        one.put("title","Redmi 9A 5000mAh大电量 1300万AI相机");
        one.put("img","http://img12.360buyimg.com/n2/s100x100_jfs/t1/118064/27/12885/59959/5f17b7efE453f688d/5b33ac76b2aaea9b.jpg!q70.dpg");
        one.put("sell_price","2199");
        one.put("market_price","2499");
        HashMap two = new HashMap(5);
        two.put("id",1);
        two.put("title","vivo iQOO U3x标准版 5000mAh大电池 双引擎闪充");
        two.put("img","//img10.360buyimg.com/n2/s100x100_jfs/t1/189737/27/10988/118289/60dc59e7Ec88ab980/795babee2b32be17.jpg!q70.jpg");
        two.put("sell_price","2199");
        two.put("market_price","2499");
        HashMap three = new HashMap(5);
        three.put("id",2);
        three.put("title","荣耀Play5T活力版 超级快充 5000mAh大电池");
        three.put("img","//img14.360buyimg.com/n2/s100x100_jfs/t1/163788/10/12449/182015/607d3289E5fc840a6/56b4eabc00aa04c8.jpg!q70.dpg");
        three.put("sell_price","2199");
        three.put("market_price","2499");
        HashMap four = new HashMap(5);
        four.put("id",3);
        four.put("title","荣耀畅玩20 5000mAh超大电池续航 6.5英寸大屏  莱茵护眼");
        four.put("img","//img13.360buyimg.com/n2/s100x100_jfs/t1/161014/2/20478/140699/6082904aEc60ee1d7/2fa0342ba547cfac.jpg!q70.dpg");
        four.put("sell_price","2199");
        four.put("market_price","2499");
        HashMap five = new HashMap(5);
        five.put("id",4);
        five.put("title","vivo Y3s 4GB+64GB 海风青 5000mAh大电池");
        five.put("img","//img12.360buyimg.com/n2/s100x100_jfs/t1/177371/35/8588/66601/60c22b38E5cc7812d/1a2341a50a57ed6f.jpg!q70.dpg");
        five.put("sell_price","2199");
        five.put("market_price","2499");
        HashMap six = new HashMap(5);
        six.put("id",5);
        six.put("title","realme 真我Q3 骁龙750G 120Hz可变帧电竞屏 30W智慧闪充");
        six.put("img","//img12.360buyimg.com/n2/s100x100_jfs/t1/166394/6/21011/214424/60863390E9c874a3c/49dd077ad7449f22.jpg!q70.dpg");
        six.put("sell_price","2199");
        six.put("market_price","2499");
        HashMap seven = new HashMap(5);
        seven.put("id",6);
        seven.put("title","Redmi Note 9 5G 天玑800U 18W快充 4800万超清三摄");
        seven.put("img","//img12.360buyimg.com/n2/s100x100_jfs/t1/170769/26/15952/50542/60657586E4859e2cd/9dcd2e35d6b2eb88.jpg!q70.dpg");
        seven.put("sell_price","2199");
        seven.put("market_price","2499");
        HashMap eight = new HashMap(5);
        eight.put("id",7);
        eight.put("title","Redmi 9A 5000mAh大电量 1300万AI相机");
        eight.put("img","http://img12.360buyimg.com/n2/s100x100_jfs/t1/118064/27/12885/59959/5f17b7efE453f688d/5b33ac76b2aaea9b.jpg!q70.dpg");
        eight.put("sell_price","2199");
        eight.put("market_price","2499");
        HashMap nine = new HashMap(5);
        nine.put("id",8);
        nine.put("title","Redmi 9A 5000mAh大电量 1300万AI相机");
        nine.put("img","http://img12.360buyimg.com/n2/s100x100_jfs/t1/118064/27/12885/59959/5f17b7efE453f688d/5b33ac76b2aaea9b.jpg!q70.dpg");
        nine.put("sell_price","2199");
        nine.put("market_price","2499");
        HashMap ten = new HashMap(5);
        ten.put("id",9);
        ten.put("title","vivo iQOO U3x标准版 5000mAh大电池 双引擎闪充");
        ten.put("img","//img10.360buyimg.com/n2/s100x100_jfs/t1/189737/27/10988/118289/60dc59e7Ec88ab980/795babee2b32be17.jpg!q70.jpg");
        ten.put("sell_price","2199");
        ten.put("market_price","2499");
        HashMap eleven = new HashMap(5);
        eleven.put("id",10);
        eleven.put("title","荣耀Play5T活力版 超级快充 5000mAh大电池");
        eleven.put("img","//img14.360buyimg.com/n2/s100x100_jfs/t1/163788/10/12449/182015/607d3289E5fc840a6/56b4eabc00aa04c8.jpg!q70.dpg");
        eleven.put("sell_price","2199");
        eleven.put("market_price","2499");
        HashMap thwo = new HashMap(5);
        thwo.put("id",11);
        thwo.put("title","荣耀畅玩20 5000mAh超大电池续航 6.5英寸大屏  莱茵护眼");
        thwo.put("img","//img13.360buyimg.com/n2/s100x100_jfs/t1/161014/2/20478/140699/6082904aEc60ee1d7/2fa0342ba547cfac.jpg!q70.dpg");
        thwo.put("sell_price","2199");
        thwo.put("market_price","2499");
        result.add(one);
        result.add(two);
        result.add(three);
        result.add(four);
        result.add(five);
        result.add(six);
        result.add(seven);
        result.add(eight);
        result.add(nine);
        result.add(ten);
        result.add(eleven);
        result.add(thwo);

        int length = Integer.parseInt(pageNum);
        List<HashMap> resultTwo = new ArrayList<>(1);
        for(int i = 0; i<= result.size();i++){
            if((length-1)*6<=i && i<length*6 && length*6<=result.size()){
                resultTwo.add(result.get(i));
            }
        }

        System.out.print("test");
        return resultTwo;
    }

    @RequestMapping("/websocket/{name}")
    public String webSocket(@PathVariable String name, Model model){
        try{
            System.out.print("跳转到websocket的页面上");
            model.addAttribute("username",name);
            return "websocket";
        }
        catch (Exception e){
            System.out.print("跳转到websocket的页面上发生异常，异常信息是："+e.getMessage());
            return "error";
        }
    }

    @GetMapping(value = "/testThreadPool")
    public void testThreadPool(){
        ThreadPoolExecutor poolExecutor = GlobalSingleThreadPool.getInstance();
        for (int i = 0; i < 100; i++) {
            poolExecutor.execute(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) poolExecutor);

            System.out.println();

            int queueSize = tpe.getQueue().size();
            System.out.println("当前排队线程数：" + queueSize);

            int activeCount = tpe.getActiveCount();
            System.out.println("当前活动线程数：" + activeCount);

            long completedTaskCount = tpe.getCompletedTaskCount();
            System.out.println("执行完成线程数：" + completedTaskCount);

            long taskCount = tpe.getTaskCount();
            System.out.println("总线程数：" + taskCount);


    }

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public int get() {
        return 1;
    }


    @GetMapping(value = "/mqSimpleTest")
    public void mqSimpleTest() throws IOException {
        mqService.sendMsg("simple");

    }

    @GetMapping(value = "/mqRk1Test")
    public void mqRk1Test() throws IOException {
        mqService.sendMsg("rk1");

    }


    @GetMapping(value = "/sendDelayMessage")
    public void sendDelayMessage() {
        mqService.sendDelayMessage("rk1");

    }

    @GetMapping(value = "/contextLoads")
    public void contextLoads() {
        mqService.contextLoads();

    }


}
