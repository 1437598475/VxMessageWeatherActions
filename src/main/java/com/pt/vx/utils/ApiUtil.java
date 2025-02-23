package com.pt.vx.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.pt.vx.domain.Api.*;
import com.pt.vx.domain.BirthDay;
import org.apache.commons.lang3.StringEscapeUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

public class ApiUtil {

    private static final Logger logger = Logger.getLogger("ApiUtil");

    private static final String  history_today= "http://fuyhi.top/api/historyday/api.php?type=json";//历史上的今天

    private static final String  qinghua = "https://api.uomg.com/api/rand.qinghua?format=text"; //土味情话

    private static final String dongman = "http://fuyhi.top/api/dmyiyan/api.php?type=text";//一句动漫台词

    private static final String tgrj ="http://api.gt5.cc/api/dog";//舔狗日记
    private static final String tgrj2 ="https://api.caonm.net/api/yulu/tgrj.php";
    private static final String WorldRead60sApi="http://api.gt5.cc/api/60s?type=json";//世界新闻
    private static final String randomRead ="http://api.gt5.cc/api/yiy?type=JSON";//随机一句
    private static final String wozairenjianApi ="https://api.gt5.cc/api/rj?type=text"; //我在人间凑日子
    private static final String poetryApi = "http://fuyhi.top/api/shicix/api.php?type=text";//诗句


    private static final String en = "http://fuyhi.top/api/yingyi/api.php?type=text";//英语

    private static final String miyuApi ="http://fuyhi.top/api/miyu/api.php";//谜语

    private static final String horoscopeApi = "https://api.vvhan.com/api/horoscope?type=%s&time=today";//星座运势 aries, taurus, gemini, cancer, leo, virgo, libra, scorpio, sagittarius, capricorn, aquarius, pisces
    private static final String horoscopeApi2 = "http://fuyhi.top/api/xingzuo/api.php?msg=%s";//星座运势2

    private static final String storyApi = "https://api.pingcc.cn/fiction/search/title/%s/1/10";
    private static final String storyApiChapter ="https://api.pingcc.cn/fictionChapter/search/%s";
    private static final String storyApiContent="https://api.pingcc.cn/fictionContent/search/%s";


   /* public static String getStoryApiContent()  {
        String re ;
        try {
            re = HttpUtil.get(String.format(storyApiContent, getStoryApiChapter()));
        } catch (Exception e) {
            logger.warning(String.format("获取小说失败 %s", e.getMessage()));
            return null;
        }
        re =  StringEscapeUtils.unescapeJava(re);
        return JSONUtil.toBean(re, Result.class).getData();
    }
    private static String getStoryApiChapter() throws InterruptedException {
        Thread.sleep(501);
        LocalDate satrtTime = AllConfig.start_time;
        String result = HttpUtil.get(String.format(storyApiChapter, getStoryCatalogue()));
        result =  StringEscapeUtils.unescapeJava(result);
        String data = JSONUtil.toBean(result, Result.class).getData();
        ChapterData chapterData = JSONUtil.toBean(data, ChapterData.class);
        String index = DateUtil.passDayOfNow(satrtTime);
        Chapter chapter = chapterData.getChapterList().get(Integer.parseInt(index));
        return chapter.getChapterId();
    }
    private static String getStoryCatalogue() throws InterruptedException {
        Thread.sleep(502);
        String title =  AllConfig.title;
        String resultGet = HttpUtil.get(String.format(storyApi, title));
        if(ObjectUtil.isEmpty(resultGet)){
            logger.warning("获取小说失败");
            return null;
        }
        resultGet =  StringEscapeUtils.unescapeJava(resultGet);
        Result result = JSONUtil.toBean(resultGet, Result.class);
        String data = result.getData();
        List<Catalogue> catalogues = JSONUtil.toList(data, Catalogue.class);
        if(CollectionUtil.isNotEmpty(catalogues)){
            for(Catalogue catalogue : catalogues){
                if( AllConfig.author.equals(catalogue.getAuthor())){
                    return catalogue.getFictionId();
                }
            }
        }
        Catalogue catalogue = Optional.ofNullable(catalogues).orElse(new ArrayList<>()).stream().findFirst().orElse(null);
        return catalogue == null ? null : catalogue.getFictionId();
    }*/

    /**
     * 获取历史上今天
     * @param count 数量，最大20
     * @return count条历史上今天
     */
    public static String getHistoryToday(int count){
        String result = HttpUtil.get(history_today, StandardCharsets.UTF_8,10000);
        logger.info("获取历史上的今天 "+result);
        result =  StringEscapeUtils.unescapeJava(result);
        Result re = JSONUtil.toBean(result, Result.class);
        String data = re.getData();
        List<HistoryDto> historyDtoList = JSONUtil.toList(data, HistoryDto.class);
        StringBuilder message = new StringBuilder();
        int i = 0;
        if(CollectionUtil.isNotEmpty(historyDtoList)){
            message.append("历史上的今天").append("\n");
            for(HistoryDto dto : historyDtoList){
                i++;
                message.append(dto.getMsg()).append("\n");
                if(i >= count){
                    break;
                }
            }
        }
        return message.toString();
    }

    public static String getDongman(){
        return HttpUtil.get(dongman, StandardCharsets.UTF_8,10000);
    }

    public static String getPoetryApi(){
        return HttpUtil.get(poetryApi, StandardCharsets.UTF_8,10000);
    }

    public static String getWozairenjian(){
        return HttpUtil.get(wozairenjianApi, StandardCharsets.UTF_8,10000);
    }
    /**
     * @return 获取一条土味情话
     */
    public static String getQingHua(){
        String result = HttpUtil.get(qinghua, StandardCharsets.UTF_8,10000);
        String format = String.format("获取情话 %s", result);
        logger.info(format);
        return result;
    }

    /**
     * @return 每日英语
     */
    public static String getEnglish(){
        String re = HttpUtil.get(en, StandardCharsets.UTF_8,10000);
        String format = String.format("获取英语 %s", re);
        logger.info(format);
        return re;
    }

    /**
     *
     * @return 世界新闻
     */
    public static String getWorldRead60s(){
        String result = HttpUtil.get(WorldRead60sApi, StandardCharsets.UTF_8,10000);
        logger.info(String.format("获取世界新闻 %s", result));
        Result re = JSONUtil.toBean(result, Result.class);
        WorldRead60s worldRead60s = JSONUtil.toBean(re.getData(), WorldRead60s.class);
        List<String> news = worldRead60s.getNews();
        StringBuilder message = new StringBuilder();
        int i = 0;
        if(CollUtil.isNotEmpty(news)){
            message.append("新闻").append("\n");
            for(String dto : news){
                i++;
                message.append(dto).append("\n");
                if(i >= 5){
                    break;
                }
            }
        }
        return message.toString();
    }


    public static String getMiYu(){
        String result = HttpUtil.get(miyuApi,StandardCharsets.UTF_8,10000);
        logger.info(String.format("获取谜语 %s", result));
        MiYuDto miYuDto = JSONUtil.toBean(result, MiYuDto.class);
        return "小谜语"+"\n"+miYuDto.getMt();
    }


    /**
     *
     * @return 舔狗日记
     */
    public static String getTgrj(){
        String result = HttpUtil.get(tgrj, StandardCharsets.UTF_8,10000);
        logger.info(String.format("获取舔狗日记 %s", result));
        return result;
    }

    /**
     *
     * @return 随机一句
     */
    public static String getRandomRead(){
        String result = HttpUtil.get(randomRead, StandardCharsets.UTF_8,10000);
        logger.info(String.format("获取短句 %s", result));
        result =  StringEscapeUtils.unescapeJava(result);
        RandomRead read = JSONUtil.toBean(result, RandomRead.class);
        return read.getText()+" -- "+read.getFrom();
    }

    /**
     *
     * @param horoscope 星座
     * @return 每日星座情况
     */
    private static String getHoroscopeRead(String horoscope){
        String result = HttpUtil.get(String.format(horoscopeApi, horoscope), StandardCharsets.UTF_8,10000);
        if(ObjectUtil.isEmpty(result)){
            logger.warning(String.format("获取星座解析失败，星座为：%s",horoscope ));
            return "";
        }
        logger.info("星座解析 "+result);
        result =  StringEscapeUtils.unescapeJava(result);
        Result result1 = JSONUtil.toBean(result, Result.class);
        HoroscopeDto horoscopeDto = JSONUtil.toBean(result1.getData(), HoroscopeDto.class);
        Fortunetext fortunetext = horoscopeDto.getFortunetext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(horoscopeDto.getTitle())
                .append("\n")
                .append("幸运色:")
                .append(horoscopeDto.getLuckycolor())
                .append("\n")
                .append("幸运数:")
                .append(horoscopeDto.getLuckynumber())
                .append("\n")
                .append("概况:")
                .append(horoscopeDto.getShortcomment())
                .append("\n")
                .append("整体情况:")
                .append(fortunetext.getAll())
                .append("\n")
                .append("健康情况:")
                .append(fortunetext.getHealth())
                .append("\n")
                .append("事业情况:")
                .append(fortunetext.getWork())
                .append("\n")
                .append("财运情况:")
                .append(fortunetext.getMoney())
                .append("\n")
                .append("爱情情况:")
                .append(fortunetext.getLove());


        return stringBuilder.toString();
    }
    public static String getHoroscopeRead2(BirthDay birthDay){
        return HttpUtil.get(String.format(horoscopeApi2, getHoroscopeChina(birthDay)), StandardCharsets.UTF_8,10000);
    }

    public static String getHoroscopeRead(BirthDay birthDay){
        return getHoroscopeRead(getHoroscope(birthDay));
    }

    private static String getHoroscope(String horoscope){
        if("白羊".equals(horoscope) || "白羊座".equals(horoscope)){
            return "aries";
        }else if("金牛".equals(horoscope) || "金牛座".equals(horoscope)){
            return "taurus";
        }else if("双子".equals(horoscope) || "双子座".equals(horoscope)){
            return "gemini";
        }else if("巨蟹".equals(horoscope) || "巨蟹座".equals(horoscope)){
            return "cancer";
        }else if("狮子".equals(horoscope) || "狮子座".equals(horoscope)){
            return "leo";
        }else if("处女".equals(horoscope) || "处女座".equals(horoscope)){
            return "virgo";
        }else if("天蝎".equals(horoscope) || "天蝎座".equals(horoscope)){
            return "libra";
        }else if("射手".equals(horoscope) || "射手座".equals(horoscope)){
            return "scorpio";
        }else if("摩羯".equals(horoscope) || "摩羯座".equals(horoscope)){
            return "sagittarius";
        }else if("水瓶".equals(horoscope) || "水瓶座".equals(horoscope)){
            return "capricorn";
        }else if("双鱼".equals(horoscope) || "双鱼座".equals(horoscope)){
            return "aquarius";
        }else if("天秤".equals(horoscope) || "天秤座".equals(horoscope)){
            return "pisces";
        }else {
            logger.warning(String.format("请天写正确的星座！%s",horoscope ));
            throw new RuntimeException("请天写正确的星座");
        }
    }

    private static String getHoroscope(BirthDay birthDay){

        String horoscopeChina = getHoroscopeChina(birthDay);
        return getHoroscope(horoscopeChina);
    }

    private static String getHoroscopeChina(BirthDay birthDay){
        int month = birthDay.getMonth();
        int day = birthDay.getDay();
        boolean chinese = birthDay.isChineseFlag();
        if(chinese){
            int year = birthDay.getYear();
            ChineseDate chineseDate=new ChineseDate(year,month,day);
            month = chineseDate.getGregorianMonthBase1();
            day = chineseDate.getGregorianDay();
        }
        if(month == 3 && day >= 21  ||  month == 4 && day <=20){
            return "白羊";
        }else if(month == 4 || month == 5 && day <= 20){
            return "金牛";
        }else if(month == 5 || month == 6 && day <= 20){
            return "双子";
        }else if(month == 6 || month == 7 && day <= 22){
            return "巨蟹";
        }else if(month == 7 || month == 8 && day <= 22){
            return "狮子";
        }else if(month == 8 || month == 9 && day <= 22){
            return "处女";
        }else if(month == 9 || month == 10 && day <= 22){
            return "天秤";
        }else if(month == 10 || month == 11 && day <= 22){
            return "天蝎";
        }else if(month == 11 || month == 12 && day <= 22){
            return "射手";
        }else if(month == 12 || month == 1 && day <= 21){
            return "摩羯";
        }else if(month == 1 || month == 2 && day <= 19){
            return "水瓶";
        }else if(month == 2 || month == 3){
            return "双鱼";
        }
        return  "不知道";
    }


}
