package com.followba.store.product.controller;

import com.followba.store.common.resp.Out;
import com.followba.store.product.convert.MallCartConvert;
import com.followba.store.product.dto.CartItemDTO;
import com.followba.store.product.service.MallCartService;
import com.followba.store.product.vo.in.CartAddIn;
import com.followba.store.product.vo.in.CartMergeIn;
import com.followba.store.product.vo.in.CartUpdateQuantityIn;
import com.followba.store.product.vo.in.CartUpdateSelectedIn;
import com.followba.store.product.vo.out.CartItemOut;
import com.followba.store.product.vo.out.CommonBooleanOut;
import com.followba.store.product.vo.out.CommonIdOut;
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
@RequestMapping("/mall/cart")
@Validated
public class MallCartController {

    @Resource
    private MallCartService mallCartService;

    @PostMapping("/add")
    public Out<CommonIdOut> add(@Valid @RequestBody CartAddIn in) {
        Long id = mallCartService.add(MallCartConvert.INSTANCE.toCartAddDTO(in));
        return Out.success(CommonIdOut.of(id));
    }

    @GetMapping("/list")
    public Out<List<CartItemOut>> list() {
        List<CartItemDTO> itemDTOList = mallCartService.list();
        return Out.success(MallCartConvert.INSTANCE.toCartItemOutList(itemDTOList));
    }

    @PutMapping("/update-quantity")
    public Out<CommonBooleanOut> updateQuantity(@Valid @RequestBody CartUpdateQuantityIn in) {
        mallCartService.updateQuantity(MallCartConvert.INSTANCE.toCartUpdateQuantityDTO(in));
        return Out.success(CommonBooleanOut.ok());
    }

    @PutMapping("/update-selected")
    public Out<CommonBooleanOut> updateSelected(@Valid @RequestBody CartUpdateSelectedIn in) {
        mallCartService.updateSelected(MallCartConvert.INSTANCE.toCartUpdateSelectedDTO(in));
        return Out.success(CommonBooleanOut.ok());
    }

    @PostMapping("/merge")
    public Out<CommonBooleanOut> merge(@Valid @RequestBody CartMergeIn in) {
        mallCartService.mergeGuestItems(null, MallCartConvert.INSTANCE.toCartMergeItemDTOList(in.getItems()));
        return Out.success(CommonBooleanOut.ok());
    }

    @DeleteMapping("/delete")
    public Out<CommonBooleanOut> delete(@RequestParam("cartId") Long cartId) {
        mallCartService.delete(cartId);
        return Out.success(CommonBooleanOut.ok());
    }
}
