package tsl.campus_book_sharing_platform.ebook.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Ebook)实体类
 *
 * @author makejava
 * @since 2022-03-01 22:00:01
 */
@Data
public class Ebook implements Serializable {
    private static final long serialVersionUID = 905506547269443166L;
    /**
     * 电子书ID
     */
    private String ebookId;
    /**
     * 电子书名称
     */
    private String ebookName;
    /**
     * 电子书分类
     */
    private String ebookCategory;
    /**
     * 收藏次数
     */
    private Integer collectionTimes;
    /**
     * 下载次数
     */
    private Integer downloadTimes;
    /**
     * 电子书简介
     */
    private String ebookBriefInformation;
    /**
     * 上传者ID
     */
    private String downloadUserId;
    /**
     * 上传者用户名（仅记录上传时用户名称）
     */
    private String downloadUserName;
    /**
     * 电子书大小
     */
    private String ebookSize;
    /**
     * 电子书审核状态（审核中、已驳回、上传成功、已取消）
     */
    private String ebookState;
    /**
     * 电子书状态（未删除、已删除）
     */
    private String informationState;

}

