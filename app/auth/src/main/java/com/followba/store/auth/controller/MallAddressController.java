package com.followba.store.auth.controller;

import com.followba.store.auth.convert.MallAuthConvert;
import com.followba.store.auth.dto.TradeUserAddressDTO;
import com.followba.store.auth.service.MallAddressService;
import com.followba.store.auth.vo.in.AddressCreateIn;
import com.followba.store.auth.vo.in.AddressSetDefaultIn;
import com.followba.store.auth.vo.in.AddressUpdateIn;
import com.followba.store.auth.vo.out.AddressOut;
import com.followba.store.auth.vo.out.CommonBooleanOut;
import com.followba.store.auth.vo.out.CommonIdOut;
import com.followba.store.common.resp.Out;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mall/address")
@Validated
public class MallAddressController {

    @Resource
    private MallAddressService mallAddressService;

    @GetMapping("/list")
    public Out<List<AddressOut>> list() {
        List<TradeUserAddressDTO> dtoList = mallAddressService.list();
        return Out.success(MallAuthConvert.INSTANCE.toAddressOutList(dtoList));
    }

    @PostMapping("/create")
    public Out<CommonIdOut> create(@Valid @RequestBody AddressCreateIn in) {
        Long id = mallAddressService.create(MallAuthConvert.INSTANCE.toTradeUserAddressCreateDTO(in));
        return Out.success(CommonIdOut.of(id));
    }

    @PutMapping("/update")
    public Out<CommonBooleanOut> update(@Valid @RequestBody AddressUpdateIn in) {
        mallAddressService.update(MallAuthConvert.INSTANCE.toTradeUserAddressUpdateDTO(in));
        return Out.success(CommonBooleanOut.ok());
    }

    @DeleteMapping("/delete")
    public Out<CommonBooleanOut> delete(@RequestParam("id") Long id) {
        mallAddressService.delete(id);
        return Out.success(CommonBooleanOut.ok());
    }

    @PutMapping("/set-default")
    public Out<CommonBooleanOut> setDefault(@Valid @RequestBody AddressSetDefaultIn in) {
        mallAddressService.setDefault(in.getId());
        return Out.success(CommonBooleanOut.ok());
    }
}
