package com.followba.store.admin.vo.out;

import lombok.Data;

@Data
public class SystemFilePresignedUploadUrlOut {

    private String objectKey;

    private String uploadUrl;

    private String objectUrl;
}
