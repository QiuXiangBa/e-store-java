package com.followba.store.product.controller;

import com.followba.store.common.resp.Out;
import com.followba.store.common.resp.PageResp;
import com.followba.store.product.convert.MallProductConvert;
import com.followba.store.product.dto.ProductAppSpuDTO;
import com.followba.store.product.dto.ProductAppSpuDetailDTO;
import com.followba.store.product.dto.ProductPageQueryDTO;
import com.followba.store.product.service.MallProductService;
import com.followba.store.product.vo.in.ProductPageIn;
import com.followba.store.product.vo.out.ProductAppSpuDetailOut;
import com.followba.store.product.vo.out.ProductAppSpuOut;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mall/product")
@Validated
public class MallProductController {

    @Resource
    private MallProductService mallProductService;

    @GetMapping("/page")
    public Out<PageResp<ProductAppSpuOut>> page(@Valid ProductPageIn in) {
//        ProductPageQueryDTO queryDTO = MallProductConvert.INSTANCE.toProductPageQueryDTO(in);
        PageResp<ProductAppSpuDTO> pageDTO = mallProductService.page(in);
        return Out.success(PageResp.of(pageDTO.getTotal(), MallProductConvert.INSTANCE.toProductAppSpuOutList(pageDTO.getList())));
    }

    @GetMapping("/detail")
    public Out<ProductAppSpuDetailOut> detail(@RequestParam("spuId") Long spuId) {
        ProductAppSpuDetailDTO detailDTO = mallProductService.detail(spuId);
        return Out.success(MallProductConvert.INSTANCE.toProductAppSpuDetailOut(detailDTO));
    }
}
