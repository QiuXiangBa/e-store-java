package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.SystemFilePresignedDownloadUrlIn;
import com.followba.store.admin.vo.in.SystemFilePresignedUploadUrlIn;
import com.followba.store.admin.vo.out.SystemFilePresignedDownloadUrlOut;
import com.followba.store.admin.vo.out.SystemFilePresignedUploadUrlOut;

public interface SystemFileService {

    SystemFilePresignedUploadUrlOut getPresignedUploadUrl(SystemFilePresignedUploadUrlIn in);

    SystemFilePresignedDownloadUrlOut getPresignedDownloadUrl(SystemFilePresignedDownloadUrlIn in);
}
