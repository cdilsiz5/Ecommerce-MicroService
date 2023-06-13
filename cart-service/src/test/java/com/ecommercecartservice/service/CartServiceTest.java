package com.ecommercecartservice.service;

import com.ecommerce.clientsservice.product.ProductDto;
import com.ecommerce.clientsservice.product.ProductServiceClient;
import com.ecommerce.clientsservice.user.UserDto;
import com.ecommerce.clientsservice.user.UserServiceClient;
import com.ecommercecartservice.dto.CartDto;
import com.ecommercecartservice.entity.Cart;
import com.ecommercecartservice.entity.CartItem;
import com.ecommercecartservice.exception.NotFoundException;
import com.ecommercecartservice.mapper.CartMapper;
import com.ecommercecartservice.repository.CartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartServiceTest {
   private CartService cartService;
    private CartRepository repository;

    private ProductServiceClient productServiceClient;
    private UserServiceClient userServiceClient;

    @BeforeEach
    public void setUp() {
        repository= Mockito.mock(CartRepository.class);
        productServiceClient=Mockito.mock(ProductServiceClient.class);
        userServiceClient = Mockito.mock(UserServiceClient.class);
        cartService=new CartService(repository,productServiceClient,userServiceClient);
    }
    @DisplayName("Should Return Cart Total when User Id And Cart Items And Product  Exist")
    @Test
    void shouldReturnCartTotal_whenUserIdAndCartItemsAndProductExist(){
        //parameters :  Cart , Cart Items , Product
        Long userId=1L;
        UserDto userDto=new UserDto(userId,"Cihan","Dilsiz","cdilsiz6@gmail.com","123456789");
        Cart cart=new Cart(1L,1L,Arrays.asList(new CartItem(1L,1L,3,null),new CartItem(2L,2L,3,null)));
        ProductDto productDto1=new ProductDto(1L,"Basqot Faraola","Chocolate biscuit",BigDecimal.valueOf(10L),1L);
        ProductDto productDto2=new ProductDto(2L,"Doritos Cubun","Potato Chips",BigDecimal.valueOf(10L),2L);
        BigDecimal expectedCartTotal = cart.getItems().stream()
                .map(item -> item.getProductId().equals(1L) ? productDto1.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                        : productDto2.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Mockito.when(repository.findByUserId(userId)).thenReturn(cart);
        Mockito.when(productServiceClient.getProduct(1L)).thenReturn(productDto1);
        Mockito.when(productServiceClient.getProduct(2L)).thenReturn(productDto2);
        Mockito.when(userServiceClient.getUser(userId)).thenReturn(userDto);


        BigDecimal cartTotal = cartService.getTotal(userId);

        assertEquals(expectedCartTotal, cartTotal);
        Mockito.verify(repository, Mockito.times(1)).findByUserId(Mockito.any(Long.class));
        Mockito.verify(productServiceClient, Mockito.times(1)).getProduct(1L);
        Mockito.verify(productServiceClient, Mockito.times(1)).getProduct(2L);
    }
    @DisplayName("Should Throw Not Found Exception  when User Id Does Not Exist")
    @Test
    void shouldThrowNotFoundException_whenUserIdDoesNotExist(){
        //parameters :  Cart , Cart Items , Product
        Long userId=1L;
        Mockito.when(repository.findByUserId(userId)).thenReturn(null);
        org.assertj.core.api.Assertions.assertThatThrownBy(()->cartService.getTotal(userId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User Not Found");

        Mockito.verify(userServiceClient, Mockito.times(1)).getUser(userId);

    }

    @DisplayName("Should Return Zero when User  Exist And Cart Id Does Not Exist")
    @Test
    void shouldReturnZero_whenUserIdExistAndCartDoesNotExist(){
        Long userId=1L;
        UserDto userDto=new UserDto(userId,"Cihan","Dilsiz","cdilsiz6@gmail.com","123456789");
        Cart cart=null;


        Mockito.when(repository.findByUserId(userId)).thenReturn(cart);
        Mockito.when(userServiceClient.getUser(userId)).thenReturn(userDto);

        BigDecimal expectedCartTotal = BigDecimal.ZERO;

        assertEquals(expectedCartTotal,cartService.getTotal(userId));
        Mockito.verify(repository, Mockito.times(1)).findByUserId(Mockito.any(Long.class));

    }
    @DisplayName("Should Return Saved Cart when User Id Exist ")
    @Test
    void shouldReturnSavedCart_whenUserIdExist() {
        // parameters :  Cart , Cart Items , Product
        Long userId=1L;
        UserDto userDto=new UserDto(userId, "Cihan", "Dilsiz", "cdilsiz6@gmail.com", "123456789");

        Cart expectedCart=new Cart(1L, userId, Arrays.asList());

        // Mock the behavior of userServiceClient and repository
        Mockito.when(userServiceClient.getUser(userId)).thenReturn(userDto);
        Mockito.when(repository.save(Mockito.any(Cart.class))).thenReturn(expectedCart);

        Cart actualCart = cartService.saveCart(userId);

        assertEquals(expectedCart, actualCart);
        Mockito.verify(userServiceClient, Mockito.times(1)).getUser(userId);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Cart.class));
    }

    @DisplayName("Should Clear Cart Items when User Id and Cart Exist")
    @Test
    void shouldClearCart_whenUserIdAndCartExist() {
        // parameters :  Cart , Cart Items , Product
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "Cihan", "Dilsiz", "cdilsiz6@gmail.com", "123456789");

        Cart cart = new Cart(1L, 1L, Arrays.asList(new CartItem(1L, 1L, 3, null), new CartItem(2L, 2L, 3, null)));
        Cart clearedCart = new Cart(1L, 1L, Arrays.asList());

        Mockito.when(userServiceClient.getUser(userId)).thenReturn(userDto);
        Mockito.when(repository.findByUserId(userId)).thenReturn(cart);
        Mockito.when(repository.save(clearedCart)).thenReturn(clearedCart);

        cartService.clearCartByUserId(userId);

        assertEquals(0, cart.getItems().size());
        Mockito.verify(userServiceClient, Mockito.times(1)).getUser(userId);
        Mockito.verify(repository, Mockito.times(1)).findByUserId(userId);
        Mockito.verify(repository, Mockito.times(1)).save(clearedCart);
    }
    @DisplayName("Should Clear Cart Items when User Id Exist and Cart Does Not Exist")
    @Test
    void shouldClearCartItems_whenUserIdExistAndCartDoesNotExist() {
        // parameters :  Cart , Cart Items , Product
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "Cihan", "Dilsiz", "cdilsiz6@gmail.com", "123456789");

        Cart emptyCart = null;
        Cart clearedCart = new Cart(1L, 1L, Arrays.asList());

        Mockito.when(userServiceClient.getUser(userId)).thenReturn(userDto);
        Mockito.when(repository.findByUserId(userId)).thenReturn(emptyCart);
        Mockito.when(repository.save(clearedCart)).thenReturn(clearedCart);

        cartService.clearCartByUserId(userId);

        assertEquals(0, clearedCart.getItems().size());
        Mockito.verify(userServiceClient, Mockito.times(1)).getUser(userId);
        Mockito.verify(repository, Mockito.times(1)).findByUserId(userId);
        Mockito.verify(repository, Mockito.times(1)).save(clearedCart);
    }
    @DisplayName("Should Return CartDto when User Id Exists")
    @Test
    void shouldReturnCartDto_whenUserIdExist() {
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "Cihan", "Dilsiz", "cdilsiz6@gmail.com", "123456789");
        Cart cart = new Cart(1L, userId, Arrays.asList(new CartItem(1L, 1L, 3, null), new CartItem(2L, 2L, 3, null)));
        CartDto expectedCartDto = CartMapper.CART_MAPPER.toCartDto(cart);

        Mockito.when(userServiceClient.getUser(userId)).thenReturn(userDto);
        Mockito.when(repository.findByUserId(userId)).thenReturn(cart);

        CartDto actualCartDto = cartService.getCartDtoByUserId(userId);

        assertEquals(expectedCartDto, actualCartDto);
        Mockito.verify(userServiceClient, Mockito.times(1)).getUser(userId);
        Mockito.verify(repository, Mockito.times(1)).findByUserId(userId);
    }



    @AfterEach
    public void tearDown(){
        Mockito.reset(repository, productServiceClient, userServiceClient);

    }

}