package com.followba.store.admin.controller;

import com.followba.store.admin.service.SystemFileService;
import com.followba.store.admin.vo.in.SystemFilePresignedDownloadUrlIn;
import com.followba.store.admin.vo.in.SystemFilePresignedUploadUrlIn;
import com.followba.store.admin.vo.out.SystemFilePresignedDownloadUrlOut;
import com.followba.store.admin.vo.out.SystemFilePresignedUploadUrlOut;
import com.followba.store.common.resp.Out;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/file")
@Validated
public class SystemFileController {

    @Resource
    private SystemFileService systemFileService;

    @PostMapping("/presigned-upload-url")
    public Out<SystemFilePresignedUploadUrlOut> getPresignedUploadUrl(@Valid @RequestBody SystemFilePresignedUploadUrlIn in) {
        return Out.success(systemFileService.getPresignedUploadUrl(in));
    }

    @PostMapping("/presigned-download-url")
    public Out<SystemFilePresignedDownloadUrlOut> getPresignedDownloadUrl(@Valid @RequestBody SystemFilePresignedDownloadUrlIn in) {
        return Out.success(systemFileService.getPresignedDownloadUrl(in));
    }
}
