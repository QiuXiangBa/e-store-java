package com.followba.store.product.convert;

import com.followba.store.product.dto.ProductAppSkuDTO;
import com.followba.store.product.dto.ProductAppSpuDTO;
import com.followba.store.product.dto.ProductAppSpuDetailDTO;
import com.followba.store.product.dto.ProductPageQueryDTO;
import com.followba.store.product.vo.in.ProductPageIn;
import com.followba.store.product.vo.out.ProductAppSkuOut;
import com.followba.store.product.vo.out.ProductAppSpuDetailOut;
import com.followba.store.product.vo.out.ProductAppSpuOut;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MallProductConvert {

    MallProductConvert INSTANCE = Mappers.getMapper(MallProductConvert.class);

    ProductPageQueryDTO toProductPageQueryDTO(ProductPageIn in);

    ProductAppSpuOut toProductAppSpuOut(ProductAppSpuDTO dto);

    List<ProductAppSpuOut> toProductAppSpuOutList(List<ProductAppSpuDTO> dtoList);

    ProductAppSkuOut toProductAppSkuOut(ProductAppSkuDTO dto);

    List<ProductAppSkuOut> toProductAppSkuOutList(List<ProductAppSkuDTO> dtoList);

    ProductAppSpuDetailOut toProductAppSpuDetailOut(ProductAppSpuDetailDTO dto);
}
