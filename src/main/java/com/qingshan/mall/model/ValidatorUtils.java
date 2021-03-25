package com.qingshan.mall.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class ValidatorUtils {

    /**
     * 自定义正则表达式验证
     */
    private static boolean validate(String regex, String src) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    /**
     * 过滤表情
     *
     */
    public static boolean isExpression(String src) {//[\\uD83D\\uDE00-\\uD83D\\uDE4F]
        return validate("(?:[\uD83C\uDF00-\uD83D\uDDFF]|[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|[\uD83E\uDD00-\uD83E\uDDFF]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|[\u2600-\u26FF]\uFE0F?|[\u2700-\u27BF]\uFE0F?|\u24C2\uFE0F?|[\uD83C\uDDE6-\uD83C\uDDFF]{1,2}|[\uD83C\uDD70\uD83C\uDD71\uD83C\uDD7E\uD83C\uDD7F\uD83C\uDD8E\uD83C\uDD91-\uD83C\uDD9A]\uFE0F?|[\u0023\u002A\u0030-\u0039]\uFE0F?\u20E3|[\u2194-\u2199\u21A9-\u21AA]\uFE0F?|[\u2B05-\u2B07\u2B1B\u2B1C\u2B50\u2B55]\uFE0F?|[\u2934\u2935]\uFE0F?|[\u3030\u303D]\uFE0F?|[\u3297\u3299]\uFE0F?|[\uD83C\uDE01\uD83C\uDE02\uD83C\uDE1A\uD83C\uDE2F\uD83C\uDE32-\uD83C\uDE3A\uD83C\uDE50\uD83C\uDE51]\uFE0F?|[\u203C\u2049]\uFE0F?|[\u25AA\u25AB\u25B6\u25C0\u25FB-\u25FE]\uFE0F?|[\u00A9\u00AE]\uFE0F?|[\u2122\u2139]\uFE0F?|\uD83C\uDC04\uFE0F?|\uD83C\uDCCF\uFE0F?|[\u231A\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA]\uFE0F?)",src);
    }

    /**
     * 验证是否是字符组成，包含特殊字符（包含汉字）
     *
     */
//    public static boolean isSpecialCharacter(String src) {
//        return validate("[\\[\\]\u4E00-\u9FA5A-Za-z0-9，。？！、……@：；＆＾～““”（）（）＊＃％＋－＿＝／·￥＄￡€×÷°《》《》｛｝｛｝【】【】＜＞＜＞「」「」‘’‘’,.?!/…@:;&^~'()*#%+\\-_=·¥$£€￠`×÷¿¡<>{}|°▪]+", src);
//    }

    public static boolean isSpecialCharacter(String src) {//
        return validate("[\u4E00-\u9FA5A-Za-z0-9，,。.？?！!：:；;·、…~～……&＆@＠#＃“”‘’〝〞 \" '＂＇´＇（）()《》＜＞<>\\[\\]«»‹›〈〉{}［］「」｛｝〖〗『』" +
                "/|／｜＼_＿﹏﹍﹎`¦¡^\u00AD¨ˊ¯￣﹋﹉﹊ˋ︴¿ˇ　﹢﹣\\-–·/=﹤﹥≦≧＋－×÷＝＜＞≈≠¥￥$＄£€￠฿₠￡′″°*＊￦₩【】——\"%％+\\\\▪]+", src);
    }
}
