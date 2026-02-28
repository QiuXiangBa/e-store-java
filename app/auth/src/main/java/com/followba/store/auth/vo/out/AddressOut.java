package com.followba.store.auth.vo.out;

import lombok.Data;

@Data
public class AddressOut {

    private Long id;

    private String receiverName;

    private String receiverMobile;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String districtCode;

    private String districtName;

    private String detailAddress;

    private String postalCode;

    private Integer isDefault;

    private Integer status;
}
