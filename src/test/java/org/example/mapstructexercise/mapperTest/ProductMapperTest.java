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


@SpringBootTest
public class ProductMapperTest {
    ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Nested
    class HelperMethods {
        @Test
        void shouldConvertToPence(){
            BigDecimal pounds = mapper.convertToPence(10.50);
            assertThat(pounds).isEqualTo(1050);
        }

        @Test
        void shouldTruncateDescription(){
            String Description = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111122222";
            String truncatedDescription = mapper.truncated(Description);
            assertThat(truncatedDescription).isEqualTo("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        }

        @Test
        void shouldFormatAsString(){
            BigDecimal pence = BigDecimal.valueOf(1099);
            String formattedAsString = mapper.formattedAsString(pence);
            assertThat(formattedAsString).isEqualTo("£10.99");
        }

        @Test
        void shouldShowAvailiablity(){
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
            assertThat(product.getDescription()).isEqualTo(productCreationForm.getFullDescription().substring(0,100));
            assertThat(product.getPrice()).isEqualTo(1999);
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
            assertThat(productDto.getSummary()).isEqualTo(product.getDescription().substring(0, 100));
            assertThat(productDto.getPrice()).isEqualTo("£19.99");
            assertThat(productDto.isInStock()).isEqualTo(true);
            assertThat(productDto.getImageUrl()).isEqualTo(product.getImage());
            assertThat(productDto.getAvailability()).isEqualTo("In Stock");
        }

    }

}
