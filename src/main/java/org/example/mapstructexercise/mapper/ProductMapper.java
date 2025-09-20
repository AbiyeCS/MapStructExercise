package org.example.mapstructexercise.mapper;

import org.example.mapstructexercise.dto.ProductDto;
import org.example.mapstructexercise.frontend.ProductCreationForm;
import org.example.mapstructexercise.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper
public interface ProductMapper {

    // TODO: Map from ProductCreationForm to Product entity
    // Hints:
    // - productName -> name
    // - priceInPounds needs converting to pence (multiply by 100)
    // - shortDescription is ignored (we only store fullDescription)
    // - supplierEmail -> supplier
    // - stockQuantity -> stock
    // - isActive -> active
    // - imageUrl -> image
    // - createdAt and updatedAt should be set to now()
    // - id should be ignored (database generates it)

    @Mapping(target = "name", source = "productName")
    @Mapping(target = "price", source = "priceInPounds", expression = "java(convertToPence(priceInPounds)")
    // Not sure if I've done this mapping right, I guess I'll have to try.
    @Mapping(target = "supplier", source = "supplierEmail")
    @Mapping(target = "stock", source = "stockQuantity")
    @Mapping(target = "active", source = "isActive")
    @Mapping(target = "image", source = "imageUrl")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    Product formToEntity(ProductCreationForm form);



    // TODO: Map from Product entity to ProductDto for API response
    // Hints:
    // - id -> productId
    // - name -> title
    // - description -> summary (but truncate to 100 chars if longer)
    // - price in pence -> formatted string like "£10.99"
    // - stock > 0 -> inStock = true
    // - category auto-maps
    // - image -> imageUrl
    // - availability should be "In Stock" or "Out of Stock" based on stock
    // - updatedAt and createdAt are ignored
    @Mapping(target = "productId", source = "id")
    @Mapping(target = "title", source = "name")
    @Mapping(target = "Summary", source = "description", expression = "java(truncated(description))")
    // again I don't know if this even works
    @Mapping(target = "price", expression = "")
    ProductDto entityToDto(Product product);

    // TODO: Helper methods (you can add these as default methods)
    // - Convert pounds to pence
    // - Format pence as price string
    // - Truncate description
    // - Determine availability text

    default double convertToPence(double priceInPounds){
        return priceInPounds * 100;
    }
    default String truncated(String description){
        String truncatedWord = "";
        if(description.length() > 100){
            truncatedWord = description.substring(0,100);
            return truncatedWord;
        }
        return description;
    }
    default String formattedAsString(BigDecimal pence){
        String priceAsString = pence.toString();
        for(int i = 0; i < priceAsString.length(); i++){

        }
        return priceAsString;
    }
}
