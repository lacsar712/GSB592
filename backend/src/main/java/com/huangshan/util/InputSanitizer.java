package com.huangshan.util;

/**
 * 输入清理工具类
 * 用于清理和验证用户输入，防止XSS攻击和数据质量问题
 */
public class InputSanitizer {

    private static final int MAX_STRING_LENGTH = 500;

    /**
     * 清理字符串输入
     * - 去除首尾空格
     * - 移除潜在的XSS字符
     * - 限制长度
     *
     * @param input 原始输入
     * @return 清理后的字符串，如果输入为null则返回null
     */
    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }

        // 去除首尾空格
        String cleaned = input.trim();

        // 移除潜在的XSS字符
        cleaned = cleaned.replaceAll("[<>\"'`]", "");

        // 限制长度
        if (cleaned.length() > MAX_STRING_LENGTH) {
            cleaned = cleaned.substring(0, MAX_STRING_LENGTH);
        }

        return cleaned;
    }

    /**
     * 验证字符串是否为空或仅包含空格
     *
     * @param input 待验证的字符串
     * @return 如果为空或仅包含空格返回true
     */
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * 验证字符串长度是否在指定范围内
     *
     * @param input 待验证的字符串
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return 如果长度在范围内返回true
     */
    public static boolean isLengthValid(String input, int minLength, int maxLength) {
        if (input == null) {
            return false;
        }
        int length = input.trim().length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * 清理并验证字符串
     *
     * @param input 原始输入
     * @param fieldName 字段名称（用于错误消息）
     * @return 清理后的字符串
     * @throws IllegalArgumentException 如果输入无效
     */
    public static String sanitizeAndValidate(String input, String fieldName) {
        if (isEmpty(input)) {
            throw new IllegalArgumentException(fieldName + "不能为空");
        }

        String cleaned = sanitize(input);

        if (isEmpty(cleaned)) {
            throw new IllegalArgumentException(fieldName + "包含无效字符");
        }

        return cleaned;
    }
}
