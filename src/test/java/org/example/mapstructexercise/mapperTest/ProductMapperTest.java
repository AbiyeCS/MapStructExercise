package org.example.mapstructexercise.mapperTest;

import org.example.mapstructexercise.dto.ProductDto;
import org.example.mapstructexercise.frontend.ProductCreationForm;
import org.example.mapstructexercise.mapper.ProductMapper;
import org.example.mapstructexercise.model.Product;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


//@SpringBootTest
public class ProductMapperTest {
    ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Nested
    class HelperMethods {
        @Test
        void shouldConvertToPence(){
            BigDecimal pounds = mapper.convertToPence(10.50);
            assertThat(pounds).isEqualTo(BigDecimal.valueOf(1050.0));
        }

        @Test
        void shouldTruncateDescription(){
            String Description = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111122222";
            String truncatedDescription = mapper.truncated(Description);
            String shortDescription = "Testing";
            String shortTruncatedDescription = mapper.truncated(shortDescription);
            assertThat(truncatedDescription).isEqualTo("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
            assertThat(shortTruncatedDescription).isEqualTo("Testing");

        }

        @Test
        void shouldFormatAsString(){
            BigDecimal pence = BigDecimal.valueOf(1099);
            String formattedAsString = mapper.formattedAsString(pence);

            BigDecimal pence2 = BigDecimal.valueOf(50099);
            String formattedAsString2 = mapper.formattedAsString(pence2);
            assertThat(formattedAsString).isEqualTo("£10.99");
            assertThat(formattedAsString2).isEqualTo("£500.99");
        }

        @Test
        void shouldShowAvailability(){
            Integer inStock = 5;
            Integer OutOfStock = 0;

            assertThat(mapper.availability(inStock)).isEqualTo("In Stock");
            assertThat(mapper.availability(OutOfStock)).isEqualTo("Out of Stock");
        }
    }

    @Nested
    class FormToEntity{
        ProductCreationForm productCreationForm = new ProductCreationForm("Wireless Mouse",
                "Ergonomic wireless mouse",
                "A high-precision ergonomic wireless mouse with adjustable DPI and long battery life.",
                19.99,
                150,
                "Electronics",
                "supplier@example.com",
                true,
                "https://example.com/images/mouse.jpg");

        Product product = mapper.formToEntity(productCreationForm);

        @Test
        void testingMappingProductCreationFromToEntity(){
            assertThat(product.getName()).isEqualTo(productCreationForm.getProductName());
            assertThat(product.getDescription()).isEqualTo(productCreationForm.getFullDescription());
            assertThat(product.getPrice()).isEqualTo(1999); // No clue why this assertion is failing
            assertThat(product.getStock()).isEqualTo(productCreationForm.getStockQuantity());
            assertThat(product.getCategory()).isEqualTo(productCreationForm.getCategoryName());
            assertThat(product.getSupplier()).isEqualTo(productCreationForm.getSupplierEmail());
            assertThat(product.getActive()).isEqualTo(productCreationForm.isActive());
            assertThat(product.getImage()).isEqualTo(productCreationForm.getImageUrl());
//            assertThat(product.getCreatedAt()).is - Not sure how I'd check the time lol because wouldn't it be different if I say is equal to now.

        }
    }

    @Nested
    class EntityToDto {
        Product product = new Product("Wireless Mouse",
                "A high-precision ergonomic wireless mouse with adjustable DPI and long battery life.",
                BigDecimal.valueOf(1999),
                150,
                "Electronics",
                "supplier@example.com",
                true,
                "https://example.com/images/mouse.jpg",
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(2));

        ProductDto productDto = mapper.entityToDto(product);

        @Test
        void testingMappingEntityToDto() {
            assertThat(productDto.getProductId()).isEqualTo(product.getId());
            assertThat(productDto.getTitle()).isEqualTo(product.getName());
            assertThat(productDto.getSummary()).isEqualTo(product.getDescription());
            assertThat(productDto.getPrice()).isEqualTo("£19.99");
            assertThat(productDto.isInStock()).isEqualTo(true);
            assertThat(productDto.getImageUrl()).isEqualTo(product.getImage());
            assertThat(productDto.getAvailability()).isEqualTo("In Stock");
        }

    }

}
