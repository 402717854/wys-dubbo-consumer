package com.wys.dubbo.consumer.util;


import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Data: Created on 2020/7/21
 * @Description:
 */
public class CheckUtil {
    //校验手机号格式
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        return phone != null && phone.length() > 0 && Pattern.matches(regex, phone);
   }

    public static boolean checkAccount(String account) {
        String regex = "^[a-zA-Z0-9]{6,16}$";
        return Pattern.matches(regex, account);
   }

    public static boolean checkPassword(String password) {
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{6,16}$";
        return Pattern.matches(regex, password);
   }

    public static <T> String validAnnotation(@Valid T t) {
        Set<ConstraintViolation<@Valid T>> validateSet =
                Validation.buildDefaultValidatorFactory()
                        .getValidator()
                        .validate(t, new Class[0]);
        if (!CollectionUtils.isEmpty(validateSet)) {
            String messages = validateSet.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((m1, m2) -> m1 + ";" + m2)
                    .orElse("参数错误");
            return messages;
       }

        return null;
   }

    /**
     * 判断是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || "".equals(email)){
            return Boolean.FALSE;
       }
        // 范围 更广的 邮箱验证 “/^[^@]+@.+\\..+$/”
        final String pattern1 = "[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+@[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+\\.[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+";

        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        return mat.matches();

   }

    /**
     * 判断是否是手机号
     *
     * @param tel
     * @return
     */
    public static boolean isMobile(String tel) {
        final String REGEX_MOBILE = "(134[0-8]\\d{7})" + "|(" + "((13([0-3]|[5-9]))" + "|149"
                + "|15([0-3]|[5-9])" + "|166" + "|17(3|[5-8])" + "|18[0-9]" + "|17[0-9]" + "|16[0-9]" + "|19[8-9]" + ")" + "\\d{8}" + ")";
        return Pattern.matches(REGEX_MOBILE, tel);
   }

    /**
     * 判断两个集合是否相等
     * @param aList
     * @param bList
     * @return
     */
    public static boolean isEquals(List aList, List bList) {
        if (aList == bList){
            return true;
       }
        if (aList.size() != bList.size()){
            return false;
       }
        int n = aList.size();
        int i = 0;
        while (n-- != 0) {
            if (!aList.get(i).equals(bList.get(i))){
                return false;
           }
            i++;
       }
        return true;
   }
}
