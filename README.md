# MapStruct Exercise: E-commerce Product Management

## Scenario
You're building an e-commerce system where:
- Frontend sends product data in one format
- You store it in database entities with different structure  
- You return API responses in yet another format

**Your task:** Create MapStruct mappers to convert between these objects

## 1. FRONTEND FORM (what the admin panel sends when creating a product)
```java
public class ProductCreationForm {
    private String productName;
    private String shortDescription;
    private String fullDescription;
    private double priceInPounds;
    private int stockQuantity;
    private String categoryName;
    private String supplierEmail;
    private boolean isActive;
    private String imageUrl;
}
```
## 2. DATABASE ENTITY (what you store)
```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;          // only stores full description
    private BigDecimal price;            // stored in pence, not pounds
    private Integer stock;
    private String category;
    private String supplier;
    private Boolean active;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // constructors, getters, setters...
}
```
## 3. API RESPONSE (what customers see)
```java
public class ProductDto {
    private Long productId;
    private String title;
    private String summary;              // short description only
    private String price;                // formatted as "£10.99"
    private boolean inStock;             // true if stock > 0
    private String category;
    private String imageUrl;
    private String availability;         // "In Stock" or "Out of Stock"
}
```
## TODO: Create the Mapper Interface
```java
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
    
    ProductDto entityToDto(Product product);
    
    
    // TODO: Helper methods (you can add these as default methods)
    // - Convert pounds to pence
    // - Format pence as price string
    // - Truncate description
    // - Determine availability text
}
```

## 🏆 BONUS CHALLENGE
Create a method that maps a List<Product> to List<ProductDto>
MapStruct can auto-generate this if you define the single object mapping!
Think About:

- Which fields auto-map (same names)?
- Which need explicit @Mapping annotations?
- Which need transformations or expressions?
- Which should be ignored?
