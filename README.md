# Shopping Cart


## Sample Requests: Order Service

POST    localhost:8282/api/order

Body    

```

{
"orderLineItemsDtos": [
{
"skuCode": "samsung_22",
"price": 120000,
"quantity": 1
}
]
}

```

#

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

#
In Inventory Service, we are introduces to an annotation @Transactional(readOnly = true)

```

 @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.finBySkuCode(skuCode).isEmpty();
    }
    
```
- we use @Transactional(readOnly = true) for search or retrieval operation to make sure we can only perform the read-only operation
- if we annotate @Transactional a class (eg: OrderServiceImpl), springframework will automatically create and commit the transaction


- we need to add data to inventory database, from Application class
- so that it will load the data at the time of application startup

```

@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("vivo_17_black");
			inventory.setQuantity(120);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("vivo_19_blue");
			inventory1.setQuantity(80);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("vivo_19_white");
			inventory2.setQuantity(0);
			
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
		};
	}

```
#

Note:

Spring Data JPA generate queries based on name of property in your Entity class ,
If entity based custom methods defined in repository interfaces also generate queries internally based on method name.

#

## Synchronous and Asynchronous Communication

- Order-service sends the request and wait for the response from inventory-service called synchronous
- Order-service sends the request and does not wait for the response: fire and fly called Asynchronous
- Synchronous comm can be done through Http client eg: RestTemplate by spring boot, WebClient by Spring web flux
- web client also supports async