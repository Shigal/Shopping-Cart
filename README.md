# Shopping Cart

@Builder - to build an object
 
@RequiredArgsConstructor - from lombok, at compile time it creates the constructor with all the required arguments

@Slf4j - from lombok, to add logs

```
log.info("Product {} is saved.", product.getId());
```
- here we use placeholder of to concatenate the string

Note:
- we should not expose the model entities to the outside world, cause some fields are not meant to be shared


- mapping Product to ProductResponse
```
private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
}
	
```

