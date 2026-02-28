package com.followba.store.auth.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressUpdateIn {

    @NotNull(message = "id 不能为空")
    private Long id;

    @NotBlank(message = "receiverName 不能为空")
    private String receiverName;

    @NotBlank(message = "receiverMobile 不能为空")
    private String receiverMobile;

    @NotBlank(message = "provinceCode 不能为空")
    private String provinceCode;

    @NotBlank(message = "provinceName 不能为空")
    private String provinceName;

    @NotBlank(message = "cityCode 不能为空")
    private String cityCode;

    @NotBlank(message = "cityName 不能为空")
    private String cityName;

    private String districtCode;

    private String districtName;

    @NotBlank(message = "detailAddress 不能为空")
    private String detailAddress;

    private String postalCode;

    private Boolean isDefault;
}
