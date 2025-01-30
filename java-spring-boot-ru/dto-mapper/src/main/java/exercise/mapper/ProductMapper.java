package exercise.mapper;

// BEGIN

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {

    @Mappings({
            @Mapping(target = "name", source = "title"),
            @Mapping(target = "cost", source = "price"),
            @Mapping(target = "barcode", source = "vendorCode")
    })
    public abstract Product map(ProductCreateDTO dto);

    @Mappings({
            @Mapping(target = "title", source = "name"),
            @Mapping(target = "price", source = "cost"),
            @Mapping(target = "vendorCode", source = "barcode")
    })
    public abstract ProductDTO map(Product model);

    @Mapping(target = "cost", source = "price")
    public abstract void map(ProductUpdateDTO dto, @MappingTarget Product model);
}
// END
