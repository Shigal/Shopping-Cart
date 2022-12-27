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

## Test Containers

- to write JUnit tests
- provides throwaway instances of common databases, Selenium Web browser, RabbitMQ and etc
- can do integration testing


### Integration Test

- need a container for Mongodb
- add the bom(bill of material) dependency of test containers

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>testcontainers-bom</artifactId>
            <version>1.17.6</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

```
- then install mongodb container into the project, for that add the dependency

```
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>mongodb</artifactId>
    <version>1.17.6</version>
    <scope>test</scope>
</dependency>


```
- also need to add the JUnit supporting module for the test containers

```
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>1.17.6</version>
    <scope>test</scope>
</dependency>

```

- then write the integration test in test class

annotate the class with @Testcontainers

- test will start the mongodb container by downloading the mongo:4.4.2 image 
- get the replica set url after starting the container and add it to the spring.data.mongodb.uri property dynamically at the time of creating the test
- we are providing this because we are not using the local mongodb but the mongodb docker container
- @DynamicPropertySource will add the properties dynamically at the time of running the test
- ObjectMapper is used to convert a Json into POJO or POJO into Json
- if we run the test it will first pull the mongodb image from docker container registry

- the test case for creatProduct method:
```
@Test
void shouldCreateProduct() throws Exception {

	ProductRequest productRequest = getProductRequest();
	String productRequestString = objectMapper.writeValueAsString(productRequest);

	mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
	.contentType(MediaType.APPLICATION_JSON)
	.content(productRequestString))
	.andExpect(status().isCreated());

	Assertions.assertEquals(productRepository.findAll().size(), 1);
}

```

- here, we create a product request using a method

```
private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("samsung 22")
				.description("samsung 22 +")
				.price(BigDecimal.valueOf(90000))
				.build();
}
	
```

- since the content accepts String argument we use ObjectMapper to convert Json into POJO and get its String valueOf
- Also we check if the object is created in the database or not with Assertions
