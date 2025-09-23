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
    @Mapping(target = "name", source = "productName")
    @Mapping(target = "price", expression = "java(convertToPence(form.getPriceInPounds()))")
    @Mapping(target = "description", source = "fullDescription")  // Add this
    @Mapping(target = "category", source = "categoryName")        // Add this
    // Not sure if I've done this mapping right, I guess I'll have to try.
    @Mapping(target = "supplier", source = "supplierEmail")
    @Mapping(target = "stock", source = "stockQuantity")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "image", source = "imageUrl")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    Product formToEntity(ProductCreationForm form);

    // TODO: Map from Product entity to ProductDto for API response
    @Mapping(target = "productId", source = "id")
    @Mapping(target = "title", source = "name")
    @Mapping(target = "summary", expression = "java(truncated(product.getDescription()))")
    @Mapping(target = "price", expression = "java(formattedAsString(product.getPrice()))")
    @Mapping(target = "inStock", expression = "java(inStock(product.getStock()))")
    @Mapping(target = "imageUrl", source = "image")
    @Mapping(target = "availability", expression = "java(availability(product.getStock()))")
    ProductDto entityToDto(Product product);

    // TODO: Helper methods (you can add these as default methods)
    default BigDecimal convertToPence(double priceInPounds){
        return BigDecimal.valueOf(priceInPounds * 100);
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
        String firstPart = priceAsString.substring(0, priceAsString.length()-2);
        String secondPart = priceAsString.substring(priceAsString.length()-2, priceAsString.length());
        return "£"+firstPart+"."+secondPart;
        // There's probably SUCH a better way to do this lol, but this is the only way I can think of atm
    }
    default boolean inStock(Integer stock){
        return stock > 0;
    }
    default String availability(Integer stock){
        if(inStock(stock)){
            return "In Stock";
        }
        return "Out of Stock";
    } // Is there a better way to do this one as well?
}
