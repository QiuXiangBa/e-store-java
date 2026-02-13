package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCategoryRespVO {

    private Long id;

    private Long parentId;

    private String name;

    private String picUrl;

    private String bigPicUrl;

    private Integer sort;

    private Integer status;

    private Date createTime;
}
