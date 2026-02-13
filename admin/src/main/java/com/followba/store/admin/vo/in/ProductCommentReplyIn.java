package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCommentReplyIn {

    @NotNull(message = "id 不能为空")
    private Long id;

    @NotBlank(message = "回复内容不能为空")
    private String replyContent;
}
